package mx.edu.utez.fastfood.fastfood.model;

import java.time.LocalDateTime;
import java.util.List;

public class Dish {
    private long id;
    private String name;
    private String description;
    private double price;
    private LocalDateTime registrationDate;
    private boolean status;

    private Category category;
    private List<Ingredient> ingredients;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public Dish() {
    }

    public Dish(long id, String name, String description, double price, LocalDateTime registrationDate, boolean status) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.registrationDate = registrationDate;
        this.status = status;
    }

    public Dish(long id, String name, String description, double price, LocalDateTime registrationDate, boolean status, Category category, List<Ingredient> ingredients) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.registrationDate = registrationDate;
        this.status = status;
        this.category = category;
        this.ingredients = ingredients;
    }

    @Override
    public String toString() {
        return "Dish{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", registrationDate=" + registrationDate +
                ", status=" + status +
                ", category=" + category +
                ", ingredients=" + ingredients +
                '}';
    }
}
