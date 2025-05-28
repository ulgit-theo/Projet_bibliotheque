//package gui;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
/*import modele.Livre;
import modele.Adherent;
import facade.LibraryFacade;*/

public class LibraryController {
    @FXML private TextField searchBookField;
    @FXML private TableView<Livre> tableBooks;
    @FXML private TableColumn<Livre, String> colBookTitle;
    @FXML private TableColumn<Livre, String> colBookAuthor;
    @FXML private TableColumn<Livre, String> colBookCat;

    @FXML private TextField searchMemberField;
    @FXML private TableView<Adherent> tableMembers;
    @FXML private TableColumn<Adherent, Number> colMemberId;
    @FXML private TableColumn<Adherent, String> colMemberName;

    private LibraryFacade facade = LibraryFacade.getInstance();

    @FXML
    public void initialize() {
        // Associer colonnes aux propriétés
        colBookTitle.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getTitre()));
        colBookAuthor.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getAuteur()));
        colBookCat.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getCategorie().toString()));

        colMemberId.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().getId()));
        colMemberName.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getNom()));
    }

    // Recherche de livres et remplissage du tableau
    @FXML
    public void onSearchBooks() {
        String key = searchBookField.getText().trim();
        ObservableList<Livre> data = FXCollections.observableArrayList(facade.rechercherLivres(key));
        tableBooks.setItems(data);
    }
    // Afficher les détails du livre sélectionné
    @FXML
    public void onShowBookDetails() {
        Livre l = tableBooks.getSelectionModel().getSelectedItem();
        if (l != null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Détails du livre");
            alert.setContentText("Titre: " + l.getTitre() + "\nAuteur: " + l.getAuteur()
                    + "\nCatégorie: " + l.getCategorie() + "\nÉtat: " + l.getState().getClass().getSimpleName());
            alert.showAndWait();
        }
    }
    // Supprimer le livre sélectionné
    @FXML
    public void onDeleteBook() {
        Livre l = tableBooks.getSelectionModel().getSelectedItem();
        if (l != null) {
            facade.supprimerLivre(l);
            tableBooks.getItems().remove(l);
        }
    }

    // Recherche d’adhérents
   /* @FXML
    public void onSearchMembers() {
        String key = searchMemberField.getText().trim();
        ObservableList<Adherent> data = FXCollections.observableArrayList(
                facade.getAllAdherents().stream()
                        .filter(a -> String.valueOf(a.getId()).equals(key) || a.getNom().contains(key))
                        .toList());
        tableMembers.setItems(data);
    }*/
    // Afficher les détails de l'adhérent sélectionné
    @FXML
    public void onShowMemberDetails() {
        Adherent a = tableMembers.getSelectionModel().getSelectedItem();
        if (a != null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Détails de l'adhérent");
            alert.setContentText("ID: " + a.getId() + "\nNom: " + a.getNom());
            alert.showAndWait();
        }
    }
    // Supprimer l’adhérent sélectionné
    @FXML
    public void onDeleteMember() {
        Adherent a = tableMembers.getSelectionModel().getSelectedItem();
        if (a != null) {
            facade.supprimerAdherent(a);
            tableMembers.getItems().remove(a);
        }
    }
}
