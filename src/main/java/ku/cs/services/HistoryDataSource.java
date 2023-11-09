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
                int qty = queryOutput.getInt(4);
                String requisitionId = queryOutput.getString(5);
                String unit = queryOutput.getString(6);

                historyList.addHistory(new History(userId, itemId, date, qty, requisitionId, unit));
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
                    String insertUserQuery = "INSERT INTO history (user_id, item_id, date, qty, requisition_id, unit) " +
                            "VALUES ('" + history.getUserId() + "', '" + history.getItemId() + "', '" + history.getDate() + "', " +
                            history.getQty() + ", '" + history.getRequisitionId() + "', '" + history.getUnit() + "')";


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
