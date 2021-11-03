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

public class MainScreenController implements Initializable {
  private final ObservableList<Part> partSearchList = FXCollections.observableArrayList();
  private final ObservableList<Product> productSearchList = FXCollections.observableArrayList();
  // General nodes.
  @FXML private Button exitButton;
  // Part management nodes.
  @FXML private TextField partSearchField;
  @FXML private Button partSearchButton;
  @FXML private TableView<Part> partTableView;
  @FXML private TableColumn<Part, Integer> partIdColumn;
  @FXML private TableColumn<Part, String> partNameColumn;
  @FXML private TableColumn<Part, Integer> partInventoryColumn;
  @FXML private TableColumn<Part, Double> partPriceColumn;
  @FXML private Button addPartButton;
  @FXML private Button modifyPartButton;
  @FXML private Button deletePartButton;
  private boolean partSearchActive;
  // Product management nodes.
  @FXML private TextField productSearchField;
  @FXML private Button productSearchButton;
  @FXML private TableView<Product> productTableView;
  @FXML private TableColumn<Product, Integer> productIdColumn;
  @FXML private TableColumn<Product, String> productNameColumn;
  @FXML private TableColumn<Product, Integer> productInventoryColumn;
  @FXML private TableColumn<Product, Double> productPriceColumn;
  @FXML private Button addProductButton;
  @FXML private Button modifyProductButton;
  @FXML private Button deleteProductButton;
  private boolean productSearchActive;

  public void addPartButtonClicked(ActionEvent event) throws IOException {
    changeSceneButton(event, "/View_Controller/AddPart.fxml");
  }

  public void modifyPartButtonClicked(ActionEvent event) throws IOException {
    Part selectedPart = partTableView.getSelectionModel().getSelectedItem();
    if (!(selectedPart == null)) {
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(getClass().getResource("/View_Controller/ModifyPart.fxml"));
      Parent parent = loader.load();
      Scene targetScene = new Scene(parent);

      ModifyPartController controller = loader.getController();
      controller.initData(selectedPart);

      Stage window = (Stage) ((Button) event.getSource()).getScene().getWindow();
      window.setScene(targetScene);
      window.show();
    }
  }

  public void partDeleteButtonClicked() {
    Part selectedPart = partTableView.getSelectionModel().getSelectedItem();
    if (!(selectedPart == null)) {
      boolean confirmDelete = AlertMessage.confirmationAlert(2, selectedPart.getName());
      if (confirmDelete) {
        Inventory.getAllParts().remove(selectedPart);
        if (partSearchActive) {
          partSearchList.remove(selectedPart);
        }
      }
    }
  }

  public void addProductButtonClicked(ActionEvent event) throws IOException {
    changeSceneButton(event, "/View_Controller/AddProduct.fxml");
  }

  public void modifyProductButtonClicked(ActionEvent event) throws IOException {
    Product selectedProduct = productTableView.getSelectionModel().getSelectedItem();
    if (!(selectedProduct == null)) {
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(getClass().getResource("/View_Controller/ModifyProduct.fxml"));
      Parent parent = loader.load();
      Scene targetScene = new Scene(parent);

      ModifyProductController controller = loader.getController();
      controller.initData(selectedProduct);

      Stage window = (Stage) ((Button) event.getSource()).getScene().getWindow();
      window.setScene(targetScene);
      window.show();
    }
  }

  public void deleteProductButtonClicked() {
    Product selectedProduct = productTableView.getSelectionModel().getSelectedItem();
    if (!(selectedProduct == null)) {
      boolean confirmDelete = AlertMessage.confirmationAlert(3, selectedProduct.getName());
      if (confirmDelete) {
        Inventory.getAllProducts().remove(selectedProduct);
        if (productSearchActive) {
          productSearchList.remove(selectedProduct);
        }
      }
    }
  }

  public void partSearchButtonClicked() {
    if (!partSearchField.getText().trim().isEmpty()) {
      partSearchList.clear();
      for (Part part : Inventory.getAllParts()) {
        if (part.getName().toLowerCase().contains(partSearchField.getText().toLowerCase().trim())) {
          partSearchList.add(part);
        }
      }
      partTableView.setItems(partSearchList);
      partSearchActive = true;
    } else {
      partSearchActive = false;
      partTableView.setItems(Inventory.getAllParts());
    }
    partTableView.refresh();
    partTableView.getSortOrder().add(partIdColumn);
    partTableView.sort();
  }

  public void productSearchButtonClicked() {
    if (!productSearchField.getText().trim().isEmpty()) {
      productSearchList.clear();
      for (Product product : Inventory.getAllProducts()) {
        if (product
            .getName()
            .toLowerCase()
            .contains(productSearchField.getText().toLowerCase().trim())) {
          productSearchList.add(product);
        }
      }
      productTableView.setItems(productSearchList);
      productSearchActive = true;
    } else {
      productSearchActive = false;
      productTableView.setItems(Inventory.getAllProducts());
    }
    productTableView.refresh();
    productTableView.getSortOrder().add(productIdColumn);
    productTableView.sort();
  }

  public void exitButtonClicked() {
    boolean confirmExit = AlertMessage.confirmationAlert(4, null);
    if (confirmExit) {
      System.exit(0);
    }
  }

  // Handle add part/product scene changes - no data is passed.
  public void changeSceneButton(ActionEvent event, String fxmlResource) throws IOException {
    Parent parent = FXMLLoader.load(getClass().getResource(fxmlResource));
    Scene targetScene = new Scene(parent);

    Stage window = (Stage) ((Button) event.getSource()).getScene().getWindow();
    window.setScene(targetScene);
    window.show();
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    // Initialize part table.
    partTableView.setItems(Inventory.getAllParts());
    partSearchActive = false;
    partIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
    partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
    partInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
    partPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
    partTableView.getSortOrder().add(partIdColumn);
    partTableView.sort();

    // Initialize product table.
    productTableView.setItems(Inventory.getAllProducts());
    productSearchActive = false;
    productIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
    productNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
    productInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
    productPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
    productTableView.getSortOrder().add(productIdColumn);
    productTableView.sort();
  }
}
