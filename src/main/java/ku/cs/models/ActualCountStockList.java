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

    public boolean isShelfIdExists(String shelfId) {
        for (ActualCountStock actualCountStock : actualCountStocks) {
            if (actualCountStock.getShelfId().equals(shelfId)) {
                return true; // Found a record with the same category ID
            }
        }
        return false; // No record with the same category ID found
    }

    public ActualCountStock findActualCountStockByShelfId(String shelfId) {
        for (ActualCountStock actualCountStock : actualCountStocks) {
            if (actualCountStock.getShelfId().equals(shelfId)) {
                return actualCountStock; // Return the matching ActualCountStock object
            }
        }
        return null; // Return null if no matching ActualCountStock is found
    }
}
