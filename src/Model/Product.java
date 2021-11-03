package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Product {
  private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
  private int id, stock, min, max;
  private String name;
  private double price;

  public Product(int id, String name, double price, int stock, int min, int max) {
    this.id = id;
    this.name = name;
    this.price = price;
    this.stock = stock;
    this.min = min;
    this.max = max;
  }

  public Product(
      int id,
      String name,
      double price,
      int stock,
      int min,
      int max,
      ObservableList<Part> associatedParts) {
    this.id = id;
    this.name = name;
    this.price = price;
    this.stock = stock;
    this.min = min;
    this.max = max;
    this.associatedParts = associatedParts;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  public int getStock() {
    return stock;
  }

  public void setStock(int stock) {
    this.stock = stock;
  }

  public int getMin() {
    return min;
  }

  public void setMin(int min) {
    this.min = min;
  }

  public int getMax() {
    return max;
  }

  public void setMax(int max) {
    this.max = max;
  }

  public ObservableList<Part> getAllAssociatedParts() {
    return associatedParts;
  }

  public void setAllAssociatedParts(ObservableList<Part> associatedParts) {
    this.associatedParts = associatedParts;
  }

  // Unused UML methods.
  public void addAssociatedPart(Part part) {}

  public boolean deleteAssociatedPart(Part part) {
    return false;
  }
}
