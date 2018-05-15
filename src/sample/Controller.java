package sample;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableView;
import sample.model.Constants;
import sample.model.DataSource;
import sample.model.Product;

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

    public void exit() {
        Platform.exit();
    }

    public void listProducts() {
        previousPageButton.setText("<");
        totalPagesLabel.setText("100 pages");
        totalProductsLabel.setText("Total 100 products");
        Task<ObservableList<Product>> task = new GetAllProductsTask();
        productsTable.itemsProperty().bind(task.valueProperty());
        new Thread(task).start();
    }

    public void insertProduct(){
        final Product product = new Product();

        product.setName("P20");
        product.setDescription("Mobile phone");
        product.setPrice(100.00);
        product.setColor("Black");
        product.setInStock(true);

        DataSource.getInstance().insertProduct("Huawei", "Smartphone",product);
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