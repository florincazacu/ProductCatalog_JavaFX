package sample.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Color {

    private SimpleIntegerProperty id;
    private SimpleStringProperty name;

    public Color() {
        this.id = new SimpleIntegerProperty();
        this.name = new SimpleStringProperty();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public int getId(){
        return id.get();
    }

    public String getName(){
        return name.get();
    }

    @Override
    public String toString() {
        return name.get();
    }
}
