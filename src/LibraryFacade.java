import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class LibraryFacade {
    private Bibliotheque bib = Bibliotheque.getInstance();
    private FineStrategy fineStrategy = new AmendeFixe(1.0); // 1 unité par jour

    // Initialisation de données de démonstration
    public void loadSampleData() {
        Adherent a1 = MemberFactory.creerAdherent("Alice");
        Adherent a2 = MemberFactory.creerAdherent("Bob");
        bib.getAdherents().add(a1);
        bib.getAdherents().add(a2);
        Livre l1 = BookFactory.creerLivre("1984", "Orwell", Categorie.GENERALE);
        Livre l2 = BookFactory.creerLivre("Dictionnaire", "Larousse", Categorie.REFERENCE);
        bib.getLivres().add(l1);
        bib.getLivres().add(l2);
    }

    // Méthode console simplifiée pour ajouter un livre
    public void ajouterLivreConsole(Scanner sc) {
        System.out.print("Titre du livre: ");
        String titre = sc.nextLine();
        System.out.print("Auteur: ");
        String auteur = sc.nextLine();
        System.out.print("Catégorie (GENERALE/REFERENCE): ");
        Categorie cat = Categorie.valueOf(sc.nextLine().toUpperCase());
        Livre l = BookFactory.creerLivre(titre, auteur, cat);
        bib.getLivres().add(l);
        System.out.println("Livre ajouté.");
    }

    // Suppression adhérent console
    public void supprimerAdherentConsole(Scanner sc) {
        System.out.print("ID de l'adhérent à supprimer: ");
        int id = sc.nextInt(); sc.nextLine();
        Adherent toRemove = null;
        for (Adherent a : bib.getAdherents()) {
            if (a.getId() == id) { toRemove = a; break; }
        }
        if (toRemove != null) {
            bib.getAdherents().remove(toRemove);
            System.out.println("Adhérent supprimé.");
        } else {
            System.out.println("Adhérent non trouvé.");
        }
    }

    // Emprunter un livre (console)
    public void emprunterLivreConsole(Scanner sc) {
        // Exemple simplifié : chercher par titre
        System.out.print("Titre du livre à emprunter: ");
        String titre = sc.nextLine();
        Livre trouve = null;
        for (Livre l : bib.getLivres()) {
            if (l.getTitre().equalsIgnoreCase(titre)) { trouve = l; break; }
        }
        if (trouve == null) {
            System.out.println("Livre non trouvé.");
            return;
        }
        System.out.print("ID adhérent: ");
        int id = sc.nextInt(); sc.nextLine();
        Adherent adh = bib.getAdherents().stream()
                .filter(a -> a.getId() == id).findFirst().orElse(null);
        if (adh == null) {
            System.out.println("Adhérent non trouvé.");
            return;
        }
        try {
            trouve.emprunter(adh);
            adh.emprunterLivre(trouve);
        } catch (Exception e) {
            System.out.println("Impossible d'emprunter: " + e.getMessage());
        }
    }

    // Retourner un livre (console)
    public void retournerLivreConsole(Scanner sc) {
        System.out.print("Titre du livre à retourner: ");
        String titre = sc.nextLine();
        Livre trouve = bib.getLivres().stream()
                .filter(l -> l.getTitre().equalsIgnoreCase(titre))
                .findFirst().orElse(null);
        if (trouve == null) { System.out.println("Livre non trouvé."); return; }
        try {
            trouve.retourner();
        } catch (Exception e) {
            System.out.println("Impossible de retourner: " + e.getMessage());
        }
    }

    // Réserver un livre (console)
    public void reserverLivreConsole(Scanner sc) {
        System.out.print("Titre du livre à réserver: ");
        String titre = sc.nextLine();
        Livre trouve = bib.getLivres().stream()
                .filter(l -> l.getTitre().equalsIgnoreCase(titre))
                .findFirst().orElse(null);
        if (trouve == null) { System.out.println("Livre non trouvé."); return; }
        System.out.print("ID adhérent: ");
        int id = sc.nextInt(); sc.nextLine();
        Adherent adh = bib.getAdherents().stream()
                .filter(a -> a.getId() == id).findFirst().orElse(null);
        if (adh == null) { System.out.println("Adhérent non trouvé."); return; }
        trouve.ajouterObservateur(adh);
        System.out.println("Livre réservé. Vous serez notifié à son retour.");
    }

    // Calculer amendes pour tous les livres empruntés (console)
    public void calculerAmendesConsole() {
        System.out.println("Calcul des amendes (hypothétique) :");
        // Ici on simule la notion de jours de retard par défaut de 3 jours
        int joursRetard = 3;
        for (Livre l : bib.getLivres()) {
            // On suppose trouver l'adhérent et calculer son amende
            if (l.getState() instanceof Emprunte) {
                double amende = fineStrategy.calculerAmende(joursRetard);
                System.out.println("Livre '" + l.getTitre() + "' : amende = " + amende);
            }
        }
    }

    // Méthode console pour afficher info (utilisable dans JavaFX aussi)
    public Livre trouverLivreParTitre(String titre) {
        return bib.getLivres().stream()
                .filter(l -> l.getTitre().equalsIgnoreCase(titre))
                .findFirst().orElse(null);
    }
    public Adherent trouverAdherentParId(int id) {
        return bib.getAdherents().stream()
                .filter(a -> a.getId() == id).findFirst().orElse(null);
    }
    public List<Livre> rechercherLivres(String motCle) {
        return bib.getLivres().stream()
                .filter(l -> l.getTitre().contains(motCle)
                        || l.getAuteur().contains(motCle))
                .collect(Collectors.toList());
    }
    public void supprimerLivre(Livre l) {
        bib.getLivres().remove(l);
    }
    public void supprimerAdherent(Adherent a) {
        bib.getAdherents().remove(a);
    }

    // Méthodes pour lancer l'app JavaFX
    public void launchJavaFX() {
        ApplicationFX.launchApp();
    }

    // Sauvegarde (facultative) : par exemple sérialiser l'état
    public void saveState() {
        // Implémentation fictive ou utile pour persistance
    }

    private static LibraryFacade instance = new LibraryFacade();
    public static LibraryFacade getInstance() {
        return instance;
    }

    public Arrays getAllAdherents() {
        return null;
    }
}