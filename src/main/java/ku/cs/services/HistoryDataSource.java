package ku.cs.services;

import ku.cs.models.History;
import ku.cs.models.HistoryList;
import ku.cs.models.Stock;

import java.sql.*;

public class HistoryDataSource implements Datasource<HistoryList>{
    private DatabaseConnection databaseConnection;
    public HistoryDataSource(){}
    @Override
    public HistoryList readData() {
        HistoryList historyList = new HistoryList();
        databaseConnection = new DatabaseConnection();
        Connection connectionHistory = databaseConnection.getConnection();
        String getHistoryData = "SELECT * FROM history";
        try{
            Statement statement = connectionHistory.createStatement();
            ResultSet queryOutput = statement.executeQuery(getHistoryData);
            while (queryOutput != null && queryOutput.next()){
                String userId = queryOutput.getString(1);
                String itemId = queryOutput.getString(2);
                String date = queryOutput.getString(3);
                float amount = queryOutput.getFloat(4);
                String requisitionId = queryOutput.getString(5);

                historyList.addHistory(new History(userId, itemId, date, amount, requisitionId));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return historyList;
    }

    @Override
    public void insertData(HistoryList historyList) {
        databaseConnection = new DatabaseConnection();
        Connection connectionUser = databaseConnection.getConnection();
        for (History history : historyList.getHistories()) {
            try {
                Statement statement = connectionUser.createStatement();
                String insertHistoryQuery = "INSERT INTO history (user_id, item_id, date, amount, requisition_id) " +
                        "VALUES (?, ?, ?, ?, ?)";
                try (PreparedStatement preparedStatement = connectionUser.prepareStatement(insertHistoryQuery)) {
                    preparedStatement.setString(1, history.getUserId());
                    preparedStatement.setString(2, history.getItemId());
                    preparedStatement.setString(3, history.getDate());
                    preparedStatement.setFloat(4, history.getAmount());
                    preparedStatement.setString(5, history.getRequisitionId());

                    preparedStatement.executeUpdate();

                    statement.executeUpdate(insertHistoryQuery);
            }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void deleteData(String itemId) {

    }
}
