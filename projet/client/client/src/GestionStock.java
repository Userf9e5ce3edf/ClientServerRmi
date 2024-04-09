import Models.ClientDistant;
import Models.Composant;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.rmi.NotBoundException;
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
    private ClientDistant clientDistant;

    public GestionStock() {

        try {
            ClientDistant.getInstance();
        } catch (RemoteException | NotBoundException e) {
            JOptionPane.showMessageDialog(this,
                    "Error: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
            dispose();
        }

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

                String quantityText = QuantiteTextField.getText();

                int quantite;
                try {
                    quantite = Integer.parseInt(quantityText);
                    if(quantite <= 0) {
                        JOptionPane.showMessageDialog(
                            GestionStock.this,
                            "La quantité doit être plus supérieur à 0.",
                            "Erreur", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(
                            GestionStock.this,
                            "Quantité invalide. Saisir un chiffre.",
                            "Erreur", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Get the selected composant
                int selectedIndex = ComposantsList.getSelectedIndex();
                if (selectedIndex == -1) {
                    JOptionPane.showMessageDialog(
                            GestionStock.this,
                            "Aucun composant selectionner.",
                            "Erreur", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                Composant selectedComposant = composants.get(selectedIndex);

                // si la nouvelle quantité est plus de 100, afficher une erreur
                int newQuantite = selectedComposant.getQuantite() + quantite;
                if (newQuantite > 100) {
                    JOptionPane.showMessageDialog(
                            GestionStock.this,
                            "La nouvelle quantité ne peut pas dépasser 100.",
                            "Erreur", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                // si la nouvelle quantite est 100 ou moins, mettre a jour la bdd et refresh le front
                try {
                    clientDistant.stub.ajouterComposant(selectedComposant.getReference(), quantite);
                    RefreshListeComposants(selectedComposant.getFamille());
                } catch (RemoteException ex) {
                    JOptionPane.showMessageDialog(
                            GestionStock.this,
                            "Erreur lors de la mise à jour du composant: " + ex.getMessage(),
                            "Erreur", JOptionPane.ERROR_MESSAGE);
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
                    this, "Erreur lors du chargement des familles: " +
                            e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
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
                    GestionStock.this, "Erreur lors du chargement des composants: " +
                            ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
}
