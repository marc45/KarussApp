package Version.n1;

import redis.clients.jedis.JedisPubSub;

public class KarussaJedisPubSub extends JedisPubSub implements Runnable {
    NanozNotificacion nn=null;

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

    @Override
    public void run() 
    {
        try 
        {
            System.out.println("Se subscribio al canal, el hilo se bloqueara. ");
        } catch (Exception e) 
        {
            System.out.println("Fallo la subscripcion: " + e.toString());
        }
    }
    
}
