package ku.cs.services;

import ku.cs.models.Analyze;
import ku.cs.models.AnalyzeList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AnalyzeDataSource implements Datasource<AnalyzeList> {
    private DatabaseConnection databaseConnection;

    public AnalyzeDataSource() {
        databaseConnection = new DatabaseConnection();
    }

    @Override
    public AnalyzeList readData() {
        AnalyzeList analyzeList = new AnalyzeList();
        Connection connection = databaseConnection.getConnection();
        String getAnalyzeData = "SELECT * FROM analyze_count_stock";
        try {
            Statement statement = connection.createStatement();
            ResultSet queryOutput = statement.executeQuery(getAnalyzeData);
            while (queryOutput != null && queryOutput.next()) {
                String categoryId = queryOutput.getString(1);
                String userId = queryOutput.getString(2);
                String userName = queryOutput.getString(3);
                String textAnalyze = queryOutput.getString(4);

                analyzeList.addAnalyze(new Analyze(categoryId, userId, userName, textAnalyze));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return analyzeList;
    }

    @Override
    public void insertData(AnalyzeList analyzeList) {
        Connection connection = databaseConnection.getConnection();
        for (Analyze analyze : analyzeList.getAnalyzes()) {
            try {
                Statement statement = connection.createStatement();

                String checkAnalyzeQuery = "SELECT * FROM analyze_count_stock WHERE category_id = '" + analyze.getCategoryId() + "'";
                ResultSet checkAnalyzeResult = statement.executeQuery(checkAnalyzeQuery);

                if (checkAnalyzeResult.next()) {
                    String updateAnalyzeQuery = "UPDATE analyze_count_stock SET user_id = '" + analyze.getUserId() + "', " +
                            "user_name = '" + analyze.getUserName() + "', " +
                            "text_analyze = '" + analyze.getTextAnalyze() + "' " +
                            "WHERE category_id = '" + analyze.getCategoryId() + "'";
                    statement.executeUpdate(updateAnalyzeQuery);
                } else {
                    String insertAnalyzeQuery = "INSERT INTO analyze_count_stock (category_id, user_id, user_name, text_analyze) " +
                            "VALUES ('" + analyze.getCategoryId() + "', '" + analyze.getUserId() + "', '" +
                            analyze.getUserName() + "', '" + analyze.getTextAnalyze() + "')";
                    statement.executeUpdate(insertAnalyzeQuery);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void deleteData(String categoryId) {
        Connection connection = databaseConnection.getConnection();
        try {
            Statement statement = connection.createStatement();
            String deleteQuery = "DELETE FROM analyze_count_stock WHERE category_id = '" + categoryId + "'";
            statement.executeUpdate(deleteQuery);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
