import Models.ClientDistant;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PagePrincipale extends JFrame {
    private JButton clientsButton;
    private JButton achatsButton;
    private JButton gererLeStockButton;
    private JPanel mainPanel;
    private JButton optionsButton;

    public PagePrincipale() {
        setContentPane(mainPanel);
        setTitle("Page Principale");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(10, 10, 840, 300);
        setVisible(true);

        // Add action listeners to the buttons
        clientsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PageClients pageClients = new PageClients();
                pageClients.setVisible(true);

                dispose();
            }
        });

        achatsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PageAchat pageAchat = new PageAchat();
                pageAchat.setVisible(true);

                dispose();
            }
        });

        gererLeStockButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GestionStock gestionStock = new GestionStock();
                gestionStock.setVisible(true);

                dispose();
            }
        });
        optionsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String hostIP = JOptionPane.showInputDialog("Saisir l'ip du serveur:");

                if(hostIP == null) {
                    return;
                }

                if (isValidIP(hostIP)) {
                    if (isReachable(hostIP)) {
                        ClientDistant.setHostIP(hostIP);
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

    private static boolean isReachable(String ip) {
        try {
            InetAddress address = InetAddress.getByName(ip);
            return address.isReachable(2000); // 2 seconds
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


}
