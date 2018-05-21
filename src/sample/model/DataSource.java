package sample.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataSource {

    private static DataSource instance = new DataSource();
    private Connection connection;

    private PreparedStatement insertIntoColors;
    private PreparedStatement insertIntoBrands;
    private PreparedStatement insertIntoCategories;
    private PreparedStatement insertIntoProducts;

    private PreparedStatement queryColor;
    private PreparedStatement queryBrand;
    private PreparedStatement queryCategory;

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

            insertIntoColors = connection.prepareStatement(Constants.INSERT_COLOR, Statement.RETURN_GENERATED_KEYS);
            insertIntoBrands = connection.prepareStatement(Constants.INSERT_BRAND, Statement.RETURN_GENERATED_KEYS);
            insertIntoCategories = connection.prepareStatement(Constants.INSERT_CATEGORY, Statement.RETURN_GENERATED_KEYS);
            insertIntoProducts = connection.prepareStatement(Constants.INSERT_PRODUCT);

            queryColor = connection.prepareStatement(Constants.QUERY_COLOR);
            queryBrand = connection.prepareStatement(Constants.QUERY_BRAND);
            queryCategory = connection.prepareStatement(Constants.QUERY_CATEGORY);

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
                Constants.COLUMN_PRODUCT_IN_STOCK + " boolean, " +
                Constants.COLUMN_PRODUCT_COLOR_ID + " integer " + " REFERENCES " + Constants.COLORS_TABLE + "(" + Constants.COLUMN_COLOR_ID + "), " +
                Constants.COLUMN_PRODUCT_CATEGORY_ID + " integer " + " REFERENCES " + Constants.CATEGORIES_TABLE + "(" + Constants.COLUMN_CATEGORY_ID + "), " +
//                Constants.COLUMN_PRODUCT_CATEGORY_NAME + " text, " +
                Constants.COLUMN_PRODUCT_BRAND_ID + " integer " + " REFERENCES " + Constants.BRANDS_TABLE + "(" + Constants.COLUMN_BRAND_ID + ") " +
//                Constants.COLUMN_PRODUCT_BRAND_NAME + " text " +
                ")";

        System.out.println("create products table: " + createProductsTable);

        try (PreparedStatement ps = connection.prepareStatement(createProductsTable)) {
            ps.execute();
        } catch (SQLException e) {
            System.out.println("Error creating products table: " + e.getMessage());
        }
    }

    public void createColorsTable() {
        String createCategoriesTable = Constants.CREATE_TABLE + Constants.COLORS_TABLE +
                " (" + Constants.COLUMN_COLOR_ID + Constants._ID +
                Constants.COLUMN_COLOR_NAME + " text unique)";
        System.out.println("create categories table " + createCategoriesTable);

        try (PreparedStatement ps = connection.prepareStatement(createCategoriesTable)) {
            ps.execute();
        } catch (SQLException e) {
            System.out.println("Error creating colors table: " + e.getMessage());
        }
    }

    public void createCategoriesTable() {
        String createCategoriesTable = Constants.CREATE_TABLE + Constants.CATEGORIES_TABLE +
                " (" + Constants.COLUMN_CATEGORY_ID + Constants._ID +
                Constants.COLUMN_CATEGORY_NAME + " text unique)";
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
                Constants.COLUMN_BRAND_NAME + " text unique)";
        System.out.println("create brands table " + createCategoriesTable);

        try (PreparedStatement ps = connection.prepareStatement(createCategoriesTable)) {
            ps.execute();
        } catch (SQLException e) {
            System.out.println("Error creating brands table: " + e.getMessage());
        }
    }

//    public void insertProduct() {
//        String addProduct = Constants.INSERT + Constants.PRODUCTS_TABLE +
//                " (" + Constants.COLUMN_PRODUCT_NAME + ", " +
//                Constants.COLUMN_PRODUCT_PRICE + ", " +
//                Constants.COLUMN_PRODUCT_DESCRIPTION + ", " +
//                Constants.COLUMN_PRODUCT_IN_STOCK + ", " +
//                Constants.COLUMN_PRODUCT_COLOR_ID + ", " +
//                Constants.COLUMN_PRODUCT_CATEGORY_ID + ", " +
////                Constants.COLUMN_PRODUCT_CATEGORY_NAME + ", " +
//                Constants.COLUMN_PRODUCT_BRAND_ID +
////                Constants.COLUMN_PRODUCT_BRAND_NAME + ") " +
//                ") " +
//                "VALUES('Galaxy S9', '50.00', 'Samsung Galaxy S9 Plus, Dual SIM, 64GB, 4G, Black', 'true', '1', '1', '1')";
////                "VALUES('Galaxy S9', '50.00', 'Latest Samsung Model', 'Black', 'true', '2', 'category', '1', 'brand')";
//
//        System.out.println("insert: " + addProduct);
//
//        try (PreparedStatement ps = connection.prepareStatement(addProduct)) {
//            ps.execute();
//        } catch (SQLException e) {
//            System.out.println("Error adding product: " + e.getMessage());
//        }
//    }

    public void insertProduct(Product product) {
        try {
            connection.setAutoCommit(false);
            System.out.println("autoCommit false");

            int colorId = insertColor(product.getColorName());
            System.out.println("colorId: " + colorId);
            int brandId = insertBrand(product.getBrandName());
            System.out.println("brandId: " + brandId);
            int categoryId = insertCategory(product.getCategoryName());
            System.out.println("categoryId: " + categoryId);

            insertIntoProducts.setString(1, product.getName());
            insertIntoProducts.setDouble(2, product.getPrice());
            insertIntoProducts.setString(3, product.getDescription());
            insertIntoProducts.setBoolean(4, product.getInStock());
            insertIntoProducts.setInt(5, colorId);
            insertIntoProducts.setInt(6, brandId);
            insertIntoProducts.setInt(7, categoryId);

            int affectedRows = insertIntoProducts.executeUpdate();
            if (affectedRows == 1) {
                connection.commit();
            } else {
                throw new SQLException("Insert product failed");
            }
        } catch (Exception e) {
            System.out.println("Insert product exception: " + e.getMessage());
            try {
                System.out.println("Performing rollback");
                connection.rollback();
            } catch (SQLException e2) {
                System.out.println("Couldn't perform rollback " + e.getMessage());
            }
        } finally {
            try {
                System.out.println("Resetting default commit behaviour");
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                System.out.println("Couldn't reset auto-commit! " + e.getMessage());
            }
        }
    }

    private int insertColor(String colorName) throws SQLException {
        queryColor.setString(1, colorName);
        ResultSet results = queryCategory.executeQuery();
        if (results.next()) {
            return results.getInt(1);
        } else {
            // Insert the category
            insertIntoColors.setString(1, colorName);
            int affectedRows = insertIntoCategories.executeUpdate();
            if (affectedRows != 1) {
                throw new SQLException("Couldn't insert category!");
            }
            ResultSet generatedKeys = insertIntoCategories.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            } else {
                throw new SQLException("Couldn't get _id for category");
            }
        }
    }

    public void insertCategory() {
        String addCategory = Constants.INSERT + Constants.CATEGORIES_TABLE +
                " (" + Constants.COLUMN_CATEGORY_NAME + ") " +
                "VALUES('Smartphone')";

        System.out.println("insert: " + addCategory);

        try (PreparedStatement ps = connection.prepareStatement(addCategory)) {
            ps.execute();
        } catch (SQLException e) {
            System.out.println("Error adding product: " + e.getMessage());
        }
    }

    private int insertCategory(String categoryName) throws SQLException {
        queryCategory.setString(1, categoryName);
        ResultSet results = queryCategory.executeQuery();
        if (results.next()) {
            return results.getInt(1);
        } else {
            // Insert the category
            insertIntoCategories.setString(1, categoryName);
            int affectedRows = insertIntoCategories.executeUpdate();
            if (affectedRows != 1) {
                throw new SQLException("Couldn't insert category!");
            }
            ResultSet generatedKeys = insertIntoCategories.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            } else {
                throw new SQLException("Couldn't get _id for category");
            }
        }
    }

    public void insertBrand() {
        String addBrand = Constants.INSERT + Constants.BRANDS_TABLE +
                " (" + Constants.COLUMN_BRAND_NAME + ") " +
                "VALUES('Samsung')";

        System.out.println("insert: " + addBrand);

        try (PreparedStatement ps = connection.prepareStatement(addBrand)) {
            ps.execute();
        } catch (SQLException e) {
            System.out.println("Error adding product: " + e.getMessage());
        }
    }

    private int insertBrand(String brand) throws SQLException {

        queryBrand.setString(1, brand);
        ResultSet results = queryBrand.executeQuery();

        System.out.println("results.next() " + results.next());

        if (results.next()) {
            return results.getInt(1);
        } else {
            // Insert the brand
            insertIntoBrands.setString(1, brand);
            int affectedRows = insertIntoBrands.executeUpdate();
            if (affectedRows != 1) {
                throw new SQLException("Couldn't insert brand!");
            }
            ResultSet generatedKeys = insertIntoBrands.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            } else {
                throw new SQLException("Couldn't get _id for brand");
            }
        }
    }

    private StringBuilder sortQuery(int sortOrder, StringBuilder stringBuilder, String sortBy) {
        if (sortOrder != Constants.ORDER_DEFAULT) {
            if (sortBy.equals("brandName")) {
                stringBuilder.append(Constants.QUERY_SORT_BY_BRAND_NAME);
            } else if (sortBy.equals("categoryName")) {
                stringBuilder.append(Constants.QUERY_SORT_BY_CATEGORY_NAME);
            } else if (sortBy.equals("productName")) {
                stringBuilder.append(Constants.QUERY_SORT_BY_PRODUCT_NAME);
            } else if (sortBy.equals("colorName")) {
                stringBuilder.append(Constants.QUERY_SORT_BY_COLOR_NAME);
            }

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
        String sorted = sortQuery(sortOrder, stringBuilder, "productName").toString();
        System.out.println("sorted in queryProducts: " + sorted);
        try (PreparedStatement ps = connection.prepareStatement(sorted);
             ResultSet results = ps.executeQuery()) {
            List<Product> products = new ArrayList<>();


            while (results.next()) {
                Product product = new Product();
                product.setId(results.getInt(Constants.INDEX_PRODUCT_ID));
                product.setName(results.getString(Constants.INDEX_PRODUCT_NAME));
                product.setPrice(results.getDouble(Constants.INDEX_PRODUCT_PRICE));
                product.setDescription(results.getString(Constants.INDEX_PRODUCT_DESCRIPTION));
                product.setInStock(Boolean.parseBoolean(results.getString(Constants.INDEX_PRODUCT_IN_STOCK)));
                product.setColorId(results.getInt(Constants.INDEX_PRODUCT_COLOR_ID));
                product.setColorName(results.getString(Constants.INDEX_PRODUCT_COLOR_NAME));
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
        String sorted = sortQuery(sortOrder, stringBuilder, "categoryName").toString();

        System.out.println("sorted in queryCategories: " + sorted);

        try (PreparedStatement ps = connection.prepareStatement(sorted);
             ResultSet results = ps.executeQuery()) {
            List<Category> categories = new ArrayList<>();
            while (results.next()) {
                Category category = new Category();
                int id = results.getInt(Constants.INDEX_CATEGORY_ID);
                String name = results.getString(Constants.INDEX_CATEGORY_NAME);
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

    public List<Brand> queryBrands(int sortOrder) {
        StringBuilder stringBuilder = new StringBuilder(Constants.QUERY_BRANDS);
        String sorted = sortQuery(sortOrder, stringBuilder, "brandName").toString();

        System.out.println("sorted in queryBrands: " + sorted);

        try (PreparedStatement ps = connection.prepareStatement(sorted);
             ResultSet results = ps.executeQuery()) {
            List<Brand> brands = new ArrayList<>();
            while (results.next()) {
                Brand brand = new Brand();
                int id = results.getInt(Constants.INDEX_BRAND_ID);
                String name = results.getString(Constants.INDEX_BRAND_NAME);
                brand.setId(id);
                brand.setName(name);
                brands.add(brand);
            }
            return brands;
        } catch (SQLException e) {
            System.out.println("Query brands failed: " + e.getMessage());
            return null;
        }
    }

    public List<Color> queryColors(int sortOrder) {
        StringBuilder stringBuilder = new StringBuilder(Constants.QUERY_COLORS);
        String sorted = sortQuery(sortOrder, stringBuilder, "colorName").toString();

        System.out.println("sorted in queryColors: " + sorted);

        try (PreparedStatement ps = connection.prepareStatement(sorted);
             ResultSet results = ps.executeQuery()) {
            List<Color> colors = new ArrayList<>();
            while (results.next()) {
                Color color = new Color();
                int id = results.getInt(Constants.INDEX_COLOR_ID);
                String name = results.getString(Constants.INDEX_COLOR_NAME);
                color.setId(id);
                color.setName(name);
                colors.add(color);
            }
            return colors;
        } catch (SQLException e) {
            System.out.println("Query colors failed: " + e.getMessage());
            return null;
        }
    }
}
