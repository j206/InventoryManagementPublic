package Main;

import Model.InHouse;
import Model.Inventory;
import Model.Outsourced;
import Model.Product;
import View_Controller.AlertMessage;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class InventoryManagement extends Application {

  public static void main(String[] args) {
    addTestData();
    launch(args);
  }

  // There's no check if an ID already exists since they'd normally be automatically incremented
  // from 1 or 10001, so the sample data IDs here are very large numbers.
  public static void addTestData() {
    InHouse part9996 = new InHouse(9996, "Quick Part (In)", 9.99, 10, 1, 50, 5150);
    InHouse part9997 = new InHouse(9997, "Brown Part (In)", 19.99, 5, 1, 50, 59120);
    Outsourced part9998 = new Outsourced(9998, "Fox Part (Out)", 109.99, 10, 1, 25, "AMD");
    Outsourced part9999 = new Outsourced(9999, "Jumps Part (Out)", 119.99, 5, 1, 25, "Intel");

    Inventory.addPart(part9996);
    Inventory.addPart(part9997);
    Inventory.addPart(part9998);
    Inventory.addPart(part9999);

    Product product99996 = new Product(99996, "Over Product ABC", 99.99, 50, 1, 500);
    Product product99997 = new Product(99997, "The Product DEF", 199.99, 40, 1, 500);
    Product product99998 = new Product(99998, "Lazy Product GHI", 299.99, 30, 1, 500);
    Product product99999 = new Product(99999, "Dog Product JKL", 399.99, 20, 1, 500);

    Inventory.addProduct(product99996);
    Inventory.addProduct(product99997);
    Inventory.addProduct(product99998);
    Inventory.addProduct(product99999);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    Parent root = FXMLLoader.load(getClass().getResource("/View_Controller/MainScreen.fxml"));
    primaryStage.setScene(new Scene(root));
    primaryStage.show();
    primaryStage.setResizable(false);
    primaryStage.setOnCloseRequest(
        event -> {
          boolean confirmExit = AlertMessage.confirmationAlert(4, null);
          if (confirmExit) {
            System.exit(0);
          } else {
            event.consume();
          }
        });
  }
}
