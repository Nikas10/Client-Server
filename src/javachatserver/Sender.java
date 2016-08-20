/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javachatserver;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javachatapp.Message;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;



/**
 *
 * @author Nikas
 */
public class Sender extends Thread{
    private ConnManager cn;
    private static int msgid;
    private ObjectOutputStream out;
    private String path=System.getProperty("user.dir");
    private Connection conn = null;
    private EntityManagerFactory emfactory;
    private EntityManager entitymanager;
    public ConnManager getCn() {
        return cn;
    }

    public void setCn(ConnManager cn) {
        this.cn = cn;
    }
    
    public Sender(ConnManager cnm)
    {
        cn=cnm;
        msgid=1;
        emfactory = Persistence.createEntityManagerFactory("JavaChatServerPU");
        entitymanager = emfactory.createEntityManager( );
        
        setDaemon(true);
        setPriority(MIN_PRIORITY);
        start();
    };
    public void run()
    {
        while (cn.isAlive())
        {
                //if (!cn.getServers().isEmpty())
                //for (Server s:cn.getServers())
               // {
                    if (!cn.getMsglog().isEmpty()){
                    try {
                        for (Server ss:cn.getServers())
                        {                        
                            out = new ObjectOutputStream(ss.getS().getOutputStream());
                            if (cn.getMsglog().get(0).getMsgRec().equals(ss.getID()))
                            {
                                Message msg = cn.getMsglog().get(0);
                                out.writeObject(msg); // отсылаем введенную строку текста серверу.
                                out.flush(); // заставляем поток закончить передачу данных.
                                try {
                                    //HERE COMES JPA TEST PART
                                    /*entitymanager.getTransaction( ).begin( );		
                                    Msglog message = new Msglog();
                                    message.setId(msgid++);
                                    message.setSender(msg.getUsrID());
                                    message.setReceiver(msg.getMsgRec());
                                    message.setMessage(msg.getUsrMsg());
                                    message.setDate(msg.getMsgDate());
                                    entitymanager.persist(message);
                                    entitymanager.getTransaction( ).commit( );*/ 
                                   // Msglog test = entitymanager.find(Msglog.class, 1);
                                    //System.out.println(test.getMessage());
                                      
                                    
                                    
                                    //CODE AFTER THAT IS SQLITE VERSION
                                    conn = DriverManager.getConnection("jdbc:sqlite:"+path+"\\dist\\serverDb.sqlite");
                                    String s = "insert into msglog(SENDER,MESSAGE,RECEIVER,DATE) values ("+"'"+msg.getUsrID()+"'"+","+"'"+msg.getUsrMsg()+"'"+","+"'"+msg.getMsgRec()+"'"+","+"'"+msg.getMsgDate()+"'"+")";
                                    Statement statement = conn.createStatement();
                                    statement.setQueryTimeout(30);  // set timeout to 30 sec.
                                    statement.execute(s);
                                    conn.close();
                                } catch (SQLException ex) {
                                    Logger.getLogger(Sender.class.getName()).log(Level.SEVERE, null, ex);
                                }         
                                
                            }else
                             if ((cn.getMsglog().get(0).getUsrID().equals(ss.getID())))
                            {
                                Message msg = cn.getMsglog().get(0);
                                out.writeObject(msg); // отсылаем введенную строку текста серверу.
                                out.flush(); // заставляем поток закончить передачу данных.                              
                            }
                            
                        }
                        
                        cn.getMsglog().remove(0);
                    } catch (IOException ex) {
                        Logger.getLogger(Sender.class.getName()).log(Level.SEVERE, null, ex);
                        break;
                    }
                  //}
                }
        }
        
                                    
        entitymanager.close( );
        emfactory.close( );
    };
    
}
