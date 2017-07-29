/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package co.com.greenApp.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author wsalazar@ias.com.co
 */
@Entity
@Table(name = "foro", catalog = "greenapp", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Foro.findAll", query = "SELECT f FROM Foro f")
    , @NamedQuery(name = "Foro.findByIdForo", query = "SELECT f FROM Foro f WHERE f.idForo = :idForo")
    , @NamedQuery(name = "Foro.findByTitle", query = "SELECT f FROM Foro f WHERE f.title = :title")})
public class Foro implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idForo", nullable = false)
    private Integer idForo;
    @Basic(optional = false)
    @Column(name = "title", nullable = false, length = 100)
    private String title;
    @Basic(optional = false)
    @Lob
    @Column(name = "description", nullable = false, length = 65535)
    private String description;
    @JoinColumn(name = "idModule", referencedColumnName = "idModule", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Module idModule;
    @JoinColumn(name = "idUser", referencedColumnName = "idUser", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private User idUser;

    public Foro() {
    }

    public Foro(Integer idForo) {
        this.idForo = idForo;
    }

    public Foro(Integer idForo, String title, String description) {
        this.idForo = idForo;
        this.title = title;
        this.description = description;
    }

    public Integer getIdForo() {
        return idForo;
    }

    public void setIdForo(Integer idForo) {
        this.idForo = idForo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Module getIdModule() {
        return idModule;
    }

    public void setIdModule(Module idModule) {
        this.idModule = idModule;
    }

    public User getIdUser() {
        return idUser;
    }

    public void setIdUser(User idUser) {
        this.idUser = idUser;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idForo != null ? idForo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Foro)) {
            return false;
        }
        Foro other = (Foro) object;
        if ((this.idForo == null && other.idForo != null) || (this.idForo != null && !this.idForo.equals(other.idForo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.greenApp.entities.Foro[ idForo=" + idForo + " ]";
    }

}
