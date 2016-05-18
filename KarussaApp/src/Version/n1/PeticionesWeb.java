package Version.n1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.InetAddress;
import java.net.NetworkInterface;

public class PeticionesWeb {

    public PeticionesWeb(String URL) {
        this.POST_URL = URL;
    }
    private static String USER_AGENT = "Mozilla/5.0";
    //static loggerKarussa lk = new loggerKarussa();
    static configuracion conf = new configuracion();
    static String GET_URL;

    static String POST_URL;
    static InetAddress ip;

    private static String POST_PARAMS;

    private static void sendGET() throws IOException {
        GET_URL = "http://" + conf.DameUrl() + "/loginJava/";
        URL obj = new URL(GET_URL);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", USER_AGENT);
        int responseCode = con.getResponseCode();
        System.out.println("GET Response Code :: " + responseCode);
        if (responseCode == HttpURLConnection.HTTP_OK) { // success
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
        } else {
            //lk.nivelWARNING("no funciona peticion GET");
        }

    }

    public static String sendPOST(String User, String pass) throws Exception {
        POST_URL = "http://" + conf.DameUrl() + "/loginJava/";
        URL obj = new URL(POST_URL);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", USER_AGENT);
        String MAC = "";
        String IP = "";
        
        String ip2 = "";

        try {
            ip = InetAddress.getLocalHost();
            NetworkInterface network = NetworkInterface.getByInetAddress(ip);
            IP = ip.getHostAddress();
            //System.out.println("ip: " + IP);
            URL whatismyip = new URL("http://checkip.amazonaws.com");
            BufferedReader in = new BufferedReader(new InputStreamReader(
                            whatismyip.openStream()));

            ip2 = in.readLine(); //you get the IP as a String
            byte[] mac = network.getHardwareAddress();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < mac.length; i++) {
                sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
            }
            MAC = sb.toString();
            //System.out.println("mac: " + MAC);
        } catch (Exception e) {
            //System.out.println(e);
        }
        POST_PARAMS = "username=" + User + "&password=" + pass + "&ipPublica=" + ip2 +"&ipPrivada=" + IP + "&mac=" + MAC;
        con.setDoOutput(true);
        OutputStream os = con.getOutputStream();
        os.write(POST_PARAMS.getBytes());
        os.flush();
        os.close();
        int responseCode = con.getResponseCode();
        //lk.nivelINFO("empezando peticion post");
        //lk.nivelINFO("parametros: " + POST_PARAMS);
        //lk.nivelINFO("Codigo Respuesta " + responseCode);
        if (responseCode == HttpURLConnection.HTTP_OK) { //success
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            System.out.println(response);
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
                System.out.println(inputLine);
            }
            //lk.nivelINFO("Respuesta POST" + response.toString());
            in.close();
            return response.toString();
        } else {
            //lk.nivelWARNING("no funciona peticion POST");
            return "-1";
        }
    }

}
