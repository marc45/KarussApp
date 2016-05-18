package Version.n1;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisPubSub;

public class JedisPubSubThread extends Thread
{
    public static String IP="104.131.146.146";    
    private static JedisPubSubThread jedispst=null;
    private JedisPool pool_conexiones=null;
    private JedisPoolConfig jpc;
    private Jedis cliente = null;
    private String canales[];
    private KarussaJedisPubSub jsp=null;
    private NanozNotificacion nn;
    private int estado;
    //loggerKarussa lk = new loggerKarussa();        
    //System.out.println(cliente.isConnected());
        
    private JedisPubSubThread(String nombre) 
    {
        super(nombre);
        getConfiguracion();
        getPoolConexiones();
        getKarussaJedisPubSub();
    }
    
    public static JedisPubSubThread getInstance()
    {
        if(jedispst == null)
        {
            System.out.println("Creando por primera vez el objeto...");
            jedispst = new JedisPubSubThread("notifs-karussa");
        }
        return jedispst;        
    }
    
    public  JedisPool getPoolConexiones()
    {
        if(pool_conexiones == null)
        {
            getConfiguracion();
            this.pool_conexiones = new JedisPool(this.jpc, IP);            
        }
        return this.pool_conexiones;
    }
    
    public JedisPubSub getKarussaJedisPubSub()
    {
        System.out.println("METODO getKarussaJedisPubSub");
        if(jsp == null)
        {
            this.jsp = new KarussaJedisPubSub();
        }
        return this.jsp;        
    }
        
    public Jedis getCliente()
    {
        try 
        {
            //obtenemos la conexion a el servidor, y asi se crea un cliente
            System.out.println("METODO getCliente");
            return this.pool_conexiones.getResource();          
        } 
        catch(Exception e)
        {
            System.out.println("getCliente error: "+e.toString());
            return null;
        }
    }
    
    public void conectar(String canal)
    {
        this.canales = new String[1];
        canales[0] = canal;
        cliente.subscribe(jsp, canales);
        System.out.println("metodo conectar");
    }
    

    public void getConfiguracion()
    {
        this.jpc = new JedisPoolConfig();
        this.jpc.setTestWhileIdle(true);
        this.jpc.setMaxIdle(1000);        
    }

    public void run() {
        System.out.println("Corriendo JedisPubSubThread");
        final JedisPubSubThread jpst= JedisPubSubThread.getInstance();

        System.out.println("crearemos el cliente");
        jpst.cliente = jpst.getCliente();
        System.out.println("jpst vale : "+jpst.cliente);
        while(jpst.cliente==null)
        {
            try 
            {
                this.sleep(5000);
            } catch (InterruptedException ex) 
            {
                ex.printStackTrace();
            }
            jpst.cliente = jpst.getCliente();
            System.out.println("Intentando volver a conectar en 5 segundos---");
        }
        System.out.println("Se creo el cliente..");

        final String canal = "karussa|usuario__id:1";
        boolean conexion = true;
        //jpst.conectar(canal);
        while(true)
        {            
            
        }
            
    }
    
    public static void main(String args[])
    {
        JedisPubSubThread jedisPubSub = JedisPubSubThread.getInstance();
        jedisPubSub.start();

    }
}

