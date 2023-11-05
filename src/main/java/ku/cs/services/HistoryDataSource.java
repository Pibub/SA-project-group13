package ku.cs.services;

import ku.cs.models.History;
import ku.cs.models.HistoryList;
import ku.cs.models.Stock;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class HistoryDataSource implements Datasource<HistoryList>{
    private  DatabaseConnection databaseConnection;
    public HistoryDataSource(){}
    public HistoryDataSource(DatabaseConnection databaseConnection){
        this.databaseConnection = databaseConnection;
    }
    @Override
    public HistoryList readData(){
        HistoryList historyList = new HistoryList();
        databaseConnection = new DatabaseConnection();
        Connection connectionHistory = databaseConnection.getConnection();
        String getHistoryData = "SELECT * FROM history";
        try{
            Statement statement = connectionHistory.createStatement();
            ResultSet queryOutput = statement.executeQuery(getHistoryData);
            while(queryOutput != null && queryOutput.next()){
                String user_id = queryOutput.getString(1);
                String item_id = queryOutput.getString(2);
                String date = queryOutput.getString(3);
                HistoryList.addHistory(new History(user_id , item_id , date));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return HistoryList;
    }

    @Override
    public void insertData(HistoryList historyList) {

    }

    @Override
    public void deleteData(String itemId) {

    }

}
