package Version.n1;

import java.net.MalformedURLException;
import java.net.URL;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javax.swing.JFrame;

public class panelNavegador extends JFXPanel {

    WebEngine navegador;
    WebView view;
    String URL;

    public panelNavegador(String newURL) {
        setVisible(true);
        this.setSize(300, 300);
        this.URL = newURL;
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                System.out.println("iniciando");
                view = new WebView();
                navegador = view.getEngine();
                setScene(new Scene(view));
                System.out.println("");
                loadURL(URL);
            }
        });
    }

    public void loadURL(String url) {
        System.out.println("entrando a cargar url");
        String tmp = toURL(url);
        System.out.println(tmp);
        if (tmp == null) {
            tmp = toURL(url);
        }
        navegador.load(tmp);
        System.out.println("termino");
    }

    private static String toURL(String str) {
        try {
            return new URL(str).toExternalForm();
        } catch (MalformedURLException exception) {
            System.out.println(exception);
            return null;
        }
    }

    public panelNavegador() {
    }

    public panelNavegador(WebEngine navegador, WebView view, String URL) {
        this.view = view;
        this.navegador = navegador;
        this.URL = URL;
    }

    public static void main(String[] args) {
        JFrame padre = new JFrame();
        padre.setVisible(true);
        padre.add(new panelNavegador("http://www.yogaoficina.com"));
    }
}
