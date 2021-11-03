package View_Controller;

import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

// These are rather disorganized.
public class AlertMessage {
  public static void partError(int code, TextField field) {
    fieldError(field);

    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("");
    alert.setHeaderText("Error - Cannot Add Part");
    switch (code) {
      case 1:
        {
          alert.setContentText("Fields cannot be empty.");
          break;
        }
      case 3:
        {
          alert.setContentText(
              "Inventory amount must be between minimum and maximum storage capacity.");
          break;
        }
      case 4:
        {
          alert.setContentText("Minimum storage capacity cannot exceed maximum.");
          break;
        }
      case 5:
        {
          alert.setContentText("Fields with numbers cannot be negative.");
          break;
        }
      case 6:
        {
          alert.setContentText("Machine ID must be a number.");
          break;
        }
      case 7:
        {
          alert.setContentText(
              "Price, inventory, max/min, machine ID fields must contain numbers.");
          break;
        }
      default:
        {
          alert.setContentText("An unknown error has occurred.");
          break;
        }
    }
    alert.showAndWait();
  }

  public static void productError(int code, TextField field) {
    fieldError(field);

    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("");
    switch (code) {
      case 1:
        {
          alert.setHeaderText("Error - Cannot Add Product");
          alert.setContentText("Fields cannot be empty.");
          break;
        }
      case 2:
        {
          alert.setHeaderText("Error - Cannot Add Product");
          alert.setContentText("A product with this name already exists.");
          break;
        }
      case 3:
        {
          alert.setHeaderText("Error - Cannot Add Product");
          alert.setContentText(
              "Inventory amount must be between minimum and maximum storage capacity.");
          break;
        }
      case 4:
        {
          alert.setHeaderText("Error - Cannot Add Product");
          alert.setContentText("Minimum storage capacity cannot exceed maximum.");
          break;
        }
      case 5:
        {
          alert.setHeaderText("Error - Cannot Add Product");
          alert.setContentText("Fields with numbers cannot be negative.");
          break;
        }
      case 6:
        {
          alert.setHeaderText("Error - Cannot Add Product");
          alert.setContentText("Machine ID must be a number.");
          break;
        }
      case 7:
        {
          alert.setHeaderText("Error - Cannot Add Associated Part");
          alert.setContentText("Selected part has already been associated with this product.");
          break;
        }
      case 8:
        {
          alert.setHeaderText("Error - Cannot Add Product");
          alert.setContentText("Product must have at least 1 associated part.");
          break;
        }
      case 9:
        {
          alert.setHeaderText("Error - Cannot Add Product");
          alert.setContentText("Price, inventory, max/min fields must contain numbers.");
          break;
        }
      case 10:
        {
          alert.setHeaderText("Error - Cannot Add Product");
          alert.setContentText("Total cost of associated parts cannot exceed product price.");
          break;
        }
      default:
        {
          alert.setHeaderText("Error - Cannot Add Product");
          alert.setContentText("An unknown error has occurred.");
          break;
        }
    }
    alert.showAndWait();
  }

  public static boolean confirmationAlert(int code, String name) {
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("");
    switch (code) {
      case 1:
        {
          alert.setHeaderText("Are you sure you want to cancel?");
          alert.setContentText("Any unsaved changes will be lost.");
          break;
        }
      case 2:
        {
          alert.setHeaderText("Are you sure you want to delete the selected part?");
          alert.setContentText(name + " will be permanently deleted.");
          break;
        }
      case 3:
        {
          alert.setHeaderText("Are you sure you want to delete the selected product?");
          alert.setContentText(name + " will be permanently deleted.");
          break;
        }
      case 4:
        {
          alert.setHeaderText("Are you sure you want to exit the program?");
          alert.setContentText("Any unsaved changes made will be lost.");
          break;
        }
      case 5:
        {
          alert.setHeaderText("Are you sure you want to remove this associated part?");
          alert.setContentText(name + " will no longer be associated with this product.");
        }
    }
    Optional<ButtonType> result = alert.showAndWait();
    return result.get() == ButtonType.OK;
  }

  public static void fieldError(TextField field) {
    if (field != null) {
      field.setStyle("-fx-border-color: red");
    }
  }
}
