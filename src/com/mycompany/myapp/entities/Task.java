/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.entities;

/**
 *
 * @author bhk
 */
public class Task {
    private int id,status;
    private String nom,image,descri;
    private String highlights;	
    private Categorie cat;
private int idcat;

    public void setIdcat(int idcat) {
        this.idcat = idcat;
    }

    public int getIdcat() {
        return idcat;
    }

    public Categorie getCat() {
        return cat;
    }

    public void setCat(Categorie cat) {
        this.cat = cat;
    }
    

    public void setHighlights(String highlights) {
        this.highlights = highlights;
    }

    public String getHighlights() {
        return highlights;
    }

    public String getImage() {
        return image;
    }

    public String getDescri() {
        return descri;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setDescri(String descri) {
        this.descri = descri;
    }
    
    
    public Task(int id, int status, String name) {
        this.id = id;
        this.status = status;
        this.nom = name;
    }

    public Task( String name,String descri) {
        this.descri = descri;
        this.nom = name;
    }

    public Task() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String name) {
        this.nom = name;
    }

    @Override
    public String toString() {
        return "Task{" + "id=" + id + ", status=" + status + ", name=" + nom + "\n";
    }
    
    
}
