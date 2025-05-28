
// États possibles d’un livre (State Pattern simplifié)
public abstract class EtatLivre {
    protected Livre livre;
    public void emprunter(Adherent a) throws Exception {
        throw new Exception("Opération impossible en l'état actuel.");
    }
    public void retourner() throws Exception {
        throw new Exception("Opération impossible en l'état actuel.");
    }
}





