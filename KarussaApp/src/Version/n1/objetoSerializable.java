package Version.n1;

import java.io.Serializable;

public class objetoSerializable implements Serializable {

    private String usuario;
    private String password;

    public objetoSerializable(String usuario, String password) {
        this.usuario = usuario;
        this.password = password;
    }

    public String getUsuario() {
        return this.usuario;
    }

    public String getPassword() {
        return this.password;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
