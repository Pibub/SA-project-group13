package ku.cs.models;

import java.util.ArrayList;
public class CountStockList {
    private ArrayList<CountStock> countStocks;
    public CountStockList(){ countStocks = new ArrayList<>();}
    public void addCountStock(CountStock countStock) {countStocks.add(countStock);}
    public ArrayList<CountStock> getCountStocks(){ return countStocks;}
    public CountStock findTotalByCategoryId(String CId){
        for (CountStock countStock : countStocks){
            if (countStock.getCategoryId().equals(CId)){
                return countStock;
            }
        }
        return null;
    }
}
