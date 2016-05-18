package Version.n1;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import javax.swing.JOptionPane;

public class serializador {

    FileOutputStream fos = null;
    ObjectOutputStream salida = null;
    FileInputStream fis = null;
    ObjectInputStream entrada = null;
    static objetoSerializable p;
    //static loggerKarussa lk = new loggerKarussa();

    public void serializar(String user, String pass) {
        try {
            //lk.nivelINFO("parese que los datos son utiles los serializaremos");
            File file;
            String SistemaOperativo = System.getProperty("os.name").toLowerCase();
            if (SistemaOperativo.matches(".*windows.*")) {
                file = new File("C:/Users/" + System.getProperty("user.name") + "/Documents/KarussaApp");
            } else if (SistemaOperativo.matches(".*linux.*")) {
                file = new File("/home/" + System.getProperty("user.name") + "/.karussaapp");
            } else {
                file = new File("/Users/" + System.getProperty("user.name") + "/.karussaapp");
            }
            file.mkdirs();
            RandomAccessFile archivo = new RandomAccessFile(file.getAbsolutePath() + "/setings.bin", "rw");
            archivo.writeBytes("");
            archivo.close();
            //lk.nivelINFO("creamos los flujos de salida");
            fos = new FileOutputStream(file.getAbsolutePath() + "/setings.bin");
            salida = new ObjectOutputStream(fos);
            p = new objetoSerializable(user, pass);
            salida.writeObject(p);
            //lk.nivelINFO("Terminamos de escribir");
        } catch (FileNotFoundException e) {
            //lk.nivelSEVERE("no se pudo crear el archivo");
            //lk.nivelSEVERE(e.getMessage() + "/" + e.getLocalizedMessage());
        } catch (IOException e) {
            //lk.nivelSEVERE("no se pudo escribir en el archivo");
            //lk.nivelSEVERE(e.getMessage() + "/" + e.getLocalizedMessage());
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                    //lk.nivelINFO("cerramos el FOS");
                }
                if (salida != null) {
                    salida.close();
                    //lk.nivelINFO("cerramos OOS");
                }
            } catch (IOException e) {
                //lk.nivelSEVERE("algo raro sucedio al cerrar los flujos");
                //lk.nivelSEVERE(e.getMessage() + "/" + e.getLocalizedMessage());
            }
        }

    }

    public String[] leerArchivo() {
        String[] datos = new String[2];
        try {
            //lk.nivelINFO("empezando a leer el archivo");
            File file;
            String SistemaOperativo = System.getProperty("os.name").toLowerCase();
            if (SistemaOperativo.matches(".*windows.*")) {
                file = new File("C:/Users/" + System.getProperty("user.name") + "/Documents/KarussaApp");
            } else if (SistemaOperativo.matches(".*linux.*")) {
                file = new File("/home/" + System.getProperty("user.name") + "/.karussaapp");
            } else {
                file = new File("/Users/" + System.getProperty("user.name") + "/.karussaapp");
            }
            file.mkdirs();
            fis = new FileInputStream(file.getAbsolutePath() + "/setings.bin");
            entrada = new ObjectInputStream(fis);
            p = (objetoSerializable) entrada.readObject(); //es necesario el casting
            datos[0] = p.getUsuario();
            datos[1] = p.getPassword();
            //lk.nivelINFO("se termino de leer el archivo");
        } catch (IOException | ClassNotFoundException e) {
            //JOptionPane.showMessageDialog(null, e.getCause());
            //JOptionPane.showMessageDialog(null, e.getClass());
            //JOptionPane.showMessageDialog(null, e.getMessage());
            //lk.nivelSEVERE("algo raro paso mientras leiamos el archivo");
            //lk.nivelSEVERE(e.getMessage() + "/" + e.getLocalizedMessage());
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
                if (entrada != null) {
                    entrada.close();
                }
                //lk.nivelINFO("se cerro el archivo");
            } catch (IOException e) {
                //lk.nivelSEVERE("algo raro paso mientras cerrabamos el archivo");
                //lk.nivelSEVERE(e.getMessage() + "/" + e.getLocalizedMessage());

            }
        }
        //lk.nivelINFO("devolviendo lectura");
        return datos;
    }
}
