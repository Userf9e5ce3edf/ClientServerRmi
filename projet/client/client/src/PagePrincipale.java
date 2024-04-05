import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PagePrincipale extends JFrame {
    private JButton clientsButton;
    private JButton achatsButton;
    private JButton gererLeStockButton;
    private JPanel mainPanel;

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
    }
}
