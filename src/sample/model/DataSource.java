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
                Constants.COLUMN_PRODUCT_PRICE + " decimal, " +
                Constants.COLUMN_PRODUCT_DESCRIPTION + " text, " +
                Constants.COLUMN_PRODUCT_COLOR + " text, " +
                Constants.COLUMN_PRODUCT_IN_STOCK + " boolean, " +
                Constants.COLUMN_PRODUCT_CATEGORY_ID + " integer, " +
                Constants.COLUMN_PRODUCT_CATEGORY_NAME + " text, " +
                Constants.COLUMN_PRODUCT_BRAND_ID + " integer, " +
                Constants.COLUMN_PRODUCT_BRAND_NAME + " text " +
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

    public void createBrandsTable() {
        String createCategoriesTable = Constants.CREATE_TABLE + Constants.BRANDS_TABLE +
                " (" + Constants.COLUMN_BRAND_ID + Constants._ID +
                Constants.COLUMN_BRAND_NAME + " text)";
        System.out.println("create brands table " + createCategoriesTable);

        try (PreparedStatement ps = connection.prepareStatement(createCategoriesTable)) {
            ps.execute();
        } catch (SQLException e) {
            System.out.println("Error creating brands table: " + e.getMessage());
        }
    }

    public void insertProduct() {
        String addProduct = Constants.INSERT + Constants.PRODUCTS_TABLE +
                " (" + Constants.COLUMN_PRODUCT_NAME + ", " +
                Constants.COLUMN_PRODUCT_PRICE + ", " +
                Constants.COLUMN_PRODUCT_DESCRIPTION + ", " +
                Constants.COLUMN_PRODUCT_COLOR + ", " +
                Constants.COLUMN_PRODUCT_IN_STOCK + ", " +
                Constants.COLUMN_PRODUCT_CATEGORY_ID + ", " +
                Constants.COLUMN_PRODUCT_CATEGORY_NAME + ", " +
                Constants.COLUMN_PRODUCT_BRAND_ID + ", " +
                Constants.COLUMN_PRODUCT_BRAND_NAME + ") " +
                "VALUES('name', '50.00', 'description', 'color', 'true', '2', 'category', '1', 'brand')";

        System.out.println("insert: " + addProduct);

        try (PreparedStatement ps = connection.prepareStatement(addProduct)) {
            ps.execute();
        } catch (SQLException e) {
            System.out.println("Error adding product: " + e.getMessage());
        }
    }

//    public void insertProduct(String brand, String category, Product product) {
//        try (PreparedStatement preparedStatement = connection.prepareStatement(Constants.INSERT_PRODUCT)) {
//            connection.setAutoCommit(false);
//
//            int brandId = insertBrand(brand);
//            int categoryId = insertCategory(category, brandId);
//            insertIntoSongs.setInt(1, track);
//            insertIntoSongs.setString(2, category);
//            insertIntoSongs.setInt(3, categoryId);
//
//            int affectedRows = insertIntoSongs.executeUpdate();
//            if (affectedRows == 1) {
//                conn.commit();
//            } else {
//                throw new SQLException("The song insert failed");
//            }
//        } catch (Exception e) {
//            System.out.println("Insert song exception: " + e.getMessage());
//            try {
//                System.out.println("Performing rollback");
//                conn.rollback();
//            } catch (SQLException e2) {
//                System.out.println("Oh boy! Things are really bad! " + e.getMessage());
//            }
//        } finally {
//            try {
//                System.out.println("Resetting default commit behaviour");
//                conn.setAutoCommit(true);
//            } catch (SQLException e) {
//                System.out.println("Couldn't reset auto-commit! " + e.getMessage());
//            }
//        }
//    }

    public void insertCategory() {
        String addCategory = Constants.INSERT + Constants.CATEGORIES_TABLE +
                " (" + Constants.COLUMN_CATEGORY_NAME + ") " +
                "VALUES('cat_name_test')";

        System.out.println("insert: " + addCategory);

        try (PreparedStatement ps = connection.prepareStatement(addCategory)) {
            ps.execute();
        } catch (SQLException e) {
            System.out.println("Error adding product: " + e.getMessage());
        }
    }

    public void insertBrand() {
        String addBrand = Constants.INSERT + Constants.BRANDS_TABLE +
                " (" + Constants.COLUMN_BRAND_NAME + ") " +
                "VALUES('brand_name_test')";

        System.out.println("insert: " + addBrand);

        try (PreparedStatement ps = connection.prepareStatement(addBrand)) {
            ps.execute();
        } catch (SQLException e) {
            System.out.println("Error adding product: " + e.getMessage());
        }
    }

    private StringBuilder sortQuery(int sortOrder, StringBuilder stringBuilder) {
        if (sortOrder != Constants.ORDER_DEFAULT) {
            stringBuilder.append(Constants.QUERY_SORT_BY_NAME);
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
//                int id = results.getInt(Constants.INDEX_PRODUCT_ID);
//                String name = results.getString(Constants.INDEX_PRODUCT_NAME);
//                double price = results.getDouble(Constants.INDEX_PRODUCT_PRICE);
//                String desc = results.getString(Constants.INDEX_PRODUCT_DESCRIPTION);
//                String color = results.getString(Constants.INDEX_PRODUCT_COLOR);
//                boolean inStock = results.getBoolean(Constants.INDEX_PRODUCT_IN_STOCK);
//                int categoryId = results.getInt(Constants.INDEX_PRODUCT_CATEGORY_ID);
//                String categoryName = results.getString(Constants.INDEX_PRODUCT_CATEGORY_NAME);
//                int brandId = results.getInt(Constants.INDEX_PRODUCT_BRAND_ID);
//                String brandName = results.getString(Constants.INDEX_PRODUCT_BRAND_NAME);

                product.setId(results.getInt(Constants.INDEX_PRODUCT_ID));
                product.setName(results.getString(Constants.INDEX_PRODUCT_NAME));
                product.setPrice(results.getDouble(Constants.INDEX_PRODUCT_PRICE));
                product.setDescription(results.getString(Constants.INDEX_PRODUCT_DESCRIPTION));
                product.setColor(results.getString(Constants.INDEX_PRODUCT_COLOR));
                product.setInStock(results.getBoolean(Constants.INDEX_PRODUCT_IN_STOCK));
                product.setCategoryId(results.getInt(Constants.INDEX_PRODUCT_CATEGORY_ID));
                product.setCategoryName(results.getString(Constants.INDEX_PRODUCT_CATEGORY_NAME));
                product.setBrandId(results.getInt(Constants.INDEX_PRODUCT_BRAND_ID));
                product.setBrandName(results.getString(Constants.INDEX_PRODUCT_BRAND_NAME));
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
