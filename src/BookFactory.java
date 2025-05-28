public class BookFactory {
    public static Livre creerLivre(String titre, String auteur, Categorie cat) {
        switch (cat) {
            case REFERENCE: return new LivreReferentielle(titre, auteur);
            default: return new LivreOrdinaire(titre, auteur);
        }
    }
}
