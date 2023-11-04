package ku.cs.controllers;

import com.github.saacsos.FXRouter;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import ku.cs.models.*;
import ku.cs.services.Datasource;
import ku.cs.services.InvoiceDataSource;
import ku.cs.services.StockDataSource;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import javafx.scene.control.Alert.AlertType;

public class InvoiceManageController {
    @FXML private Label invoiceIDLabel;
    @FXML private Label itemIDLabel;
    @FXML private Label itemNameLabel;
    @FXML private Label qtyLabel;
    @FXML private Label kLocLabel;
    @FXML private TextField addItemIdTextField;
    @FXML private TextField addItemNameTextField;
    @FXML private TextField addAmountTextField;
    @FXML private TextField addLocationTextField;
    @FXML private DatePicker addStorageDatePicker;
    @FXML private Label warning;
    @FXML private Label successLabel;
    private Invoice invoice;
    private StockList stockList;
    private Datasource<StockList> stockListDatasource;

    @FXML private void initialize(){
        Datasource<InvoiceList> invoiceListDatasource = new InvoiceDataSource();
        InvoiceList invoiceList = invoiceListDatasource.readData();
        stockListDatasource = new StockDataSource();
        stockList = stockListDatasource.readData();

        String invoiceNO = (String) FXRouter.getData();
        invoice = invoiceList.findInvoiceByID(invoiceNO);


        invoiceIDLabel.setText(invoice.getInvoiceNo());
        itemIDLabel.setText(invoice.getItemId());
        itemNameLabel.setText(invoice.getItemName());
        qtyLabel.setText(String.valueOf(invoice.getQty()));
        kLocLabel.setText(invoice.getKeepLoc());
        warning.setText("***You have to fill information above completely***");
        warning.setStyle("-fx-text-fill: red;");
    }

    @FXML
    public void onAddItemClick() {
        String addItemID = addItemIdTextField.getText();
        String addItemName = addItemNameTextField.getText();
        Integer addAmountText = Integer.valueOf(addAmountTextField.getText());
        String addLocation = addLocationTextField.getText();
        String addDate = addStorageDatePicker.getValue().toString();

        Stock stock = stockList.findItemByIdAndName(addItemID, addItemName);

        if (!addItemID.isEmpty() && !addItemName.isEmpty() && addAmountText != null && !addLocation.isEmpty()) {
            // Create a confirmation dialog
            Alert confirmationAlert = new Alert(AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Confirmation");
            confirmationAlert.setHeaderText("Are you sure to add this new item?");
            confirmationAlert.setContentText("Please confirm your action.");

            // Show the confirmation dialog and wait for a response
            ButtonType confirmButton = new ButtonType("Confirm");
            ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
            confirmationAlert.getButtonTypes().setAll(confirmButton, cancelButton);

            // Get the user's response
            Optional<ButtonType> result = confirmationAlert.showAndWait();

            if (result.isPresent() && result.get() == confirmButton) {
                try {
                    int addAmount = Integer.parseInt(String.valueOf(addAmountText));
                    LocalDateTime localDateTime = LocalDateTime.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm-yyyy-MM-dd");
                    String formattedTime = localDateTime.format(formatter);
                    String itemIdWithDate = addItemID + "-" + formattedTime.toString();
                    stockList.addStock(itemIdWithDate, addItemName, addAmount, addLocation, addDate, addItemID);
                    stockListDatasource.insertData(stockList);
                } catch (NumberFormatException e) {
                    warning.setText("Please enter a valid amount.");
                }
                successLabel.setText("Add item to stock complete");
                successLabel.setStyle("-fx-text-fill: green");
            } else {
                // User canceled the action
                warning.setText("Action canceled");
            }

            addItemIdTextField.clear();
            addItemNameTextField.clear();
            addAmountTextField.clear();
            addLocationTextField.clear();
            addStorageDatePicker.getEditor().clear();
        } else {
            warning.setText("Please fill in complete information.");
        }
    }
    @FXML
    public void onCompleteInvoice() {
        if (invoice != null) {
            Alert confirmationAlert = new Alert(AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Confirmation");
            confirmationAlert.setHeaderText("Are you sure to complete this invoice?");
            confirmationAlert.setContentText("Please confirm your action.");

            ButtonType confirmButton = new ButtonType("Confirm");
            ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
            confirmationAlert.getButtonTypes().setAll(confirmButton, cancelButton);

            Optional<ButtonType> result = confirmationAlert.showAndWait();

            if (result.isPresent() && result.get() == confirmButton) {
                invoice.setStatus("Completed");

                InvoiceDataSource invoiceDataSource = new InvoiceDataSource();
                InvoiceList invoiceList = invoiceDataSource.readData();

                for (Invoice i : invoiceList.getInvoices()) {
                    if (i.isId(invoice.getItemId())) {
                        i.setStatus("Completed");
                    }
                }

                invoiceDataSource.insertData(invoiceList);

                warning.setText("Invoice Status: " + invoice.getStatus());
            }
        }
    }
    @FXML
    public void onInCompleteInvoice() {
        if (invoice != null) {
            Alert confirmationAlert = new Alert(AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Confirmation");
            confirmationAlert.setHeaderText("Are you sure to mark this invoice as incomplete?");
            confirmationAlert.setContentText("Please confirm your action.");

            ButtonType confirmButton = new ButtonType("Confirm");
            ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
            confirmationAlert.getButtonTypes().setAll(confirmButton, cancelButton);

            Optional<ButtonType> result = confirmationAlert.showAndWait();

            if (result.isPresent() && result.get() == confirmButton) {
                invoice.setStatus("Incomplete");

                InvoiceDataSource invoiceDataSource = new InvoiceDataSource();
                InvoiceList invoiceList = invoiceDataSource.readData();

                for (Invoice i : invoiceList.getInvoices()) {
                    if (i.isId(invoice.getItemId())) {
                        i.setStatus("Incomplete");
                    }
                }

                invoiceDataSource.insertData(invoiceList);

                warning.setText("Invoice Status: " + invoice.getStatus());
            }
        }
    }
    @FXML
    public void onBackClick() {
        try {
            com.github.saacsos.FXRouter.goTo("receivingData");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}