public class Emprunte extends EtatLivre {
    @Override
    public void retourner() {
        livre.setState(new Disponible());
        System.out.println("Livre retourné et devient disponible.");
        // Notifier les réservataires
        livre.notifierObservateurs();
    }
}