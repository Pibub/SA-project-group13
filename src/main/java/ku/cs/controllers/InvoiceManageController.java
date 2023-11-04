package ku.cs.controllers;

import com.github.saacsos.FXRouter;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import ku.cs.models.*;
import ku.cs.services.Datasource;
import ku.cs.services.InvoiceDataSource;
import ku.cs.services.StockDataSource;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

    @FXML public void onAddItemClick(){
        String addItemID = addItemIdTextField.getText();
        String addItemName = addItemNameTextField.getText();
        Integer addAmountText = Integer.valueOf(addAmountTextField.getText());
        String addLocation = addLocationTextField.getText();
        String addDate = addStorageDatePicker.getValue().toString();

        Stock stock = stockList.findItemByIdAndName(addItemID, addItemName);

        if(!addItemID.isEmpty() && !addItemName.isEmpty() && addAmountText != null && !addLocation.isEmpty()){
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
        }
        else {
            warning.setText("Please fill in complete information.");
        }
        addItemIdTextField.clear();
        addItemNameTextField.clear();
        addAmountTextField.clear();
        addLocationTextField.clear();
        addStorageDatePicker.getEditor().clear();
    }
    @FXML public void onCompleteInvoice(){
        if (invoice != null) {
            // Update the status of the invoice to "Completed"
            invoice.setStatus("Completed");

            // Save the updated status back to the data source
            InvoiceDataSource invoiceDataSource = new InvoiceDataSource();
            InvoiceList invoiceList = invoiceDataSource.readData();

            for (Invoice i : invoiceList.getInvoices()) {
                if (i.isId(invoice.getItemId())) {
                    i.setStatus("Completed");
                }
            }

            invoiceDataSource.insertData(invoiceList);

            // Update the status label to show that the invoice is now completed
            warning.setText("Invoice Status: " + invoice.getStatus());
        }
    }
    @FXML public void onInCompleteInvoice(){
        if (invoice != null) {
            // Update the status of the invoice to "Incomplete"
            invoice.setStatus("Incomplete");

            // Save the updated status back to the data source
            InvoiceDataSource invoiceDataSource = new InvoiceDataSource();
            InvoiceList invoiceList = invoiceDataSource.readData();

            for (Invoice i : invoiceList.getInvoices()) {
                if (i.isId(invoice.getItemId())) {
                    i.setStatus("Incomplete");
                }
            }

            invoiceDataSource.insertData(invoiceList);

            // Update the status label to show that the invoice is now incomplete
            warning.setText("Invoice Status: " + invoice.getStatus());
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