

package ku.cs.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import ku.cs.models.Invoice;
import ku.cs.models.InvoiceList;
import ku.cs.services.Datasource;
import ku.cs.services.InvoiceDataSource;

import java.io.IOException;


public class InvoiceListController {
    @FXML
    private TableView<Invoice> itemTableView;
    @FXML
    private TextField keywordsTextField;
    private FilteredList<Invoice> filteredList;



    @FXML
    private void initialize() {
        initTableView();
        loadData();

        itemTableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                Invoice selectedInvoice = itemTableView.getSelectionModel().getSelectedItem();
                if (selectedInvoice != null) {
                    try {
                        com.github.saacsos.FXRouter.goTo("invoiceManage", selectedInvoice.getInvoiceNo());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        keywordsTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(invoice -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true; // Display all items when the search box is empty
                }

                String lowerCaseFilter = newValue.toLowerCase();
                // Check if the keyword is present in any of the columns
                return invoice.getItemName().toLowerCase().contains(lowerCaseFilter)
                        || invoice.getItemId().toLowerCase().contains(lowerCaseFilter)
                        || String.valueOf(invoice.getQty()).toLowerCase().contains(lowerCaseFilter)
                        || invoice.getDescription().toLowerCase().contains(lowerCaseFilter)
                        || invoice.getVendor().toLowerCase().contains(lowerCaseFilter)
                        || invoice.getPoNo().toLowerCase().contains(lowerCaseFilter)
                        || invoice.getDueDate().toLowerCase().contains(lowerCaseFilter)
                        || invoice.getLine().toLowerCase().contains(lowerCaseFilter)
                        || invoice.getKeepLoc().toLowerCase().contains(lowerCaseFilter)
                        || invoice.getReceiveLoc().toLowerCase().contains(lowerCaseFilter)
                        || invoice.getStatus().toLowerCase().contains(lowerCaseFilter);
            });
        });


    }

    private void loadData() {
        Datasource<InvoiceList> invoiceListDatasource = new InvoiceDataSource();
        InvoiceList invoiceList = invoiceListDatasource.readData();
        ObservableList<Invoice> invoices = FXCollections.observableArrayList(invoiceList.getInvoices());
        filteredList = new FilteredList<>(invoices, p -> true); // Initialize the filtered list

        itemTableView.setItems(filteredList);
//        itemTableView.setItems(FXCollections.observableArrayList(invoiceList.getInvoices()));
    }

    private void initTableView() {
        TableColumn<Invoice, String> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("itemId"));

        TableColumn<Invoice, String> nameColumn = new TableColumn<>("NAME");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));

        TableColumn<Invoice, String> qtyColumn = new TableColumn<>("QTY");
        qtyColumn.setCellValueFactory(new PropertyValueFactory<>("qty"));

        TableColumn<Invoice, String> unitColumn = new TableColumn<>("UNIT");
        unitColumn.setCellValueFactory(new PropertyValueFactory<>("unit"));

        TableColumn<Invoice, String> descriptionColumn = new TableColumn<>("DESCRIPTION");
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

        TableColumn<Invoice, String> vendorColumn = new TableColumn<>("VENDOR");
        vendorColumn.setCellValueFactory(new PropertyValueFactory<>("vendor"));

        TableColumn<Invoice, String> poColumn = new TableColumn<>("PoNo");
        poColumn.setCellValueFactory(new PropertyValueFactory<>("poNo"));

        TableColumn<Invoice, String> ddColumn = new TableColumn<>("DUE-DATE");
        ddColumn.setCellValueFactory(new PropertyValueFactory<>("dueDate"));

        TableColumn<Invoice, String> lineColumn = new TableColumn<>("LINE");
        lineColumn.setCellValueFactory(new PropertyValueFactory<>("line"));

        TableColumn<Invoice, String> kLocColumn = new TableColumn<>("K-LOC");
        kLocColumn.setCellValueFactory(new PropertyValueFactory<>("keepLoc"));

        TableColumn<Invoice, String> rLocColumn = new TableColumn<>("R-LOC");
        rLocColumn.setCellValueFactory(new PropertyValueFactory<>("receiveLoc"));

        TableColumn<Invoice, String> statusColumn = new TableColumn<>("STATUS");
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        itemTableView.getColumns().clear();
        itemTableView.getColumns().addAll(idColumn, nameColumn, qtyColumn, unitColumn, descriptionColumn, vendorColumn,
                poColumn, ddColumn, lineColumn, kLocColumn, rLocColumn, statusColumn);
    }

    @FXML
    public void onBackClick() {
        try {
            com.github.saacsos.FXRouter.goTo("home");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}

