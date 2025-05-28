import java.util.ArrayList;
import java.util.List;

public class Bibliotheque {
    private static Bibliotheque instance = null;
    private List<Livre> livres = new ArrayList<>();
    private List<Adherent> adherents = new ArrayList<>();
    private Bibliotheque() {}
    public static Bibliotheque getInstance() {
        if (instance == null) {
            instance = new Bibliotheque();
        }
        return instance;
    }
    public List<Livre> getLivres() { return livres; }
    public List<Adherent> getAdherents() { return adherents; }
    // Méthodes pour ajouter, supprimer, rechercher...
}


