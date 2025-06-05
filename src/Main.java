import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        LibraryFacade facade = LibraryFacade.getInstance();
        facade.loadSampleData(); // initialisation exemple
        Scanner sc = new Scanner(System.in);
        boolean exit = false;
        while (!exit) {
            System.out.println("\n=== Menu Bibliothèque ===");
            System.out.println("1. Ajouter un livre");
            System.out.println("2. Supprimer un adhérent");
            System.out.println("3. Emprunter un livre");
            System.out.println("4. Retourner un livre");
            System.out.println("5. Réserver un livre");
            System.out.println("6. Calculer amendes");
            System.out.println("7. Lancer interface graphique JavaFX (pas encore implémenté correctement)");
            System.out.println("8. Afficher la liste des livres");
            System.out.println("9. Afficher la liste des adhérents");

            System.out.println("10. Quitter");
            System.out.print("Choix: ");
            int choix = sc.nextInt();
            sc.nextLine();
            switch (choix) {
                case 1: facade.ajouterLivreConsole(sc); break;
                case 2: facade.supprimerAdherentConsole(sc); break;
                case 3: facade.emprunterLivreConsole(sc); break;
                case 4: facade.retournerLivreConsole(sc); break;
                case 5: facade.reserverLivreConsole(sc); break;
                case 6: facade.calculerAmendesConsole(); break;
                case 7: ApplicationFX.launchApp(); break; // lance l'UI JavaFX

                case 8:
                    facade.afficherListeLivres();
                    break;
                case 9:
                    facade.afficherListeAdherents();
                    break;
                case 10: exit = true; break;

                default: System.out.println("Choix invalide");
            }
        }
        sc.close();
        LibraryFacade.getInstance().saveState(); // sauvegarde éventuelle
    }
}
/*import javax.swing.JOptionPane;

public class Main {

    public static void main(String[] args) {
        LibraryFacade facade = LibraryFacade.getInstance();
        facade.loadSampleData(); // Initialisation de test

        boolean quitter = false;

        while (!quitter) {
            String[] options = {
                    "1. Ajouter un livre",
                    "2. Supprimer un adhérent",
                    "3. Emprunter un livre",
                    "4. Retourner un livre",
                    "5. Réserver un livre",
                    "6. Calculer amendes",
                    "7. Afficher liste livres",
                    "8. Afficher liste adhérents",
                    "9. Quitter"
            };

            String choix = (String) JOptionPane.showInputDialog(
                    null,
                    "Menu Bibliothèque - Choisissez une action",
                    "Système de Bibliothèque",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[0]);

            if (choix == null || choix.contains("9")) {
                quitter = true;
                break;
            }

            try {
                if (choix.contains("1")) facade.ajouterLivre();
                else if (choix.contains("2")) facade.supprimerAdherent();
                else if (choix.contains("3")) facade.emprunterLivre();
                else if (choix.contains("4")) facade.retournerLivre();
                else if (choix.contains("5")) facade.reserverLivre();
                else if (choix.contains("6")) facade.calculerAmendes();
                else if (choix.contains("7")) facade.afficherListeLivres();
                else if (choix.contains("8")) facade.afficherListeAdherents();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Erreur : " + e.getMessage());
            }
        }

        JOptionPane.showMessageDialog(null, "Merci d'avoir utilisé le système !");
    }
}*/

