package  com.mycompany.myapp.entities;

public class Categorie {
	private int id;
	private String nom;
	
	public Categorie() {
		
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public Categorie(String nom) {
		super();
	 
		this.nom = nom;
	}
	@Override
	public String toString() {
		return "Categorie [id=" + id + ", nom=" + nom + "]";
	}
	

}
