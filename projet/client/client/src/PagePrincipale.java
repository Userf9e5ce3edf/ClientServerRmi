import Models.ServeurDistant;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PagePrincipale extends JFrame {

    // Déclaration des composants de l'interface utilisateur
    private JButton clientsButton;
    private JButton achatsButton;
    private JButton gererLeStockButton;
    private JPanel mainPanel;
    private JButton optionsButton;

    /**
     * Constructeur de la classe PagePrincipale.
     * Initialise les composants de l'interface utilisateur et les gestionnaires d'événements.
     */
    public PagePrincipale() {
        setContentPane(mainPanel);
        setTitle("Page Principale");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(10, 10, 840, 300);
        setVisible(true);

        // Gestionnaires d'événements

        // Ajout d'un ActionListener au bouton Clients
        // permet d'ouvrir la page Clients
        clientsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PageClients pageClients = new PageClients();
                pageClients.setVisible(true);

                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        dispose();
                    }
                });
            }
        });

        // Ajout d'un ActionListener au bouton Achats
        // permet d'ouvrir la page Achats
        achatsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PageAchat pageAchat = new PageAchat();
                pageAchat.setVisible(true);

                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        dispose();
                    }
                });
            }
        });

        // Ajout d'un ActionListener au bouton Gérer le stock
        // permet d'ouvrir la page GestionStock
        gererLeStockButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GestionStock gestionStock = new GestionStock();
                gestionStock.setVisible(true);

                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        dispose();
                    }
                });
            }
        });

        // Ajout d'un ActionListener au bouton Options
        // permet de changer l'adresse ip du serveur
        optionsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String hostIP = JOptionPane.showInputDialog("Saisir l'ip du serveur:");

                if(hostIP == null) {
                    return;
                }

                if (isValidIP(hostIP)) {
                    if (isReachable(hostIP)) {
                        ServeurDistant.setHostIP(hostIP);
                    } else {
                        JOptionPane.showMessageDialog(
                                null, "adresse ip inatteignable");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "adresse ip non valide");
                }
            }
        });
    }

    /**
     * Méthode pour vérifier si l'adresse ip est valide.
     * @param ip
     * @return
     */
    private static boolean isValidIP(String ip) {
        String IPADDRESS_PATTERN =
                "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                        "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                        "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                        "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
        Pattern pattern = Pattern.compile(IPADDRESS_PATTERN);
        Matcher matcher = pattern.matcher(ip);
        return matcher.matches();
    }

    /**
     * Méthode pour vérifier si l'adresse ip est atteignable.
     * @param ip
     * @return
     */
    private static boolean isReachable(String ip) {
        try {
            InetAddress address = InetAddress.getByName(ip);
            return address.isReachable(5000); // 5 secondes
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
