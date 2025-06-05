import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class LibraryFacade {
    private Bibliotheque bib = Bibliotheque.getInstance();
    private FineStrategy fineStrategy = new AmendeFixe(5); // 1 unité par jour

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
        System.out.print("Catégorie (GENERALE/REFERENCE/AVENTURE/HISTOIRE/ENFANT): ");
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
    public void afficherListeLivres() {
        List<Livre> livres = Bibliotheque.getInstance().getLivres();
        if (livres.isEmpty()) {
            System.out.println("Aucun livre enregistré.");
            return;
        }

        System.out.println("\n--- Liste des Livres ---");
        for (Livre livre : livres) {
            String titre = livre.getTitre();
            String auteur = livre.getAuteur();
            String categorie = livre.getCategorie().toString();
            String etat = livre.getState().getClass().getSimpleName();
            System.out.printf("Titre: %-25s Auteur: %-15s Catégorie: %-10s État: %s\n",
                    titre, auteur, categorie, etat);
        }
    }

    public void afficherListeAdherents() {
        List<Adherent> adherents = Bibliotheque.getInstance().getAdherents();
        if (adherents.isEmpty()) {
            System.out.println("Aucun adhérent enregistré.");
            return;
        }

        System.out.println("\n--- Liste des Adhérents ---");
        for (Adherent a : adherents) {
            System.out.printf("ID: %-3d Nom: %s\n", a.getId(), a.getNom());
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
                System.out.println("Livre '" + l.getTitre() + "' : amende = " + amende+"£");
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
// Partie à ajouter dans LibraryFacade.java

/*import javax.swing.*;
import java.util.Arrays;
import java.util.List;
public class LibraryFacade {
    private Bibliotheque bib = Bibliotheque.getInstance();
    private FineStrategy fineStrategy = new AmendeFixe(1.0);

    public void loadSampleData() {
        bib.getAdherents().add(new Adherent("Alice"));
        bib.getAdherents().add(new Adherent("Bob"));
        bib.getLivres().add(BookFactory.creerLivre("1984", "Orwell", Categorie.GENERALE));
        bib.getLivres().add(BookFactory.creerLivre("Dictionnaire", "Larousse", Categorie.REFERENCE));
    }
    public List<Livre> rechercherLivres(String motCle) {
        return bib.getLivres().stream()
                .filter(l -> l.getTitre().contains(motCle)
                        || l.getAuteur().contains(motCle))
                .collect(Collectors.toList());
    }

    public void ajouterLivre() {
        String titre = JOptionPane.showInputDialog("Titre du livre :");
        String auteur = JOptionPane.showInputDialog("Auteur :");
        String[] categories = {"GENERALE", "REFERENCE"};
        String catStr = (String) JOptionPane.showInputDialog(null, "Catégorie :",
                "Choix de catégorie", JOptionPane.QUESTION_MESSAGE, null, categories, categories[0]);

        if (titre != null && auteur != null && catStr != null) {
            Categorie cat = Categorie.valueOf(catStr.toUpperCase());
            Livre l = BookFactory.creerLivre(titre, auteur, cat);
            bib.getLivres().add(l);
            JOptionPane.showMessageDialog(null, "Livre ajouté avec succès !");
        }
    }

    public void supprimerAdherent() {
        String idStr = JOptionPane.showInputDialog("ID de l'adhérent à supprimer :");
        try {
            int id = Integer.parseInt(idStr);
            Adherent toRemove = bib.getAdherents().stream()
                    .filter(a -> a.getId() == id).findFirst().orElse(null);
            if (toRemove != null) {
                bib.getAdherents().remove(toRemove);
                JOptionPane.showMessageDialog(null, "Adhérent supprimé.");
            } else {
                JOptionPane.showMessageDialog(null, "Adhérent non trouvé.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "ID invalide.");
        }
    }

    public void emprunterLivre() {
        String titre = JOptionPane.showInputDialog("Titre du livre à emprunter :");
        String idStr = JOptionPane.showInputDialog("ID de l'adhérent :");
        try {
            int id = Integer.parseInt(idStr);
            Livre livre = bib.getLivres().stream()
                    .filter(l -> l.getTitre().equalsIgnoreCase(titre)).findFirst().orElse(null);
            Adherent adherent = bib.getAdherents().stream()
                    .filter(a -> a.getId() == id).findFirst().orElse(null);
            if (livre != null && adherent != null) {
                livre.emprunter(adherent);
                adherent.emprunterLivre(livre);
                JOptionPane.showMessageDialog(null, "Livre emprunté avec succès.");
            } else {
                JOptionPane.showMessageDialog(null, "Livre ou adhérent introuvable.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erreur : " + e.getMessage());
        }
    }

    public void retournerLivre() {
        String titre = JOptionPane.showInputDialog("Titre du livre à retourner :");
        Livre livre = bib.getLivres().stream()
                .filter(l -> l.getTitre().equalsIgnoreCase(titre)).findFirst().orElse(null);
        if (livre != null) {
            try {
                livre.retourner();
                JOptionPane.showMessageDialog(null, "Livre retourné.");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Erreur : " + e.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(null, "Livre non trouvé.");
        }
    }

    public void reserverLivre() {
        String titre = JOptionPane.showInputDialog("Titre du livre à réserver :");
        String idStr = JOptionPane.showInputDialog("ID de l'adhérent :");
        try {
            int id = Integer.parseInt(idStr);
            Livre livre = bib.getLivres().stream()
                    .filter(l -> l.getTitre().equalsIgnoreCase(titre)).findFirst().orElse(null);
            Adherent adherent = bib.getAdherents().stream()
                    .filter(a -> a.getId() == id).findFirst().orElse(null);
            if (livre != null && adherent != null) {
                livre.ajouterObservateur(adherent);
                JOptionPane.showMessageDialog(null, "Réservation enregistrée. Vous serez notifié.");
            } else {
                JOptionPane.showMessageDialog(null, "Livre ou adhérent introuvable.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "ID invalide.");
        }
    }

    public void calculerAmendes() {
        String joursStr = JOptionPane.showInputDialog("Nombre de jours de retard :");
        try {
            int jours = Integer.parseInt(joursStr);
            StringBuilder sb = new StringBuilder("--- Amendes ---\n");
            for (Livre l : bib.getLivres()) {
                if (l.getState() instanceof Emprunte) {
                    double amende = fineStrategy.calculerAmende(jours);
                    sb.append("Livre : ").append(l.getTitre()).append(" | Amende : ").append(amende).append("\n");
                }
            }
            JOptionPane.showMessageDialog(null, sb.toString());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Nombre de jours invalide.");
        }
    }

    public void afficherListeLivres() {
        StringBuilder sb = new StringBuilder("--- Liste des Livres ---\n");
        for (Livre l : bib.getLivres()) {
            sb.append("Titre : ").append(l.getTitre())
                    .append(" | Auteur : ").append(l.getAuteur())
                    .append(" | Catégorie : ").append(l.getCategorie())
                    .append(" | État : ").append(l.getState().getClass().getSimpleName())
                    .append("\n");
        }
        JOptionPane.showMessageDialog(null, sb.toString());
    }

    public void afficherListeAdherents() {
        StringBuilder sb = new StringBuilder("--- Liste des Adhérents ---\n");
        for (Adherent a : bib.getAdherents()) {
            sb.append("ID : ").append(a.getId()).append(" | Nom : ").append(a.getNom()).append("\n");
        }
        JOptionPane.showMessageDialog(null, sb.toString());
    }

    public static LibraryFacade getInstance() {
        return instance;
    }

    private static final LibraryFacade instance = new LibraryFacade();
}*/
