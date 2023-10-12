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
                int amount = queryOutput.getInt(3);
                String location = queryOutput.getString(4);
                String storageDate = queryOutput.getString(5);
                stockList.addStock(new Stock(itemId , itemName , amount , location , storageDate));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stockList;
    }

    @Override
    public void insertData(StockList stockList) {

    }

}
