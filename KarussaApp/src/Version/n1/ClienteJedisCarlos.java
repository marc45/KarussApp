package Version.n1;

import java.awt.Desktop;
import java.awt.HeadlessException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisPubSub;

public class ClienteJedisCarlos {

    JedisPool jp;
    JedisPoolConfig jpc;
    Jedis cliente;
    String canales[];
    JedisPubSub jsp;
    NanozNotificacion nn;
    int estado;
    //loggerKarussa lk = new loggerKarussa();
    

    public ClienteJedisCarlos(String ip, String canal) {
        this.jpc = new JedisPoolConfig();
        this.jpc.setTestWhileIdle(true);
        this.jpc.setMaxIdle(1000);
        this.jp = new JedisPool(jpc, ip);
        this.cliente = new Jedis(ip);
        System.out.println(cliente.isConnected());
        //cliente.connect();
        //cliente.disconnect();
        this.canales = new String[1];
        canales[0] = canal;
    }
    
    
    public boolean iniciar_conexion()
    {
        try {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    jsp = new JedisPubSub() {
                        @Override
                        public void onMessage(String channel, String message) {
                            //lk.nivelINFO("llego un mensaje");
                            //lk.nivelINFO(message);
                            String result[] = new String[3];
                            result = jsonToString.recorrer(message, result);
                            System.out.println(result[0]);
                            System.out.println(result[1]);
                            System.out.println(result[2]);
                            nn = new NanozNotificacion(result[1], result[0]);
                            nn.setVisible(true);
                            //estado = JOptionPane.showConfirmDialog(null, result[0]);
                            //notificacionCiclada(result, estado);
                        }

                        @Override
                        public void onPMessage(String pattern, String channel, String message) {
                            System.out.println("Opcion2 de mensajes, " + pattern + " canal " + channel + " mensaje: " + message);
                        }

                        @Override
                        public void onSubscribe(String channel, int subscribedChannels) {
                            System.out.println("se unio al canal: " + channel + " " + subscribedChannels);
                        }

                        @Override
                        public void onUnsubscribe(String channel, int subscribedChannels) {
                            System.out.println("salio del canal: " + channel + " " + subscribedChannels);
                        }

                        @Override
                        public void onPUnsubscribe(String pattern, int subscribedChannels) {
                            System.out.println("hizo algo con : " + pattern + " " + subscribedChannels);
                        }

                        @Override
                        public void onPSubscribe(String pattern, int subscribedChannels) {
                            System.out.println("hizo algo con : " + pattern + " " + subscribedChannels);
                        }

                    };
                    cliente.subscribe(jsp, canales);
                    System.out.println("conectado");
                }
            }, "Hilo de Jedis").start();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Se a perdido la coneccion. Reconectar?");
            new clienteJedis(this.ip, this.canal);
        }        
    }

    private void notificacionCiclada(String[] result, int estado) {
        //lk.nivelINFO("Tomando Respuesta Notificacion");
        if (estado == 0) {
            try {
                //lk.nivelINFO("Vemos si podemos abrir el navegador");
                if (Desktop.isDesktopSupported()) {
                    Desktop.getDesktop().browse(new URI(result[1]));
                    //lk.nivelINFO("se abrio el navegador");
                } else {
                    JOptionPane.showMessageDialog(null, "valla a la url siguiente : " + result[1]);
                    //lk.nivelWARNING("no se nos permite abrir el navegador");
                }
                this.estado = -2;
            } catch (URISyntaxException | IOException | HeadlessException e) {
                //lk.nivelSEVERE("Algo salio mal al intentar abrir el navegador");
                //lk.nivelSEVERE(e.getMessage() + "/" + e.getLocalizedMessage());
            }
        }
    }

}
