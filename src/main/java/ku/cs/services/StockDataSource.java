package ku.cs.services;

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
                int qty = queryOutput.getInt(3);
                String location = queryOutput.getString(4);
                String storageDate = queryOutput.getString(5);
                String shelfId = queryOutput.getString(6);
                String unit = queryOutput.getString(7);
                stockList.addStock(new Stock(itemId , itemName , qty , location , storageDate, shelfId, unit));
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
                    String updateUserQuery = "UPDATE stock SET item_name = '" + stock.getItemName() + "', " +
                            "qty = '" + stock.getQty() + "', " +
                            "location = '" + stock.getLocation() + "', " +
                            "storage_date = '" + stock.getStorageDate() + "', " +
                            "shelf_id = '" + stock.getShelfId() + "', " +
                            "unit = '" + stock.getUnit() + "' " +
                            "WHERE item_id = '" + stock.getItemId() + "'";



                    statement.executeUpdate(updateUserQuery);
                } else {

                    String insertUserQuery = "INSERT INTO stock (item_id, item_name, qty, location, storage_date, shelf_id, unit) " +
                            "VALUES ('" + stock.getItemId() + "', '" + stock.getItemName() + "', '" + stock.getQty() + "', '" +
                            stock.getLocation() + "', '" + stock.getStorageDate() + "', '" + stock.getShelfId() + "', '" + stock.getUnit() + "')";



                    statement.executeUpdate(insertUserQuery);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public void deleteData(String itemId) {
        databaseConnection = new DatabaseConnection();
        Connection connectionStock = databaseConnection.getConnection();

        try {
            Statement statement = connectionStock.createStatement();

            String deleteItemQuery = "DELETE FROM stock WHERE item_id = '" + itemId + "'";
            statement.executeUpdate(deleteItemQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
