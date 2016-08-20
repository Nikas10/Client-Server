/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javachatserver;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Nikas
 */
@Entity
@Table(name = "MSGLOG")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Msglog.findAll", query = "SELECT m FROM Msglog m"),
    @NamedQuery(name = "Msglog.findById", query = "SELECT m FROM Msglog m WHERE m.id = :id"),
    @NamedQuery(name = "Msglog.findBySender", query = "SELECT m FROM Msglog m WHERE m.sender = :sender"),
    @NamedQuery(name = "Msglog.findByReceiver", query = "SELECT m FROM Msglog m WHERE m.receiver = :receiver"),
    @NamedQuery(name = "Msglog.findByMessage", query = "SELECT m FROM Msglog m WHERE m.message = :message"),
    @NamedQuery(name = "Msglog.findByDate", query = "SELECT m FROM Msglog m WHERE m.date = :date")})
public class Msglog implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id 
    //@GeneratedValue(strategy=GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "MSG_ID")
    private Integer id;
    @Column(name = "SENDER")
    private String sender;
    @Column(name = "RECEIVER")
    private String receiver;
    @Column(name = "MESSAGE")
    private String message;
    @Column(name = "DATE")
    private String date;

    public Msglog() {
        
    }

    public Msglog(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Msglog)) {
            return false;
        }
        Msglog other = (Msglog) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "javachatserver.Msglog[ id=" + id + " ]";
    }
    
}
