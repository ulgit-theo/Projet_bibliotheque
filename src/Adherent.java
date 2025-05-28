import java.util.ArrayList;
import java.util.List;

public class Adherent {
    private static int compteur = 0;
    private int id;
    private String nom;
    private List<Livre> livresEmpruntes = new ArrayList<>();

    public Adherent(String nom) {
        this.id = ++compteur;
        this.nom = nom;
    }
    public int getId() { return id; }
    public String getNom() { return nom; }
    public void emprunterLivre(Livre l) {
        livresEmpruntes.add(l);
    }
    public void rendreLivre(Livre l) {
        livresEmpruntes.remove(l);
    }
    // pour l'Observer : recevoir une notification
    public void notifier(String message) {
        System.out.println("Notification (" + nom + ") : " + message);
    }
}


