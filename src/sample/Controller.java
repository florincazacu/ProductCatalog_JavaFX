package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import sample.model.Constants;
import sample.model.DataSource;
import sample.model.Product;


public class Controller {
    @FXML
    private TableView<Product> productsTable;

    public void listProducts() {
        Task<ObservableList<Product>> task = new GetAllProductsTask();
        productsTable.itemsProperty().bind(task.valueProperty());
        new Thread(task).start();
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

class GetAllCategoriesClass extends Task {
    @Override
    protected Object call() throws Exception {
        return null;
    }
}