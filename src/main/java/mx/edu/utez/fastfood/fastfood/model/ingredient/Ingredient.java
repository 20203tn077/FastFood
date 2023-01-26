package mx.edu.utez.fastfood.fastfood.model.ingredient;

public class Ingredient {
    long id;
    String name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Ingredient() {
    }

    public Ingredient(long id, String name) {
        this.id = id;
        this.name = name;
    }
}
