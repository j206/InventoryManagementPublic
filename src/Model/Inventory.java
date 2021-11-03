package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {

  private static final ObservableList<Part> allParts = FXCollections.observableArrayList();
  private static final ObservableList<Product> allProducts = FXCollections.observableArrayList();
  private static int lastPartId = 0;
  private static int lastProductId = 10000;

  public static int getLastPartId() {
    return lastPartId;
  }

  public static void setLastPartId(int lastPartId) {
    Inventory.lastPartId = lastPartId;
  }

  public static int getLastProductId() {
    return lastProductId;
  }

  public static void setLastProductId(int lastProductId) {
    Inventory.lastProductId = lastProductId;
  }

  public static void addPart(Part part) {
    allParts.add(part);
  }

  public static void addProduct(Product product) {
    allProducts.add(product);
  }

  public static ObservableList<Part> getAllParts() {
    return allParts;
  }

  public static ObservableList<Product> getAllProducts() {
    return allProducts;
  }

  // Unused UML methods.
  public static Part lookupPart(int partId) {
    return null;
  }

  public static Product lookupProduct(int productId) {
    return null;
  }

  public static Part lookupPart(String partName) {
    return null;
  }

  public static Part lookupProduct(String productName) {
    return null;
  }

  public static void updatePart(int index, Part selectedPart) {}

  public static void updateProduct(int index, Product selectedProduct) {}

  public static boolean deletePart(Part selectedPart) {
    return false;
  }

  public static boolean deleteProduct(Product selectedProduct) {
    return false;
  }
}
