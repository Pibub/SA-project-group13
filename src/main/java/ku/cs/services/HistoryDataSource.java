package ku.cs.services;

import ku.cs.models.History;
import ku.cs.models.HistoryList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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

    }

    @Override
    public void deleteData(String itemId) {

    }
}
