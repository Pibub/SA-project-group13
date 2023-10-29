package ku.cs.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import ku.cs.models.Invoice;
import ku.cs.models.InvoiceList;
import ku.cs.services.Datasource;
import ku.cs.services.InvoiceDataSource;
import com.github.saacsos.FXRouter;

import java.io.IOException;

public class InvoiceManageController {
    @FXML private Label invoiceIDLabel;
    @FXML private Label itemIDLabel;
    @FXML private Label itemNameLabel;
    @FXML private Label qtyLabel;
    @FXML private Label kLocLabel;
    private Datasource<InvoiceList> invoiceListDatasource;
    private Invoice invoice;

    @FXML private void initialize(){
        invoiceListDatasource = new InvoiceDataSource();
        InvoiceList invoiceList = invoiceListDatasource.readData();

        String invoiceNo = (String) FXRouter.getData();

//        String invoiceNO = (String) FXRouter.getData();
//        invoice = invoiceList.findInvoiceByID(invoiceNO);
//
//        invoiceIDLabel.setText(invoice.getInvoiceNo());
//        itemIDLabel.setText(invoice.getItemId());
//        itemNameLabel.setText(invoice.getItemName());
//        qtyLabel.setText(String.valueOf(invoice.getQty()));
//        kLocLabel.setText(invoice.getKeepLoc());
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
