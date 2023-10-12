package ku.cs.models;

import java.util.ArrayList;

public class StockList {
    private ArrayList<Stock> stocks;
    public StockList(){stocks = new ArrayList<>();}
    public void addStock(Stock stock){stocks.add(stock);}

}
