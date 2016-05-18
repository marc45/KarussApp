package Version.n1;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JPopupMenu.Separator;

public class Principal extends JFrame {

    JPanel panelPadre = new JPanel(new BorderLayout());
    configuracion conf = new configuracion();
    clienteJedis CJ;
    JPopupMenu raizMenu;
    Separator separado1;
    JMenuItem maximizar;
    JMenuItem minimizar;
    JMenuItem salir;
    SystemTray ST;
    TrayIcon TI;
    Image II;
    String IP;
    String Canal;

    private void inicilizarComponentes() {
        IP = conf.DameUrl();
        raizMenu = new JPopupMenu();
        separado1 = new Separator();
        maximizar = new JMenuItem();
        ImageIcon IIM = new ImageIcon(getClass().getResource("/iconos/iconito.png"));
        maximizar.setIcon(IIM);
        maximizar.setText("Maximizar");
        maximizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ST.remove(TI);
                setVisible(true);
                toFront();
            }
        });

        minimizar = new JMenuItem();
        salir = new JMenuItem();
        salir.setIcon(new ImageIcon(getClass().getResource("/iconos/iconito.png")));
        salir.setText("salir");
        salir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        GroupLayout Layout = new GroupLayout(getContentPane());
        Layout.setHorizontalGroup(
                Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGap(0, 500, Short.MAX_VALUE)
        );
        pack();
    }

    private void maximizarAction(ActionEvent e) {
        ST.remove(TI);
        setVisible(true);
        toFront();
    }

    private void salirAction(ActionEvent e) {
        System.exit(0);
    }

    private void formWindowClosed(java.awt.event.WindowEvent evt) {
        setVisible(false);
        try {
            ST.add(TI);
        } catch (AWTException e) {
        }
    }

    private void formWindowOpened(java.awt.event.WindowEvent evt) {
        try {
            iniziarJedis(IP, Canal);
        } catch (Exception e) {
        }

    }

    public Principal(int canal) {
        super();
        if (canal < 1) {
            login lo = new login();
            lo.setVisible(true);
            lo.toFront();
        } else {
            this.Canal = canal + "";
        }
        inicilizarComponentes();
        if (SystemTray.isSupported()) {
            ST = SystemTray.getSystemTray();
            II = new ImageIcon(getClass().getResource("/iconos/iconito.png")).getImage();
            TI = new TrayIcon(II, "KarussApp", null);
            maximizar.setText("maximizar");
            salir.setText("salir");
            TI.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent evt) {
                    raizMenu.setLocation(evt.getX(), evt.getY());
                    raizMenu.setInvoker(raizMenu);
                    raizMenu.setVisible(true);
                }
            });

            TI.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    TI.displayMessage("MenuItem Maximizar", "MenuItem2 Salir", TrayIcon.MessageType.INFO);
                }
            });
            TI.setImageAutoSize(true);
        } else {
            JOptionPane.showMessageDialog(null, "no soporta el traysystem");
        }
        this.setLocationRelativeTo(null);
    }

    public void iniziarJedis(String ip, String canal) {

        try {
            CJ = new clienteJedis(ip, conf.Canal() + canal, this);
        } catch (Exception e) {
        }
    }
}
