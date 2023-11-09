package ku.cs.services;

import ku.cs.models.CountStock;
import ku.cs.models.CountStockList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CountStockDataSource implements Datasource<CountStockList>{
    private DatabaseConnection databaseConnection;
    public CountStockDataSource(){}


    @Override
    public CountStockList readData() {
        CountStockList countStockList = new CountStockList();
        databaseConnection = new DatabaseConnection();
        Connection connectionCountStock = databaseConnection.getConnection();
        String getCountStockData = "SELECT * FROM count_stock";
        try{
            Statement statement = connectionCountStock.createStatement();
            ResultSet queryOutput = statement.executeQuery(getCountStockData);
            while (queryOutput != null && queryOutput.next()){
                String userId = queryOutput.getString(1);
                String shelfId = queryOutput.getString(2);
                int total = queryOutput.getInt(3);
                String location = queryOutput.getString(4);
                String userName = queryOutput.getString(5);

                countStockList.addCountStock(new CountStock(userId, shelfId, total, location, userName));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return countStockList;
    }

    @Override
    public void insertData(CountStockList countStockList) {
        databaseConnection = new DatabaseConnection();
        Connection connectionCountStock = databaseConnection.getConnection();

        for (CountStock countStock : countStockList.getCountStocks()) {
            try {
                Statement statement = connectionCountStock.createStatement();

                String checkCountStockQuery = "SELECT * FROM count_stock WHERE shelf_id = '" + countStock.getShelfId() + "'";
                ResultSet checkCountStockResult = statement.executeQuery(checkCountStockQuery);

                if (checkCountStockResult.next()) {
                    String updateUserQuery = "UPDATE count_stock SET user_id = '" + countStock.getUserId() + "', " +
                            "total = '" + countStock.getTotal() + "', " +
                            "location = '" + countStock.getLocation() + "', " +
                            "user_name = '" + countStock.getUserName() + "' " +
                            "WHERE shelf_id = '" + countStock.getShelfId()+ "'";



                    statement.executeUpdate(updateUserQuery);
                } else {

                    String insertUserQuery = "INSERT INTO count_stock (user_id, shelf_id, total, location, user_name ) " +
                            "VALUES ('" + countStock.getUserId() + "', '" + countStock.getShelfId() + "', '" + countStock.getTotal() + "', '" +
                            countStock.getLocation() + "', '" + countStock.getUserName() + "')";

                    statement.executeUpdate(insertUserQuery);
                }
            } catch (Exception e) {
                e.printStackTrace();

            }
        }
    }

    @Override
    public void deleteData(String categoryId) {
        databaseConnection = new DatabaseConnection();
        Connection connectionCountStock = databaseConnection.getConnection();

        try {
            Statement statement = connectionCountStock.createStatement();

            String deleteQuery = "DELETE FROM count_stock WHERE category_id = '" + categoryId + "'";
            statement.executeUpdate(deleteQuery);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}