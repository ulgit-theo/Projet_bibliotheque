import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        LibraryFacade facade = LibraryFacade.getInstance();
        facade.loadSampleData(); // initialisation exemple
        Scanner sc = new Scanner(System.in);
        boolean exit = false;
        while (!exit) {
            System.out.println("\n=== Menu Bibliothèque (Console) ===");
            System.out.println("1. Ajouter un livre");
            System.out.println("2. Supprimer un adhérent");
            System.out.println("3. Emprunter un livre");
            System.out.println("4. Retourner un livre");
            System.out.println("5. Réserver un livre");
            System.out.println("6. Calculer amendes");
            System.out.println("7. Lancer interface graphique JavaFX");
            System.out.println("8. Quitter");
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
                case 8: exit = true; break;
                default: System.out.println("Choix invalide");
            }
        }
        sc.close();
        LibraryFacade.getInstance().saveState(); // sauvegarde éventuelle
    }
}
