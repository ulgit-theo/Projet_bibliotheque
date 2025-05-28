// Exemple de sous-classe de Livre pour catégorie reference
public class LivreReferentielle extends Livre {
    public LivreReferentielle(String titre, String auteur) {
        super(titre, auteur, Categorie.REFERENCE);
    }
    // On peut ajouter des comportements spécifiques (ex. interdiction de prêter)
}