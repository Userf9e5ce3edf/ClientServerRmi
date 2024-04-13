import Models.Client;
import Models.ClientDistant;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;

public class PageClients extends JFrame {
    // Déclaration des composants de l'interface utilisateur
    private JList ClientList;
    private JTextField AdressetextField;
    private JTextField NomtextField;
    private JTextField PrenomtextField;
    private JButton ajouterButton;
    private JButton supprimerButton;
    private JButton modifierButton;
    private JButton retourButton;
    private JPanel mainPanel;
    private JPanel secondPanel;

    // Déclaration des variables
    private List<Client> clients;
    private ClientDistant clientDistant;

    /**
     * Constructeur de la classe PageClients.
     * Initialise les composants de l'interface utilisateur et les gestionnaires d'événements.
     */
    public PageClients() {
          try {
                clientDistant = ClientDistant.getInstance();
            } catch (RemoteException | NotBoundException e) {
                JOptionPane.showMessageDialog(this,
                        "Erreur: " + e.getMessage(), "Erreur",
                        JOptionPane.ERROR_MESSAGE);
              PagePrincipale pagePrincipale = new PagePrincipale();
              pagePrincipale.setVisible(true);
                dispose();
                return; // pour etre sur que le code ne s'execute pas
            }

        RefreshListeClients();

        setContentPane(mainPanel);
        setTitle("Page Clients");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(10, 10, 840, 300);
        setVisible(true);

        // Gestionnaires d'événements

        // Ajout d'un ActionListener au bouton Retour
        // permet de retourner à la page principale
        retourButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PagePrincipale pagePrincipale = new PagePrincipale();
                pagePrincipale.setVisible(true);

                dispose();
            }
        });

        // Ajout d'un ListSelectionListener à la liste des clients
        // permet de remplir les champs de texte avec les informations du client sélectionné
        ClientList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (ClientList.getSelectedIndex() != -1) {
                    Client client = clients.get(ClientList.getSelectedIndex());
                    NomtextField.setText(client.getNom());
                    PrenomtextField.setText(client.getPrenom());
                    AdressetextField.setText(client.getAdresse());
                }
            }
        });

        // Ajout d'un ActionListener au bouton Ajouter
        // permet d'ajouter un nouveau client
        ajouterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nom = NomtextField.getText();
                String prenom = PrenomtextField.getText();
                String adresse = AdressetextField.getText();
                try {
                    clientDistant.stub.createClient(nom, prenom, adresse);
                    RefreshListeClients();
                    ClearFields();
                } catch (RemoteException ex) {
                    JOptionPane.showMessageDialog(
                            PageClients.this, "Erreur lors de l'ajout du client: " +
                                    ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Ajout d'un ActionListener au bouton Supprimer
        // permet de supprimer le client sélectionné
        supprimerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (ClientList.getSelectedIndex() != -1) {
                    try {
                        clientDistant.stub.deleteClient(clients.get(ClientList.getSelectedIndex()).getId());
                        RefreshListeClients();
                        ClearFields();
                    } catch (RemoteException ex) {
                        JOptionPane.showMessageDialog(
                                PageClients.this, "Erreur lors de la suppression du client: " +
                                        ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        // Ajout d'un ActionListener au bouton Modifier
        // permet de modifier les informations du client sélectionné
        modifierButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (ClientList.getSelectedIndex() != -1) {
                    int id = clients.get(ClientList.getSelectedIndex()).getId();
                    String nom = NomtextField.getText();
                    String prenom = PrenomtextField.getText();
                    String adresse = AdressetextField.getText();
                    try {
                        clientDistant.stub.updateClient(id, nom, prenom, adresse);
                        RefreshListeClients();
                        ClearFields();
                    } catch (RemoteException ex) {
                        JOptionPane.showMessageDialog(
                                PageClients.this, "Erreur lors de la modification du client: " +
                                        ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
    }

    /**
     * Méthode pour effacer les champs de texte.
     */
    private void ClearFields() {
        NomtextField.setText("");
        PrenomtextField.setText("");
        AdressetextField.setText("");
    }

    /**
     * Méthode pour rafraîchir la liste des clients.
     * Cette méthode récupère tous les clients du serveur et
     * met à jour la liste des clients dans l'interface utilisateur.
     */
    private void RefreshListeClients() {
        try {
            clients = clientDistant.stub.getAllClients();
        } catch (RemoteException e) {
            JOptionPane.showMessageDialog(
                    this, "Erreur lors du chargement des clients: " +
                            e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            PagePrincipale pagePrincipale = new PagePrincipale();
            pagePrincipale.setVisible(true);
            dispose();
            return;
        }
        DefaultListModel<String> model = new DefaultListModel<>();
        for (Client client : clients) {
            model.addElement(client.getNom());
        }
        ClientList.setModel(model);
   }
}

