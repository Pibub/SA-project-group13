package ku.cs.services;

import ku.cs.models.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class InvoiceDataSource implements Datasource<InvoiceList> {
    private DatabaseConnection databaseConnection;
    public InvoiceDataSource(){}
    public InvoiceDataSource(DatabaseConnection databaseConnection){
        this.databaseConnection = databaseConnection;
    }

    @Override
    public InvoiceList readData() {
        InvoiceList invoiceList = new InvoiceList();
        databaseConnection = new DatabaseConnection();
        Connection connectionStock = databaseConnection.getConnection();
        String getInvoiceData = "SELECT * FROM invoice";
        try{
            Statement statement = connectionStock.createStatement();
            ResultSet queryOutput = statement.executeQuery(getInvoiceData);

            while(queryOutput != null && queryOutput.next()){

                String itemId = queryOutput.getString(1);
                String itemName = queryOutput.getString(2);
                Float qty = queryOutput.getFloat(3);
                String description = queryOutput.getString(4);
                String vendor = queryOutput.getString(5);
                String invoiceNo = queryOutput.getString(6);
                String poNo = queryOutput.getString(7);
                String dueDate = queryOutput.getString(8);
                String line = queryOutput.getString(9);
                String keepLoc = queryOutput.getString(10);
                String receiveLoc = queryOutput.getString(11);
                String status = queryOutput.getString(12);


                invoiceList.addInvoice(new Invoice(itemId , itemName , qty , description , vendor, invoiceNo, poNo, dueDate, line, keepLoc, receiveLoc, status));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return invoiceList;

    }

    @Override
    public void insertData(InvoiceList invoiceList) {
        databaseConnection = new DatabaseConnection();
        Connection connectionUser = databaseConnection.getConnection();

        for (Invoice invoice : invoiceList.getInvoices()) {
            try {
                Statement statement = connectionUser.createStatement();

                String checkUserQuery = "SELECT * FROM invoice WHERE item_id = '" + invoice.getItemId() + "'";
                ResultSet checkUserResult = statement.executeQuery(checkUserQuery);

                if (checkUserResult.next()) {
                    String updateUserQuery = "UPDATE invoice SET item_name = '" + invoice.getItemName() + "', " +
                            "qty = '" + invoice.getQty() + "', " +
                            "description = '" + invoice.getDescription() + "', " +
                            "vendor = '" + invoice.getVendor() + "', " +
                            "invoice_no = '" + invoice.getInvoiceNo() + "', " +
                            "po_no = '" + invoice.getPoNo() + "', " +
                            "due_date = '" + invoice.getDueDate() + "', " +
                            "line = '" + invoice.getLine() + "', " +
                            "keep_loc = '" + invoice.getKeepLoc()+ "', " +
                            "receive_loc = '" + invoice.getReceiveLoc() + "', " +
                            "status = '" + invoice.getStatus()+ "' " +
                            "WHERE item_id = '" + invoice.getItemId() + "'";


                    statement.executeUpdate(updateUserQuery);
                } else {
                    // If the user doesn't exist, insert a new user
                    String insertUserQuery = "INSERT INTO invoice (item_id, item_name, qty, description, vendor, invoice_no, po_no, due_date, line, keep_loc, receive_loc, status) " +
                            "VALUES ('" + invoice.getItemId() + "', '" + invoice.getItemName() + "', '" + invoice.getQty() + "', '" +
                            invoice.getDescription() + "', '" + invoice.getVendor() + "', '" + invoice.getInvoiceNo() + "', '" +
                            invoice.getPoNo() + "', '" + invoice.getDueDate() + "', '" + invoice.getLine() + "', '" + invoice.getKeepLoc() + "', '" +
                            invoice.getReceiveLoc() + "', '" + invoice.getStatus() + "')";

                    statement.executeUpdate(insertUserQuery);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
