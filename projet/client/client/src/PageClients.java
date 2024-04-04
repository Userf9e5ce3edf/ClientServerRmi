import Interfaces.IClient;
import Interfaces.IRequete;
import Models.Client;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.List;

public class PageClients extends JFrame {
    private List<Client> clients;
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
    private ClientDistant clientDistant = new ClientDistant();

    public PageClients() {

        RefreshListeClients();

        setContentPane(mainPanel);
        setTitle("Page Clients");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(10, 10, 840, 300);
        setVisible(true);
        retourButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PagePrincipale pagePrincipale = new PagePrincipale();
                pagePrincipale.setVisible(true);

                dispose();
            }
        });
        ClientList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (ClientList.getSelectedIndex() != -1) {
                    Client client = clients.get(ClientList.getSelectedIndex());
                    NomtextField.setText(client.getNom());
                    AdressetextField.setText(client.getAdresse());
                }
            }
        });
        ajouterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nom = NomtextField.getText();
                String adresse = AdressetextField.getText();
                try {
                    clientDistant.stub.createClient(nom, adresse);
                    RefreshListeClients();
                    ClearFields();
                } catch (RemoteException ex) {
                    JOptionPane.showMessageDialog(
                            PageClients.this, "Erreur lors de l'ajout du client: " +
                                    ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
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
        modifierButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (ClientList.getSelectedIndex() != -1) {
                    int id = clients.get(ClientList.getSelectedIndex()).getId();
                    String nom = NomtextField.getText();
                    String adresse = AdressetextField.getText();
                    try {
                        clientDistant.stub.updateClient(id, nom, adresse);
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

    private void ClearFields() {
        NomtextField.setText("");
        PrenomtextField.setText("");
        AdressetextField.setText("");
    }

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

