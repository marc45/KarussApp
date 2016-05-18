package Version.n1;

import java.awt.Desktop;
import java.awt.HeadlessException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javax.swing.JOptionPane;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisPubSub;

public class clienteJedis {

    JedisPool jp;
    JedisPoolConfig jpc;
    Jedis cliente;
    String canales[];
    JedisPubSub jsp;
    NanozNotificacion nn;
    int estado;
    String ip;
    String canal;
    Thread plop;
    Thread ping;


    public clienteJedis(final String ip, final String canal, final Principal principal) {
        this.cliente = new Jedis(ip);
<<<<<<< HEAD
        System.out.println(cliente.isConnected());
        //cliente.connect();
        //cliente.disconnect();
=======
        this.ip = ip;
        this.canal = canal;
>>>>>>> e52375d567f5c6ccfc7490b88799de7ad9aab536
        this.canales = new String[1];
        canales[0] = canal;
        try {
            plop = new Thread(new Runnable() {
                @Override
                public void run() {
                    jsp = new JedisPubSub() 
                    {
                        @Override
                        public void onMessage(String channel, String message) {
                            String result[] = new String[3];
                            result = jsonToString.recorrer(message, result);
                            nn = new NanozNotificacion(result[1], result[0]);
                            nn.setVisible(true);
                        }

                        @Override
                        public void onPMessage(String pattern, String channel, String message) {
                        }

                        @Override
                        public void onSubscribe(String channel, int subscribedChannels) {
                            try {
                                principal.dispose();
                            } catch (Exception e) {
                            }
                        }

                        @Override
                        public void onUnsubscribe(String channel, int subscribedChannels) {
                        }

                        @Override
                        public void onPUnsubscribe(String pattern, int subscribedChannels) {
                        }

                        @Override
                        public void onPSubscribe(String pattern, int subscribedChannels) {
                        }

                    };
<<<<<<< HEAD
                    cliente.subscribe(jsp, canales);
                    System.out.println("conectado");
=======
                    while (true) {
                        try {
                            ping = new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    while (true) {
                                        try {
                                            ping.sleep(900000);
                                            System.out.println(cliente.ping());
                                        } catch (Exception e) {
                                        }
                                    }
                                }
                            });
                            ping.start();
                            cliente.subscribe(jsp, canales);
                        } catch (Exception ez) {
                            try {
                                Thread.sleep(3000);
                            } catch (Exception ex) {
                            }
                            new clienteJedis(ip, canal, principal);
                            cliente.disconnect();
                            ping.stop();
                            plop.stop();
                        }
                    }
>>>>>>> e52375d567f5c6ccfc7490b88799de7ad9aab536
                }
            });
            plop.start();
        } catch (Exception e) {
            try {
                Thread.sleep(3000);
                plop.stop();
                ping.stop();
                new clienteJedis(ip, canal, principal);
            } catch (Exception ex) {
            }

        }

    }

    private void notificacionCiclada(String[] result, int estado) {
        if (estado == 0) {
            try {
                if (Desktop.isDesktopSupported()) {
                    Desktop.getDesktop().browse(new URI(result[1]));
                } else {
                    JOptionPane.showMessageDialog(null, "valla a la url siguiente : " + result[1]);
                }
                this.estado = -2;
            } catch (URISyntaxException | IOException | HeadlessException e) {
            }
        }
    }

}
