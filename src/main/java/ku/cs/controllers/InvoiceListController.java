

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


    @FXML
    private void initialize() {
        initTableView();
        loadData();

        itemTableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Invoice>() {
            @Override
            public void changed(ObservableValue observable, Invoice oldValue, Invoice newValue) {
                if (newValue != null) {
                    try {
                        com.github.saacsos.FXRouter.goTo("invoiceManage", newValue.getInvoiceNo());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
    }

    private void loadData() {
        Datasource<InvoiceList> invoiceListDatasource = new InvoiceDataSource();
        InvoiceList invoiceList = invoiceListDatasource.readData();
        itemTableView.setItems(FXCollections.observableArrayList(invoiceList.getInvoices()));
    }

    private void initTableView() {
        TableColumn<Invoice, String> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("itemId"));

        TableColumn<Invoice, String> nameColumn = new TableColumn<>("NAME");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));

        TableColumn<Invoice, String> qtyColumn = new TableColumn<>("QTY");
        qtyColumn.setCellValueFactory(new PropertyValueFactory<>("qty"));

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
        itemTableView.getColumns().addAll(idColumn, nameColumn, qtyColumn, descriptionColumn, vendorColumn,
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

