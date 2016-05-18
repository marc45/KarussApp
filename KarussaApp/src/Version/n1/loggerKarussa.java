package Version.n1;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.FileHandler;
import javax.swing.JOptionPane;

public class loggerKarussa {

    final Logger LOGGER = Logger.getLogger(getClass().getName());

    public loggerKarussa() {
        try {
            FileHandler fileXml = new FileHandler("./Logging.xml");
            LOGGER.addHandler(fileXml);
        } catch (IOException | SecurityException e) {
            JOptionPane.showConfirmDialog(null, e.getMessage());
        }

    }

    //Mostrar mensajes informativos
    public void nivelINFO(String Mensaje) {
        LOGGER.log(Level.INFO, Mensaje);
    }

    //Mostrar mensajes de configuración
    public void nivelCONFIG(String Mensaje) {
        LOGGER.log(Level.CONFIG, Mensaje);
    }

    //Mostrar mensajes de depuración
    public void nivelFINE(String Mensaje) {
        LOGGER.log(Level.FINE, Mensaje);
    }

    //Mostrar mensajes de alerta
    public void nivelWARNING(String Mensaje) {
        LOGGER.log(Level.WARNING, Mensaje);
    }

    //Mostrar mensajes críticos
    public void nivelSEVERE(String Mensaje) {
        LOGGER.log(Level.SEVERE, Mensaje);
    }

}
