/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javachatserver;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javachatapp.Message;

/**
 *
 * @author Nikas
 */
public class Server extends Thread{

  
    private Socket s;
    private ArrayList<Message> msglog;
    private ObjectInputStream in;
    
    public ArrayList<Message> getMsglog() {
        return msglog;
    }

    public void setMsglog(ArrayList<Message> msglog) {
        this.msglog = msglog;
    }

    public Socket getS() {
        return s;
    }

  
    
    private static boolean online;
    private String ID;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }
    public static boolean isOnline() {
        return online;
    }

    public static void setOnline(boolean onlinen) {
        online = onlinen;
    }
    
   public Server(Socket s,ArrayList<Message> msg) throws IOException
    {
        // копируем данные
        msglog = msg;
        
        this.s = s;
        online = true;
        // и запускаем новый вычислительный поток (см. ф-ю run())
        setDaemon(true);
        setPriority(MIN_PRIORITY);
        start();
        in = new ObjectInputStream(s.getInputStream());
    }

    @Override
    public void run()
    {
        while(Server.isOnline() == true){
       
            try
            {                
                try{
                // создаём строку, содержащую полученную от клиента информацию
                Message msg = (Message) in.readObject();ID = msg.getUsrID();
                System.out.println(msg.toString());                           
                msglog.add(msg);                
                 } catch(SocketException se)
                {System.out.println("disconnect: "+se.toString());Server.setOnline(false);};              
                
            }
            catch(Exception e)
            {e.printStackTrace();} ;// вывод исключений
       
            
        }
        /*try {
           s.close();           
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            
        }*/
    }
    }

    

