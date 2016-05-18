/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Version.n1;

import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import javax.swing.JOptionPane;

/**
 *
 * @author rkrd_
 */
public class configuracion {

    RandomAccessFile doc1;
    RandomAccessFile doc2;
    RandomAccessFile doc3;

    public configuracion() {
        try {
            doc1 = new RandomAccessFile("./init1.txt", "r");
            doc2 = new RandomAccessFile("./init2.txt", "r");
            doc3 = new RandomAccessFile("./init3.txt", "r");
        } catch (FileNotFoundException e) {
        }
    }

    public String DameUrl() {
        try {
            return doc1.readLine();
        } catch (Exception e) {
            return JOptionPane.showInputDialog("dame la ip ");
        }
    }

    public String Direccion() {
        try {
            return doc2.readLine();
        } catch (Exception e) {
            return "";
        }
    }

    public String Canal() {
        try {
            return doc3.readLine();
        } catch (Exception e) {
            return "";
        }
    }
}
