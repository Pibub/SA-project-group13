package ku.cs.models;

import java.util.ArrayList;

public class ActualCountStockList {
    private ArrayList<ActualCountStock> actualCountStocks;

    public ActualCountStockList() {
        actualCountStocks = new ArrayList<>();
    }

    public void addActualCountStock(ActualCountStock actualCountStock) {
        actualCountStocks.add(actualCountStock);
    }

    public ArrayList<ActualCountStock> getActualCountStockList() {
        return actualCountStocks;
    }

    public boolean isCategoryIdExists(String categoryId) {
        for (ActualCountStock actualCountStock : actualCountStocks) {
            if (actualCountStock.getCategoryId().equals(categoryId)) {
                return true; // Found a record with the same category ID
            }
        }
        return false; // No record with the same category ID found
    }
}
