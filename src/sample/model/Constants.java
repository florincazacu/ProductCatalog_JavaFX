package sample.model;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Constants {

    static final String COLUMN_PRODUCT_ID = "_id";
    static final String COLUMN_PRODUCT_NAME = "name";
    static final String COLUMN_PRODUCT_DESCRIPTION = "description";
    static final String COLUMN_PRODUCT_PRICE = "price";
    static final String COLUMN_CATEGORY_ID = "_id";
    static final String COLUMN_CATEGORY_NAME = "name";
    static final int INDEX_PRODUCT_ID = 1;
    static final int INDEX_PRODUCT_NAME = 2;
    static final int INDEX_PRODUCT_PRICE = 3;
    static final int INDEX_PRODUCT_DESCRIPTION = 4;
    public static final int INDEX_CATEGORY_ID = 1;
    public static final int INDEX_CATEGORY_NAME = 2;
    static final int ORDER_DEFAULT = 1;
    public static final int ORDER_ASC = 2;
    static final int ORDER_DESC = 3;
    private static final Path DB_PATH = Paths.get("DB");
    private static final String DB_NAME = "productCatalog.db";
    static final String CONNECTION_STRING = "jdbc:sqlite:" + DB_PATH.toAbsolutePath() + "\\" + DB_NAME;
    static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS ";
    static final String _ID = " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, ";
    static final String INSERT = "INSERT INTO ";
    static final String PRODUCTS_TABLE = "products";
    static final String CATEGORIES_TABLE = "categories";
    static final String QUERY_PRODUCTS = "SELECT * FROM " + PRODUCTS_TABLE;
    static final String QUERY_CATEGORIES = "SELECT * FROM " + CATEGORIES_TABLE;
    static final String QUERY_PRODUCTS_SORT_BY_NAME = " ORDER BY " + COLUMN_PRODUCT_NAME + " COLLATE NOCASE ";

}
