public class Disponible extends EtatLivre {
    @Override
    public void emprunter(Adherent a) {
        livre.setAdherent(a);
        livre.setState(new Emprunte());
        System.out.println("Livre emprunté avec succès.");
    }
}