package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import sample.model.*;

public class DialogController {
    @FXML
    public TextField addProductNameTextField;
    @FXML
    public ComboBox<Category> addProductCategoryComboBox;
    @FXML
    public ComboBox<Brand> addProductBrandComboBox;
    @FXML
    public ComboBox<Color> addProductColorComboBox;

    public void initialize() {
        ObservableList<Category> categories = FXCollections.observableList(
                FXCollections.observableArrayList(DataSource.getInstance().queryCategories(Constants.ORDER_ASC)));
        addProductCategoryComboBox.setItems(categories);

        ObservableList<Brand> brands = FXCollections.observableList(
                FXCollections.observableArrayList(DataSource.getInstance().queryBrands(Constants.ORDER_ASC)));
        addProductBrandComboBox.setItems(brands);

        ObservableList<Color> colors = FXCollections.observableList(
                FXCollections.observableArrayList(DataSource.getInstance().queryColors(Constants.ORDER_ASC)));
        addProductColorComboBox.setItems(colors);

    }
}
