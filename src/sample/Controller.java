package sample;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import sample.model.Constants;
import sample.model.DataSource;
import sample.model.Product;

import java.io.IOException;
import java.util.Optional;

public class Controller {
    @FXML
    public ProgressBar progressBar;
    @FXML
    public Button previousPageButton;
    @FXML
    public Label totalProductsLabel;
    @FXML
    public Label totalPagesLabel;
    @FXML
    private TableView<Product> productsTable;
    @FXML
    private BorderPane mainBorderPane;

    public void exit() {
        Platform.exit();
    }

    public void initialize() {
        previousPageButton.setText("<");
        totalPagesLabel.setText("100 pages");
        totalProductsLabel.setText("Total 100 products");
    }

    public void listProducts() {
        Task<ObservableList<Product>> task = new GetAllProductsTask();
        productsTable.itemsProperty().bind(task.valueProperty());
        new Thread(task).start();
    }

    public void insertProduct() {
        final Product product = new Product();

        product.setName("P20");
        product.setDescription("Mobile phone");
        product.setPrice(100.00);
        product.setColor("Black");
        product.setInStock(true);

        DataSource.getInstance().insertProduct("Huawei", "Smartphone", product);
    }

    @FXML
    public void showAddProductDialog() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainBorderPane.getScene().getWindow());
        try {
            Parent root = FXMLLoader.load(getClass().getResource("addProductDialog.fxml"));
            dialog.getDialogPane().setContent(root);
        } catch (IOException e) {
            System.out.println("Couldn't load the dialog: " + e.getMessage());
            e.printStackTrace();
            return;
        }

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get().equals(ButtonType.OK)) {
            System.out.println("OK Pressed");
        } else {
            System.out.println("Cancel pressed");
        }
    }
}

class GetAllProductsTask extends Task {
    @Override
    public ObservableList<Product> call() {
        return FXCollections.observableArrayList(
                DataSource.getInstance().queryProducts(Constants.ORDER_ASC)
        );
    }
}