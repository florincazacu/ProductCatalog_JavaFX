package sample.model;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Product {

    private SimpleIntegerProperty id;
    private SimpleStringProperty name;
    private SimpleStringProperty description;
    private SimpleDoubleProperty price;
    private SimpleBooleanProperty inStock;

    private SimpleIntegerProperty colorId;
    private SimpleStringProperty colorName;

    private SimpleIntegerProperty categoryId;
    private SimpleStringProperty categoryName;

    private SimpleIntegerProperty brandId;
    private SimpleStringProperty brandName;


    public Product() {
        this.id = new SimpleIntegerProperty();
        this.name = new SimpleStringProperty();
        this.description = new SimpleStringProperty();
        this.price = new SimpleDoubleProperty();
        this.inStock = new SimpleBooleanProperty();
        this.colorId = new SimpleIntegerProperty();
        this.colorName = new SimpleStringProperty();
        this.categoryName = new SimpleStringProperty();
        this.categoryId = new SimpleIntegerProperty();
        this.brandId = new SimpleIntegerProperty();
        this.brandName = new SimpleStringProperty();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public void setPrice(double price) {
        this.price.set(price);
    }

    public void setColorName(String colorName) {
        this.colorName.set(colorName);
    }

    public void setInStock(boolean inStock) {
        this.inStock.set(inStock);
    }

    public void setCategoryName(String categoryName) {
        this.categoryName.set(categoryName);
    }

    public void setColorId(int colorId) {
        this.colorId.set(colorId);
    }

    public void setCategoryId(int categoryId) {
        this.categoryId.set(categoryId);
    }

    public void setBrandId(int brandId) {
        this.brandId.set(brandId);
    }

    public void setBrandName(String brandName) {
        this.brandName.set(brandName);
    }

    public int getId() {
        return id.get();
    }

    public String getName() {
        return name.get();
    }

    public String getDescription() {
        return description.get();
    }

    public double getPrice() {
        return price.get();
    }

    public String getColorName() {
        return colorName.get();
    }

    public boolean getInStock() {
        return inStock.get();
    }

    public int getColorId() {
        return colorId.get();
    }

    public int getCategoryId() {
        return categoryId.get();
    }

    public String getCategoryName(){
        return categoryName.get();
    }

    public int getBrandId() {
        return brandId.get();
    }

    public String getBrandName() {
        return brandName.get();
    }

}
