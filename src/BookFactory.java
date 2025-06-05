public class BookFactory {
    public static Livre creerLivre(String titre, String auteur, Categorie cat) {
        switch (cat) {
            case REFERENCE: return new LivreReferentielle(titre, auteur);
            case AVENTURE: return new LivreAventure(titre, auteur);
            case HISTOIRE: return new LivreHistoire(titre, auteur);
            case ENFANT: return new LivreEnfant(titre, auteur);

            default: return new LivreOrdinaire(titre, auteur);
        }
    }
}
