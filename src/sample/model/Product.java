package sample.model;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.Date;

public class Product {

    private SimpleIntegerProperty id;
    private SimpleStringProperty name;
    private SimpleStringProperty description;
    private SimpleDoubleProperty price;
    private SimpleStringProperty colour;
    private SimpleBooleanProperty inStock;
    private Date expiringDate;
    private SimpleStringProperty categoryName;
    private SimpleIntegerProperty categoryId;

    public Product() {
        this.id = new SimpleIntegerProperty();
        this.name = new SimpleStringProperty();
        this.description = new SimpleStringProperty();
        this.price = new SimpleDoubleProperty();
        this.colour = new SimpleStringProperty();
        this.inStock = new SimpleBooleanProperty();
        this.categoryName = new SimpleStringProperty();
        this.categoryId = new SimpleIntegerProperty();
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

    public void setColour(String colour) {
        this.colour.set(colour);
    }

    public void setInStock(boolean inStock) {
        this.inStock.set(inStock);
    }

    public void setExpiringDate(Date date) {
        this.expiringDate = date;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName.set(categoryName);
    }

    public void setCategoryId(int categoryId) {
        this.categoryId.set(categoryId);
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

    public String getColour() {
        return colour.get();
    }

    public boolean getInStock() {
        return inStock.get();
    }

    public Date getExpiringDate(){
        return expiringDate;
    }

    public String getCategoryName(){
        return categoryName.get();
    }

    public int getCategoryId() {
        return categoryId.get();
    }

}
