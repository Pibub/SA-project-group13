package ku.cs.services;

import ku.cs.models.Invoice;
import ku.cs.models.Stock;
import ku.cs.models.StockList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class StockDataSource implements Datasource<StockList> {
    private DatabaseConnection databaseConnection;
    public StockDataSource(){}
    public StockDataSource(DatabaseConnection databaseConnection){this.databaseConnection = databaseConnection;}

    @Override
    public StockList readData(){
        StockList stockList = new StockList();
        databaseConnection = new DatabaseConnection();
        Connection connectionStock = databaseConnection.getConnection();
        String getStockData = "SELECT * FROM stock";
        try{
            Statement statement = connectionStock.createStatement();
            ResultSet queryOutput = statement.executeQuery(getStockData);
            while(queryOutput != null && queryOutput.next()){
                String itemId = queryOutput.getString(1);
                String itemName = queryOutput.getString(2);
                int amount = queryOutput.getInt(3);
                String location = queryOutput.getString(4);
                String storageDate = queryOutput.getString(5);
                String categoryId = queryOutput.getString(6);
                stockList.addStock(new Stock(itemId , itemName , amount , location , storageDate, categoryId));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stockList;
    }

    @Override
    public void insertData(StockList stockList) {
        databaseConnection = new DatabaseConnection();
        Connection connectionUser = databaseConnection.getConnection();

        for (Stock stock : stockList.getStockList()) {
            try {
                Statement statement = connectionUser.createStatement();

                String checkUserQuery = "SELECT * FROM stock WHERE item_id = '" + stock.getItemId() + "'";
                ResultSet checkUserResult = statement.executeQuery(checkUserQuery);

                if (checkUserResult.next()) {
                    String updateUserQuery = "UPDATE invoice SET item_name = '" + stock.getItemName() + "', " +
                            "amount = '" + stock.getAmount() + "', " +
                            "location = '" + stock.getLocation() + "', " +
                            "storage_date = '" + stock.getStorageDate() + "', " +
                            "category_id = '" + stock.getCategoryId() + "', " +
                            "WHERE item_id = '" + stock.getItemId() + "'";

                    statement.executeUpdate(updateUserQuery);
                } else {
                    // If the user doesn't exist, insert a new user
                    String insertUserQuery = "INSERT INTO stock (item_id, item_name, amount, location, storage_date, category_id) " +
                            "VALUES ('" + stock.getItemId() + "', '" + stock.getItemName() + "', '" + stock.getAmount() + "', '" +
                            stock.getLocation() + "', '" + stock.getStorageDate() + "', '" + stock.getCategoryId() + "')";

                    statement.executeUpdate(insertUserQuery);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
