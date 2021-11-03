package View_Controller;

import Model.InHouse;
import Model.Inventory;
import Model.Outsourced;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

public class AddPartController implements Initializable {
  @FXML private ToggleGroup partSourceToggleGroup;
  @FXML private RadioButton inHouseRadioButton;
  @FXML private RadioButton outsourcedRadioButton;
  @FXML private TextField idField;
  @FXML private TextField nameField;
  @FXML private TextField inventoryField;
  @FXML private TextField priceField;
  @FXML private TextField maxField;
  @FXML private TextField minField;
  @FXML private TextField machineOrCompanyField;
  @FXML private Label machineOrCompanyLabel;
  @FXML private Button savePartButton;
  @FXML private Button cancelButton;

  public void inHouseRadioClicked() {
    this.machineOrCompanyLabel.setText("Machine ID:");
  }

  public void outsourcedRadioClicked() {
    this.machineOrCompanyLabel.setText("Company Name:");
  }

  public void saveButtonClicked(ActionEvent event) throws IOException {
    try {
      resetFieldsStyle();
      TextField[] textFields = {
        nameField, priceField, maxField, minField, inventoryField, machineOrCompanyField
      };

      // Check for empty fields.
      for (TextField field : textFields) {
        if (field.getText().trim().isEmpty()) {
          AlertMessage.partError(1, field);
          return;
        }
      }

      // Parse fields to types and assign variables.
      int id = Inventory.getLastPartId() + 1;
      String name = nameField.getText().trim();
      double price = Double.parseDouble(priceField.getText().trim());
      int stock = Integer.parseInt(inventoryField.getText().trim());
      int min = Integer.parseInt(minField.getText().trim());
      int max = Integer.parseInt(maxField.getText().trim());

      // Iterate through fields and check for negative numbers and logical inventory errors.
      for (TextField field : textFields) {
        if (field == priceField) {
          if (price < 0) {
            AlertMessage.partError(5, field);
            return;
          }
        } else if (field == inventoryField || field == minField || field == maxField) {
          if (Integer.parseInt(field.getText().trim()) < 0) {
            AlertMessage.partError(5, field);
            return;
          }
        }
        if (field == maxField && (max < min)) {
          AlertMessage.partError(4, field);
          return;
        } else if (field == inventoryField && (stock > max || stock < min)) {
          AlertMessage.partError(3, field);
          return;
        }
      }

      // Error handling finished.
      // Designate subclass based on radio button and add to inventory, increment part ID counter.
      if (inHouseRadioButton.isSelected()) {
        int machineId = Integer.parseInt(machineOrCompanyField.getText().trim());
        Inventory.addPart(new InHouse(id, name, price, stock, min, max, machineId));
      } else {
        String companyName = machineOrCompanyField.getText().trim();
        Inventory.addPart(new Outsourced(id, name, price, stock, min, max, companyName));
      }

      Inventory.setLastPartId(id);
      displayMainScreen(event);

    } catch (NumberFormatException e) {
      AlertMessage.partError(7, null);
      e.printStackTrace();
    }
  }

  public void cancelButtonClicked(ActionEvent event) throws IOException {
    boolean confirmCancel = AlertMessage.confirmationAlert(1, null);
    if (confirmCancel) {
      displayMainScreen(event);
    }
  }

  public void displayMainScreen(ActionEvent event) throws IOException {
    Parent parent = FXMLLoader.load(getClass().getResource("/View_Controller/MainScreen.fxml"));
    Scene targetScene = new Scene(parent);

    Stage window = (Stage) ((Button) event.getSource()).getScene().getWindow();
    window.setScene(targetScene);
    window.show();
  }

  public void resetFieldsStyle() {
    nameField.setStyle("-fx-border-color: lightgrey");
    inventoryField.setStyle("-fx-border-color: lightgrey");
    priceField.setStyle("-fx-border-color: lightgrey");
    maxField.setStyle("-fx-border-color: lightgrey");
    minField.setStyle("-fx-border-color: lightgrey");
    machineOrCompanyField.setStyle("-fx-border-color: lightgrey");
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    // Initialize radio buttons.
    partSourceToggleGroup = new ToggleGroup();
    this.inHouseRadioButton.setToggleGroup(partSourceToggleGroup);
    this.outsourcedRadioButton.setToggleGroup(partSourceToggleGroup);
    this.inHouseRadioButton.setSelected(true);

    // Initialize conditional labels.
    this.machineOrCompanyLabel.setText("Machine ID:");

    // Initialize text fields.
    this.idField.setDisable(true);
    this.idField.setText((Inventory.getLastPartId() + 1) + " (Automatically Generated)");
  }
}
