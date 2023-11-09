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
    @FXML private Label unit;
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
        unit.setText(invoice.getUnit());
    }

    @FXML
    public void onCompleteInvoice() {
        if (invoice != null) {
            Alert confirmationAlert = new Alert(AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Confirmation");
            confirmationAlert.setHeaderText("Are you sure to complete this invoice?");
            confirmationAlert.setContentText("Please confirm your action");

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

                // Create an Alert to inform the user about marking the invoice as 'Completed'
                Alert infoAlert = new Alert(AlertType.INFORMATION);
                infoAlert.setTitle("Information");
                infoAlert.setHeaderText("Invoice Status");
                infoAlert.setContentText("Invoice has been marked as 'Completed'");
                infoAlert.showAndWait();

                // Now, add the invoice details to the stock database
                String addItemID = invoice.getItemId();
                String addItemName = invoice.getItemName();
                int addAmount = invoice.getQty();
                String addLocation = invoice.getKeepLoc();
                String addUnit = invoice.getUnit();

                LocalDateTime localDateTime = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm-yyyy-MM-dd");
                String formattedTime = localDateTime.format(formatter);
                String itemIdWithDate = addItemID + "-" + formattedTime.toString();

                // Add to the stock database
                stockList.addStock(itemIdWithDate, addItemName, addAmount, addLocation, formattedTime.toString(), addItemID, addUnit);
                stockListDatasource.insertData(stockList);

                // Create an Alert to inform the user about adding to the stock
                Alert addedToStockAlert = new Alert(AlertType.INFORMATION);
                addedToStockAlert.setTitle("Information");
                addedToStockAlert.setHeaderText("Added to Stock");
                addedToStockAlert.setContentText("The item has been added to the stock.");
                addedToStockAlert.showAndWait();
            }
        }
    }


    @FXML
    public void onIncompleteInvoice() {
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

                // Create an Alert to inform the user about marking the invoice as 'Incomplete'
                Alert infoAlert = new Alert(AlertType.INFORMATION);
                infoAlert.setTitle("Information");
                infoAlert.setHeaderText("Invoice Status");
                infoAlert.setContentText("Invoice has been marked as 'Incomplete'");
                infoAlert.showAndWait();
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