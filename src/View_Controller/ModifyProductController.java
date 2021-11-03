package View_Controller;

import Model.Inventory;
import Model.Part;
import Model.Product;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class ModifyProductController implements Initializable {
  private final ObservableList<Part> partSearchList = FXCollections.observableArrayList();
  @FXML TableView<Part> partTableView;
  @FXML TableView<Part> productPartTableView;
  @FXML private TextField idField;
  @FXML private TextField nameField;
  @FXML private TextField inventoryField;
  @FXML private TextField priceField;
  @FXML private TextField maxField;
  @FXML private TextField minField;
  @FXML private TableColumn<Part, Integer> partIdColumn;
  @FXML private TableColumn<Part, String> partNameColumn;
  @FXML private TableColumn<Part, Integer> partInventoryColumn;
  @FXML private TableColumn<Part, Double> partPriceColumn;
  @FXML private TableColumn<Part, Integer> productPartIdColumn;
  @FXML private TableColumn<Part, String> productPartNameColumn;
  @FXML private TableColumn<Part, Integer> productPartInventoryColumn;
  @FXML private TableColumn<Part, Double> productPartPriceColumn;
  @FXML private Button partSearchButton;
  @FXML private TextField partSearchField;
  @FXML private Button addProductPartButton;
  @FXML private Button deleteProductPartButton;
  @FXML private Button saveProductButton;
  @FXML private Button cancelButton;
  private Product selectedProduct;
  private ObservableList<Part> modifiedPartList = FXCollections.observableArrayList();
  private ObservableList<Part> originalPartList;

  // Initialize form with information of product selected for modification.
  // Create duplicate of initial associated part list to revert to on cancel.
  public void initData(Product product) {
    selectedProduct = product;
    idField.setText(Integer.toString(product.getId()));
    nameField.setText(product.getName());
    inventoryField.setText(Integer.toString(product.getStock()));
    priceField.setText(Double.toString(product.getPrice()));
    maxField.setText(Integer.toString(product.getMax()));
    minField.setText(Integer.toString(product.getMin()));

    modifiedPartList = product.getAllAssociatedParts();
    originalPartList = FXCollections.observableArrayList(modifiedPartList);
    productPartTableView.setItems(modifiedPartList);
  }

  public void addProductPartButtonClicked() {
    Part selectedPart = partTableView.getSelectionModel().getSelectedItem();

    if (!(selectedPart == null)) {
      int id = selectedPart.getId();
      boolean repeatedPart = false;

      for (Part part : modifiedPartList) {
        if (part.getId() == id) {
          repeatedPart = true;
          AlertMessage.productError(7, null);
        }
      }
      if (!repeatedPart) {
        modifiedPartList.add(selectedPart);
      }
      productPartTableView.setItems(modifiedPartList);
      productPartTableView.refresh();
      productPartTableView.getSortOrder().add(productPartIdColumn);
      productPartTableView.sort();
    }
  }

  public void deleteProductPartButtonClicked() {
    Part selectedPart = productPartTableView.getSelectionModel().getSelectedItem();
    if (!(selectedPart == null)) {
      boolean confirmDelete = AlertMessage.confirmationAlert(5, selectedPart.getName());
      if (confirmDelete) {
        modifiedPartList.remove(selectedPart);
      }
    }
  }

  public void saveButtonClicked(ActionEvent event) throws IOException {
    try {
      resetFieldsStyle();
      TextField[] textFields = {nameField, inventoryField, priceField, maxField, minField};

      // Check for empty fields.
      for (TextField field : textFields) {
        if (field.getText().trim().isEmpty()) {
          AlertMessage.productError(1, field);
          return;
        }
      }

      // Parse fields and assign variables.
      int id = Integer.parseInt((idField.getText().trim()));
      String name = nameField.getText().trim();
      double price = Double.parseDouble(priceField.getText().trim());
      int stock = Integer.parseInt(inventoryField.getText().trim());
      int min = Integer.parseInt(minField.getText().trim());
      int max = Integer.parseInt(maxField.getText().trim());

      // Iterate through fields and check for negative numbers and logical inventory errors.
      for (TextField field : textFields) {
        if (field == priceField) {
          if (price < 0) {
            AlertMessage.productError(5, field);
            return;
          }
        } else if (field == inventoryField || field == minField || field == maxField) {
          if (Integer.parseInt(field.getText().trim()) < 0) {
            AlertMessage.productError(5, field);
            return;
          }
        }
        if (field == maxField && (max < min)) {
          AlertMessage.productError(4, field);
          return;
        } else if (field == inventoryField && (stock > max || stock < min)) {
          AlertMessage.productError(3, field);
          return;
        }
      }

      // Check if product has associated parts.
      if (modifiedPartList.size() == 0) {
        AlertMessage.productError(8, null);
        return;
      }

      // Check if total cost of associated parts exceeds product price.
      double productPartTotal = 0.00;
      for (Part part : modifiedPartList) {
        productPartTotal += part.getPrice();
      }
      if (productPartTotal > price) {
        AlertMessage.productError(10, null);
        return;
      }

      // Error handling finished.
      // Add modified product to inventory, remove original.
      Product modifiedProduct = new Product(id, name, price, stock, min, max, modifiedPartList);
      Inventory.addProduct(modifiedProduct);
      Inventory.getAllProducts().remove(selectedProduct);

      displayMainScreen(event);

    } catch (NumberFormatException e) {
      AlertMessage.productError(9, null);
      e.printStackTrace();
    }
  }

  public void cancelButtonClicked(ActionEvent event) throws IOException {
    boolean confirmCancel = AlertMessage.confirmationAlert(1, null);
    if (confirmCancel) {
      selectedProduct.setAllAssociatedParts(originalPartList);
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

  public void partSearchButtonClicked(ActionEvent event) {
    if (!partSearchField.getText().trim().isEmpty()) {
      partSearchList.clear();
      for (Part part : Inventory.getAllParts()) {
        if (part.getName().toLowerCase().contains(partSearchField.getText().toLowerCase().trim())) {
          partSearchList.add(part);
        }
      }
      partTableView.setItems(partSearchList);
    } else {
      partTableView.setItems(Inventory.getAllParts());
    }
    partTableView.refresh();
    partTableView.getSortOrder().add(partIdColumn);
    partTableView.sort();
  }

  public void resetFieldsStyle() {
    nameField.setStyle("-fx-border-color: lightgrey");
    inventoryField.setStyle("-fx-border-color: lightgrey");
    priceField.setStyle("-fx-border-color: lightgrey");
    maxField.setStyle("-fx-border-color: lightgrey");
    minField.setStyle("-fx-border-color: lightgrey");
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    // Initialize part table.
    partTableView.setItems(Inventory.getAllParts());
    partIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
    partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
    partInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
    partPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
    partTableView.getSortOrder().add(partIdColumn);
    partTableView.sort();

    // Initialize associated part table.
    productPartIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
    productPartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
    productPartInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
    productPartPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
    productPartTableView.getSortOrder().add(productPartIdColumn);
    productPartTableView.sort();

    // Initialize text fields.
    this.idField.setDisable(true);
  }
}
