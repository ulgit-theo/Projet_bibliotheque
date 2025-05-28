public class MemberFactory {
    public static Adherent creerAdherent(String nom) {
        return new Adherent(nom);
    }
}