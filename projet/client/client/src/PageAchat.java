import Models.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;

public class PageAchat extends JFrame {
    private JButton retourButton;
    private JList clientsListe;
    private JList famillesListe;
    private JList panierListe;
    private JList composantsListe;
    private JButton acheterButton;
    private JButton retirerButton;
    private JButton ajouterAuPanierButton;
    private JTextField QuantitetextField;
    private JLabel totalPrixLabel;
    private JLabel clientEnCourLabel;
    private JLabel clientLabel;
    private JTextField quantiteARetirertextField;
    private JLabel qtRetirerLabel;
    private JPanel mainPanel;
    private List<Client> clients;
    private List<Composant> composants;
    private List<String> familles;
    private ClientDistant clientDistant;
    private Facture factureEnCours;
    private List<FactureItem> factureItemsEnCours;
    private Client clientEnCours;
    public PageAchat() {
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
        RefreshListeFamilles();

        setContentPane(mainPanel);
        setTitle("Page Achat");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(10, 10, 840, 300);
        setVisible(true);
        clientsListe.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                loadPanier();
            }
        });
        famillesListe.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (famillesListe.getSelectedIndex() != -1) {
                    String famille = familles.get(famillesListe.getSelectedIndex());
                    RefreshListeComposants(famille);
                }
            }
        });
        ajouterAuPanierButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(clientEnCours == null) {
                    JOptionPane.showMessageDialog(
                            PageAchat.this,
                            "Pas de client selectionner",
                            "Erreur", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (composantsListe.getSelectedIndex() != -1) {
                    int quantite = Integer.parseInt(QuantitetextField.getText());
                    Composant composant = composants.get(composantsListe.getSelectedIndex());

                    if (composant.getQuantite() < quantite) {
                        JOptionPane.showMessageDialog(
                                PageAchat.this,
                                "Quantité en stock insuffisante",
                                "Erreur", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    try {
                        clientDistant.stub.ajouterAuPanier(
                                composant.getReference(), quantite, clientEnCours.getNom());
                        loadPanier();
                        RefreshListeComposants(composant.getFamille());
                        QuantitetextField.setText("");
                    } catch (RemoteException ex) {
                        JOptionPane.showMessageDialog(
                                PageAchat.this,
                                "Erreur lors de l'ajout au panier: " +
                                        ex.getMessage(),
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        retirerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (panierListe.getSelectedIndex() != -1) {
                    int quantite = Integer.parseInt(quantiteARetirertextField.getText());
                    FactureItem factureItem = factureItemsEnCours.get(panierListe.getSelectedIndex());

                    if(factureItem.getQuantite() < quantite) {
                        JOptionPane.showMessageDialog(
                                PageAchat.this,
                                "Quantité insuffisante",
                                "Erreur", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    try {
                        clientDistant.stub.retirerDuPanier(quantite, factureItem.getId());
                        loadPanier();
                        RefreshListeComposants(factureItem.getComposant().getFamille());
                        quantiteARetirertextField.setText("");
                    } catch (RemoteException ex) {
                        JOptionPane.showMessageDialog(
                                PageAchat.this,
                                "Erreur lors de la suppression de l'article du panier: " +
                                        ex.getMessage(),
                                "Erreur", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        acheterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(panierListe.getModel().getSize() == 0) {
                    JOptionPane.showMessageDialog(
                            PageAchat.this,
                            "Panier vide",
                            "Erreur", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    EnumModeDePaiment[] paymentMethodsEnum = EnumModeDePaiment.values();
                    String[] paymentMethods = new String[paymentMethodsEnum.length];
                    for (int i = 0; i < paymentMethodsEnum.length; i++) {
                        paymentMethods[i] = paymentMethodsEnum[i].name();
                    }
                    String selectedPaymentMethod = (String) JOptionPane.showInputDialog(
                            PageAchat.this,
                            "Total a payer: " + totalPrixLabel.getText() + "\nChoisir un mode de paiment:",
                            "Paiement",
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            paymentMethods,
                            paymentMethods[0]
                    );

                    if (selectedPaymentMethod != null) {
                        EnumModeDePaiment modeDePaiment = EnumModeDePaiment.valueOf(selectedPaymentMethod);

                        boolean result = clientDistant.stub.payerFacture(clientEnCours.getNom(), modeDePaiment);
                        if (!result) {
                            JOptionPane.showMessageDialog(
                                    PageAchat.this,
                                    "Erreur lors de l'achat",
                                    "Erreur", JOptionPane.ERROR_MESSAGE);
                        }
                        loadPanier();
                    }
                } catch (RemoteException ex) {
                    JOptionPane.showMessageDialog(
                            PageAchat.this,
                            "Erreur lors de l'achat: " +
                                    ex.getMessage(),
                            "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        retourButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PagePrincipale pagePrincipale = new PagePrincipale();
                pagePrincipale.setVisible(true);

                dispose();
            }
        });
    }

    private void RefreshListeClients() {
        try {
            clients = clientDistant.stub.getAllClients();
        } catch (RemoteException e) {
            JOptionPane.showMessageDialog(
                    this, "Erreur lors du chargement des clients: " +
                            e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            PagePrincipale pagePrincipale = new PagePrincipale();
            pagePrincipale.setVisible(true);
            dispose();
            return;
        }
        DefaultListModel<String> model = new DefaultListModel<>();
        for (Client client : clients) {
            model.addElement(client.getNom());
        }
        clientsListe.setModel(model);
    }

    private void RefreshListeFamilles() {
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
        famillesListe.setListData(familles.toArray());
    }

    private void loadPanier() {
        DefaultListModel<String> model = new DefaultListModel<>();
        panierListe.setModel(model);
        totalPrixLabel.setText("0");

        if (clientsListe.getSelectedIndex() != -1) {
            clientEnCours = clients.get(clientsListe.getSelectedIndex());
            String client = clientEnCours.getNom() + " " + clientEnCours.getPrenom();
            clientLabel.setText(client);

            try {
                factureEnCours = clientDistant.stub.getFactureEnCours(clientEnCours.getId());
                if(factureEnCours != null) {
                    totalPrixLabel.setText(String.valueOf(factureEnCours.getTotalFacture()));
                    factureItemsEnCours = clientDistant.stub.getAllFactureItem(factureEnCours.getId());
                    if(factureEnCours != null) {
                        model = new DefaultListModel<>();
                        for (FactureItem factureItem : factureItemsEnCours) {
                            model.addElement(factureItem.getComposant().getReference()
                                    + " - "
                                    + factureItem.getQuantite()
                                    + " - "
                                    + factureItem.getComposant().getPrix() + "Euros");
                        }
                        panierListe.setModel(model);
                    }
                }
            } catch (RemoteException ex) {
                JOptionPane.showMessageDialog(
                        PageAchat.this,
                        "Erreur lors de la récupération de la facture du client: " +
                                ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
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
            composantsListe.setModel(model);
        } catch (RemoteException ex) {
            JOptionPane.showMessageDialog(
                    PageAchat.this,
                    "Erreur lors de la mise a jour de la liste des composants: " +
                            ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
}
