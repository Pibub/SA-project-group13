package ku.cs.services;

import ku.cs.models.ActualCountStock;
import ku.cs.models.ActualCountStockList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ActualCountStockDataSource implements Datasource<ActualCountStockList> {
    private DatabaseConnection databaseConnection;
    public ActualCountStockDataSource() {
        databaseConnection = new DatabaseConnection(); // Initialize the database connection
    }



    @Override
    public ActualCountStockList readData() {
        ActualCountStockList actualCountStockList = new ActualCountStockList();
        Connection connection = databaseConnection.getConnection();
        String query = "SELECT * FROM actual_count_stock ";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String shelfId = resultSet.getString(1);
                int firstCount = resultSet.getInt(2);
                int secondCount = resultSet.getInt(3);
                int thirdCount = resultSet.getInt(4);
                String inputerId = resultSet.getString(5);
                String inputerName = resultSet.getString(6);
                String location = resultSet.getString(7);

                actualCountStockList.addActualCountStock(new ActualCountStock(shelfId, firstCount, secondCount, thirdCount, inputerId, inputerName, location));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return actualCountStockList;
    }

    @Override
    public void insertData(ActualCountStockList actualCountStockList) {
        databaseConnection = new DatabaseConnection();
        Connection connection = databaseConnection.getConnection();

        for (ActualCountStock actualCountStock : actualCountStockList.getActualCountStockList()) {
            try {
                Statement statement = connection.createStatement();

                String checkCountStockQuery = "SELECT * FROM actual_count_stock WHERE shelf_id = '" + actualCountStock.getShelfId()+ "'";
                ResultSet checkCountStockResult = statement.executeQuery(checkCountStockQuery);

                if (checkCountStockResult.next()) {
                    String updateCountStockQuery = "UPDATE actual_count_stock SET first_count = '" + actualCountStock.getFirstCount() + "', " +
                            "second_count = '" + actualCountStock.getSecondCount() + "', " +
                            "third_count = '" + actualCountStock.getThirdCount() + "', " +
                            "inputer_id = '" + actualCountStock.getInputerId() + "', " +
                            "inputer_name = '" + actualCountStock.getInputerName() + "', " +
                            "location = '" + actualCountStock.getLocation() + "' " +
                            "WHERE shelf_id = '" + actualCountStock.getShelfId() + "'";

                    statement.executeUpdate(updateCountStockQuery);
                } else {
                    String insertCountStockQuery = "INSERT INTO actual_count_stock (shelf_id, first_count, second_count, third_count, inputer_id, inputer_name, location) " +
                            "VALUES ('" + actualCountStock.getShelfId() + "', '" +
                            actualCountStock.getFirstCount() + "', '" + actualCountStock.getSecondCount() + "', '" + actualCountStock.getThirdCount() + "', '" +
                            actualCountStock.getInputerId() + "', '" + actualCountStock.getInputerName() + "', '" + actualCountStock.getLocation() + "')";

                    statement.executeUpdate(insertCountStockQuery);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void deleteData(String shelfId) {
        databaseConnection = new DatabaseConnection();
        Connection connection = databaseConnection.getConnection();

        try {
            Statement statement = connection.createStatement();

            // SQL query to delete actual count stock records with a specific categoryId
            String deleteCountStockQuery = "DELETE FROM actual_count_stock WHERE shelf_id = '" + shelfId + "'";

            // Execute the delete query
            statement.executeUpdate(deleteCountStockQuery);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
