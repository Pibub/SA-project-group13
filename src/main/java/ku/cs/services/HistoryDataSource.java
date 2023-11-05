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
        Connection connectionHistory = databaseConnection.getConnection();

        for (History history : historyList.getHistories()) {
            try {
                Statement statement = connectionHistory.createStatement();

                String checkHistoryQuery = "SELECT * FROM history WHERE requisition_id = '" + history.getRequisitionId() + "'";
                ResultSet checkHistoryResult = statement.executeQuery(checkHistoryQuery);

                if (!checkHistoryResult.next()) {
                    String insertUserQuery = "INSERT INTO history (user_id, item_id, date, amount, requisition_id) " +
                            "VALUES ('" + history.getUserId() + "', '" + history.getItemId() + "', '" + history.getDate() + "', " +
                            history.getAmount() + ", '" + history.getRequisitionId() + "')";

                    statement.executeUpdate(insertUserQuery);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }



    @Override
    public void deleteData(String itemId) {

    }
}
