package mx.edu.utez.fastfood.fastfood.model;

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

    public Ingredient(long id) {
        this.id = id;
    }

    public Ingredient(long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Ingredient{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
