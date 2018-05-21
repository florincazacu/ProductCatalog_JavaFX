package sample.model;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Constants {

    static final String PRODUCTS_TABLE = "products";
    static final String COLUMN_PRODUCT_ID = "_id";
    static final String COLUMN_PRODUCT_NAME = "name";
    static final String COLUMN_PRODUCT_PRICE = "price";
    static final String COLUMN_PRODUCT_DESCRIPTION = "description";
    static final String COLUMN_PRODUCT_COLOR = "color";
    static final String COLUMN_PRODUCT_IN_STOCK = "in_stock";
    static final String COLUMN_PRODUCT_CATEGORY_ID = "category_id";
    static final String COLUMN_PRODUCT_CATEGORY_NAME = "category_name";
    static final String COLUMN_PRODUCT_BRAND_ID = "brand_id";
    static final String COLUMN_PRODUCT_BRAND_NAME = "brand_name";

    static final int INDEX_PRODUCT_ID = 1;
    static final int INDEX_PRODUCT_NAME = 2;
    static final int INDEX_PRODUCT_PRICE = 3;
    static final int INDEX_PRODUCT_DESCRIPTION = 4;
    static final int INDEX_PRODUCT_COLOR = 5;
    static final int INDEX_PRODUCT_IN_STOCK = 6;
    static final int INDEX_PRODUCT_CATEGORY_ID = 7;
    static final int INDEX_PRODUCT_CATEGORY_NAME = 8;
    static final int INDEX_PRODUCT_BRAND_ID = 9;
    static final int INDEX_PRODUCT_BRAND_NAME = 10;

    static final String CATEGORIES_TABLE = "categories";
    static final String COLUMN_CATEGORY_ID = "_id";
    static final String COLUMN_CATEGORY_NAME = "name";

    public static final int INDEX_CATEGORY_ID = 1;
    public static final int INDEX_CATEGORY_NAME = 2;

    static final String BRANDS_TABLE = "brands";
    static final String COLUMN_BRAND_ID = "_id";
    static final String COLUMN_BRAND_NAME = "name";

    public static final int INDEX_BRAND_ID = 1;
    public static final int INDEX_BRAND_NAME = 2;

    static final int ORDER_DEFAULT = 1;
    public static final int ORDER_ASC = 2;
    static final int ORDER_DESC = 3;

    private static final Path DB_PATH = Paths.get("DB");
    private static final String DB_NAME = "productCatalog.db";
    static final String CONNECTION_STRING = "jdbc:sqlite:" + DB_PATH.toAbsolutePath() + "\\" + DB_NAME;

    static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS ";
    static final String _ID = " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, ";
    static final String INSERT = "INSERT INTO ";

    static final String QUERY_PRODUCTS = "SELECT " + PRODUCTS_TABLE + "." + COLUMN_PRODUCT_ID + ", " + PRODUCTS_TABLE + "." + COLUMN_PRODUCT_NAME + ", " + PRODUCTS_TABLE + "." + COLUMN_PRODUCT_PRICE + ", " +
        PRODUCTS_TABLE + "." + COLUMN_PRODUCT_DESCRIPTION + ", " + PRODUCTS_TABLE + "." + COLUMN_PRODUCT_COLOR + ", " + PRODUCTS_TABLE + "." + COLUMN_PRODUCT_IN_STOCK + ", " +
        PRODUCTS_TABLE + "." + COLUMN_PRODUCT_CATEGORY_ID + ", " +
        CATEGORIES_TABLE + "." + COLUMN_CATEGORY_NAME + " AS " + COLUMN_PRODUCT_CATEGORY_NAME + ", " +
        PRODUCTS_TABLE + "." + COLUMN_PRODUCT_BRAND_ID + ", " +
        BRANDS_TABLE + "." + COLUMN_BRAND_NAME + " AS " + COLUMN_PRODUCT_BRAND_NAME +
        " FROM ((" + PRODUCTS_TABLE +
        " INNER JOIN " + CATEGORIES_TABLE + " ON " + PRODUCTS_TABLE + "." + COLUMN_PRODUCT_CATEGORY_ID + " = " + CATEGORIES_TABLE + "." + COLUMN_CATEGORY_ID + ")" +
        " INNER JOIN " + BRANDS_TABLE + " ON " + PRODUCTS_TABLE + "." + COLUMN_PRODUCT_BRAND_ID + " = " + BRANDS_TABLE + "." + COLUMN_BRAND_ID + ")";

    static final String QUERY_CATEGORIES = "SELECT * FROM " + CATEGORIES_TABLE;

    static final String QUERY_SORT_BY_BRAND_NAME = " ORDER BY + " + BRANDS_TABLE + "." + COLUMN_BRAND_NAME + " COLLATE NOCASE ";
    static final String QUERY_SORT_BY_CATEGORY_NAME = " ORDER BY + " + CATEGORIES_TABLE + "." + COLUMN_CATEGORY_NAME + " COLLATE NOCASE ";
    static final String QUERY_SORT_BY_PRODUCT_NAME = " ORDER BY + " + PRODUCTS_TABLE + "." + COLUMN_PRODUCT_NAME + " COLLATE NOCASE ";

    public static final String QUERY_BRAND = "SELECT " + COLUMN_BRAND_ID + " FROM " +
            BRANDS_TABLE + " WHERE " + COLUMN_BRAND_NAME + " = ?";

    public static final String INSERT_BRAND = INSERT + BRANDS_TABLE +
            " (" + COLUMN_BRAND_NAME + ") VALUES(?)";

    public static final String INSERT_CATEGORY = INSERT + CATEGORIES_TABLE +
            " (" + COLUMN_CATEGORY_NAME + ") VALUES(?)";

    public static final String QUERY_CATEGORY = "SELECT " + COLUMN_CATEGORY_ID + " FROM " +
            CATEGORIES_TABLE + " WHERE " + COLUMN_CATEGORY_NAME + " = ?";

    public static final String INSERT_PRODUCT = INSERT + PRODUCTS_TABLE +
            "(" + COLUMN_PRODUCT_NAME + ", " + COLUMN_PRODUCT_PRICE + ", " + COLUMN_PRODUCT_DESCRIPTION  + ", "+ COLUMN_PRODUCT_COLOR + ", " + COLUMN_PRODUCT_IN_STOCK + ", " +
            COLUMN_PRODUCT_CATEGORY_ID + ", " + COLUMN_PRODUCT_BRAND_ID +
            ") VALUES(?, ?, ?, ?, ?, ?, ?)";
}
