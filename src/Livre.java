import java.util.ArrayList;
import java.util.List;

// Classe abstraite Livre (Subject pour Observer)
public abstract class Livre {
    private String titre;
    private String auteur;
    private Categorie categorie;
    private Adherent currentAdherent;
    private EtatLivre state;
    private List<Adherent> observateurs = new ArrayList<>(); // réservataires

    public Livre(String titre, String auteur, Categorie cat) {
        this.titre = titre; this.auteur = auteur; this.categorie = cat;
        this.state = new Disponible(); state.livre = this;
    }
    // Getters/setters...
    public void setState(EtatLivre state) {
        this.state = state;
        this.state.livre = this;
    }
    public void emprunter(Adherent a) throws Exception {
        state.emprunter(a);
    }
    public void retourner() throws Exception {
        state.retourner();
    }
    // Observer pattern: gérer réservations
    public void ajouterObservateur(Adherent a) {
        observateurs.add(a);
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public void setAdherent(Adherent currentAdherent) {
        this.currentAdherent = currentAdherent;
    }

    public void notifierObservateurs() {
        for (Adherent a : observateurs) {
            System.out.println("Notification à " + a.getNom() + " : "
                    + "Le livre '" + titre + "' est à nouveau disponible.");
        }
        observateurs.clear();
    }

    public String getTitre() {
        return titre;
    }

    public String getAuteur() {
        return auteur;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public Adherent getCurrentAdherent() {
        return currentAdherent;
    }

    public EtatLivre getState() {
        return state;
    }
}