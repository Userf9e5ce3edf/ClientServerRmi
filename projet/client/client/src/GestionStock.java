import Models.ClientDistant;
import Models.Composant;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.rmi.RemoteException;
import java.util.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GestionStock extends JFrame {
    private JList FamillesList;
    private JList ComposantsList;
    private JLabel AjoutLabel;
    private JTextField QuantiteTextField;
    private JButton AjouterButton;
    private JButton RetourButton;
    private JPanel mainPanel;
    private List<Composant> composants;
    private List<String> familles;
    private ClientDistant clientDistant = ClientDistant.getInstance();

    public GestionStock() {

        RefreshListeFamille();

        setContentPane(mainPanel);
        setTitle("Page Clients");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(10, 10, 840, 300);
        setVisible(true);

        RetourButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PagePrincipale pagePrincipale = new PagePrincipale();
                pagePrincipale.setVisible(true);

                dispose();
            }
        });

        FamillesList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (FamillesList.getSelectedIndex() != -1) {
                    String famille = familles.get(FamillesList.getSelectedIndex());
                    RefreshListeComposants(famille);
                }
            }
        });
        AjouterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the quantity from the text field
                String quantityText = QuantiteTextField.getText();

                // Check if the quantity is a number if not return;
                int quantite;
                try {
                    quantite = Integer.parseInt(quantityText);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(
                            GestionStock.this,
                            "Invalid quantity. Please enter a number.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Get the selected composant
                int selectedIndex = ComposantsList.getSelectedIndex();
                if (selectedIndex == -1) {
                    JOptionPane.showMessageDialog(
                            GestionStock.this,
                            "No composant selected.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                Composant selectedComposant = composants.get(selectedIndex);

                // Check if the new quantity is above 100 if yes show a message and then return
                int newQuantite = selectedComposant.getQuantite() + quantite;
                if (newQuantite > 100) {
                    JOptionPane.showMessageDialog(
                            GestionStock.this,
                            "Quantity cannot be more than 100.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                // if the new quantity is 100 or below, update the composant in the database and then refresh the list
                try {
                    clientDistant.stub.ajouterComposant(selectedComposant.getReference(), quantite);
                    RefreshListeComposants(selectedComposant.getFamille());
                } catch (RemoteException ex) {
                    JOptionPane.showMessageDialog(
                            GestionStock.this,
                            "Error updating composant: " + ex.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
                QuantiteTextField.setText("");
            }
        });
    }
    private void RefreshListeFamille() {
        try {
            familles = clientDistant.stub.GetAllFamilles();
        } catch (RemoteException e) {
            JOptionPane.showMessageDialog(
                    this, "Erreur lors du chargement des clients: " +
                            e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            PagePrincipale pagePrincipale = new PagePrincipale();
            pagePrincipale.setVisible(true);
            dispose();
            return;
        }
        FamillesList.setListData(familles.toArray());
    }

    private void RefreshListeComposants(String famille) {
        try {
            composants = clientDistant.stub.RechercheComposant(famille);
            DefaultListModel<String> model = new DefaultListModel<>();
            for (Composant composant : composants) {
                model.addElement(composant.getReference()
                        + " - "
                        + composant.getQuantite()
                        + " - "
                        + composant.getPrix() + "Euros");
            }
            ComposantsList.setModel(model);
        } catch (RemoteException ex) {
            JOptionPane.showMessageDialog(
                    GestionStock.this, "Erreur lors de la modification du client: " +
                            ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
