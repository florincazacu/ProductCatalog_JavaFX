package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.model.DataSource;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("main.fxml"));
        Parent root = loader.load();
        Controller controller = loader.getController();
        controller.listProducts();
        primaryStage.setTitle("Product Catalog");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }

    @Override
    public void init() throws Exception {
        super.init();
        if (!DataSource.getInstance().open()) {
            System.out.println("Cannot connect to DB");
        }
//        DataSource.getInstance().createColorsTable();
//        DataSource.getInstance().createCategoriesTable();
//        DataSource.getInstance().createBrandsTable();
//        DataSource.getInstance().createProductsTable();
//        DataSource.getInstance().insertCategory();
//        DataSource.getInstance().insertBrand();
//        DataSource.getInstance().insertProduct();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        DataSource.getInstance().close();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
