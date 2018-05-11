package sample.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataSource {

    private static DataSource instance = new DataSource();
    private Connection connection;

    private DataSource() {

    }

    public static DataSource getInstance() {
        return instance;
    }

    public boolean open() {
        try {
            connection = DriverManager.getConnection(
                    Constants.CONNECTION_STRING,
                    "user",
                    "pass"
            );
            return true;
        } catch (SQLException e) {
            System.out.println("open error: " + e.getMessage());
            return false;
        }
    }

    public void close() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println("Couldn't close DataSource: " + e.getMessage());
        }
    }

    public void createProductsTable() {
        String createProductsTable = Constants.CREATE_TABLE + Constants.PRODUCTS_TABLE +
                " (" + Constants.COLUMN_PRODUCT_ID + Constants._ID +
                Constants.COLUMN_PRODUCT_NAME + " text, " +
                Constants.COLUMN_PRODUCT_DESCRIPTION + " text, " +
                Constants.COLUMN_PRODUCT_PRICE + " decimal" +
                ")";

        System.out.println("create products table: " + createProductsTable);

        try (PreparedStatement ps = connection.prepareStatement(createProductsTable)) {
            ps.execute();
        } catch (SQLException e) {
            System.out.println("Error creating products table: " + e.getMessage());
        }
    }

    public void createCategoriesTable() {
        String createCategoriesTable = Constants.CREATE_TABLE + Constants.CATEGORIES_TABLE +
                " (" + Constants.COLUMN_CATEGORY_ID + Constants._ID +
                Constants.COLUMN_CATEGORY_NAME + " text)";
        System.out.println("create categories table " + createCategoriesTable);

        try (PreparedStatement ps = connection.prepareStatement(createCategoriesTable)) {
            ps.execute();
        } catch (SQLException e) {
            System.out.println("Error creating categories table: " + e.getMessage());
        }
    }


    public void insertProduct() {
        String insertProduct = Constants.INSERT + Constants.PRODUCTS_TABLE +
                " (" + Constants.COLUMN_PRODUCT_NAME + ", " +
                Constants.COLUMN_PRODUCT_DESCRIPTION + ", " +
                Constants.COLUMN_PRODUCT_PRICE + ") " +
                "VALUES('name_test', 'description_test', '50.00')";

        System.out.println("insert: " + insertProduct);

        try (PreparedStatement ps = connection.prepareStatement(insertProduct)) {
            ps.execute();
        } catch (SQLException e) {
            System.out.println("Error adding product: " + e.getMessage());
        }
    }

    public void insertCategory() {
        String insertCategory = Constants.INSERT + Constants.CATEGORIES_TABLE +
                " (" + Constants.COLUMN_CATEGORY_NAME + ") " +
                "VALUES('cat_name_test')";

        System.out.println("insert: " + insertCategory);

        try (PreparedStatement ps = connection.prepareStatement(insertCategory)) {
            ps.execute();
        } catch (SQLException e) {
            System.out.println("Error adding product: " + e.getMessage());
        }
    }

    public StringBuilder sortQuery(int sortOrder, StringBuilder stringBuilder) {
        if (sortOrder != Constants.ORDER_DEFAULT) {
            stringBuilder.append(Constants.QUERY_PRODUCTS_SORT_BY_NAME);
            if (sortOrder == Constants.ORDER_DESC) {
                stringBuilder.append("DESC");
            } else {
                stringBuilder.append("ASC");
            }
        }
        return stringBuilder;
    }

    public List<Product> queryProducts(int sortOrder) {
        StringBuilder stringBuilder = new StringBuilder(Constants.QUERY_PRODUCTS);

        try (PreparedStatement ps = connection.prepareStatement(sortQuery(sortOrder, stringBuilder).toString());
             ResultSet results = ps.executeQuery()) {
            List<Product> products = new ArrayList<>();
            while (results.next()) {
                Product product = new Product();
                int id = results.getInt(Constants.INDEX_PRODUCT_ID);
                String name = results.getString(Constants.INDEX_PRODUCT_NAME);
                String desc = results.getString(Constants.INDEX_PRODUCT_DESCRIPTION);
                double price = results.getDouble(Constants.INDEX_PRODUCT_PRICE);
                product.setId(id);
                product.setName(name);
                product.setDescription(desc);
                product.setPrice(price);
                products.add(product);
            }

            return products;
        } catch (SQLException e) {
            System.out.println("Query products failed: " + e.getMessage());
            return null;
        }
    }

    public List<Category> queryCategories(int sortOrder) {
        StringBuilder stringBuilder = new StringBuilder(Constants.QUERY_CATEGORIES);

        try (PreparedStatement ps = connection.prepareStatement(sortQuery(sortOrder, stringBuilder).toString());
             ResultSet results = ps.executeQuery()) {
            List<Category> categories = new ArrayList<>();
            while (results.next()) {
                Category category = new Category();
                int id = results.getInt(Constants.INDEX_PRODUCT_ID);
                String name = results.getString(Constants.INDEX_PRODUCT_NAME);
                category.setId(id);
                category.setName(name);
                categories.add(category);
            }
            return categories;
        } catch (SQLException e) {
            System.out.println("Query categories failed: " + e.getMessage());
            return null;
        }
    }

}
