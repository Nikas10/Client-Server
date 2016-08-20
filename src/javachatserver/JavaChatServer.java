/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javachatserver;
import java.io.*;
import javax.swing.JFrame;


/**
 *
 * @author Nikas
 */
public class JavaChatServer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        // TODO code application logic here
        JFrame mainfr = new JFrame();
        mainfr.getContentPane().add(new Panel());
        mainfr.pack();
        mainfr.setVisible(true);
        mainfr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        
       
    }
    
}
