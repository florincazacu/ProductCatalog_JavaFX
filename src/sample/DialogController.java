package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import sample.model.Category;
import sample.model.Constants;
import sample.model.DataSource;

public class DialogController {
    @FXML
    public TextField addProductNameTextField;
    @FXML
    public ComboBox<Category> addProductCategoryComboBox;

    public void initialize() {
        Task<ObservableList<Category>> categoriesList = new GetAllCategoriesTask();
        addProductCategoryComboBox.itemsProperty().bind(categoriesList.valueProperty());
        new Thread(categoriesList).start();
    }
}

class GetAllCategoriesTask extends Task {
    @Override
    public ObservableList<Category> call() {
        return FXCollections.observableArrayList(
                DataSource.getInstance().queryCategories(Constants.ORDER_ASC)
        );
    }
}
