package sample.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Brand {

    private SimpleIntegerProperty id;
    private SimpleStringProperty name;

    public Brand() {
        this.id = new SimpleIntegerProperty();
        this.name = new SimpleStringProperty();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public int getId() {
        return id.get();
    }

    public String getName() {
        return name.get();
    }
}
