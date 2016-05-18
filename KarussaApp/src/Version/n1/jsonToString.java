/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Version.n1;

import java.util.Iterator;
import org.json.JSONObject;

/**
 *
 * @author rkrd_
 */
public class jsonToString {

    //static loggerKarussa lk = new loggerKarussa();
    public static String[] recorrer(String raizJson, String[] result) {
        try {
            JSONObject json = new JSONObject(raizJson);
            Iterator<?> nivel = json.keys();
            while (nivel.hasNext()) {
                String value = (String) nivel.next();
                //System.out.println(value);

                int x = json.get(value).toString().lastIndexOf('{');
                int y = json.get(value).toString().lastIndexOf('[');
                if (x > -1) {
                    recorrer(json.get(value).toString(), result);
                } else {
                    switch (value) {
                        case "mensaje":
                            result[0] = json.getString(value);
                            break;
                        case "url_video":
                            result[1] = "http://" + json.getString(value);
                            break;
                        case "imagen":
                            result[2] = value;
                            break;
                    }
                }
            }
        } catch (Exception e) {
            //lk.nivelSEVERE("algo paso mientras se recorria el json");
            //lk.nivelSEVERE(e.getMessage() + "/" + e.getLocalizedMessage());
        }
        return result;
    }
}
