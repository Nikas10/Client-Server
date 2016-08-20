/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javachatserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javachatapp.Message;
/**
 *
 * @author Nikas
 */
public class ConnManager extends Thread{
    private int i;
    private ServerSocket ss; 
    private Socket socket;
    private static volatile boolean online;
    private  ArrayList<Server> servers;
    private volatile ArrayList<Message> msglog;

    public ServerSocket getSs() {
        return ss;
    }

    public void setSs(ServerSocket ss) {
        this.ss = ss;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public ArrayList<Server> getServers() {
        return servers;
    }

    public void setServers(ArrayList<Server> servers) {
        this.servers = servers;
    }
    
    public static boolean isOnline() {
        return online;
    }

    public static void setOnline(boolean online) {
        ConnManager.online = online;
    }
    
    public ConnManager(int port) throws IOException
    {
        msglog = new ArrayList<Message>();
        i=0; 
        online = true;
        servers= new ArrayList<Server>();
        ss=new ServerSocket(port);
        setDaemon(true);
        setPriority(MAX_PRIORITY);
        start();
    };

    public ArrayList<Message> getMsglog() {
        return msglog;
    }

    public void setMsglog(ArrayList<Message> msglog) {
        this.msglog = msglog;
    }
    
    public void kill() throws IOException
    {
      socket.close();
      //socket=null;
      ss.close();
      setOnline(false);
      //ss=null;
    };
               
    @Override
    public void run() {
        
            while (ConnManager.isOnline() == true){
                try {
                    
                
                        socket=new Socket();
                        socket=ss.accept();
                        Server srv = new Server(socket,msglog);
                        for (Server s:servers)
                        {
                            if (s.isAlive()==false)
                            {servers.remove(s);break;}
                        }
                        servers.add(srv);   
                        
                  
                } catch(Exception x) { x.printStackTrace(); }
            }              
        try {
            ss.close();
            System.out.println("ss closed");
            Server.setOnline(false);
        } catch (IOException ex) {
            Logger.getLogger(ConnManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}
