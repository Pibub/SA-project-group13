package ku.cs.models;

import java.util.ArrayList;

public class StockList {
    private ArrayList<Stock> stocks;
    public StockList(){stocks = new ArrayList<>();}
    public void addStock(Stock stock){stocks.add(stock);}
    public ArrayList<Stock> getStockList(){
        return stocks;
    }
    public Stock findItemByIdAndName(String id, String name) {
        for(Stock stock : stocks)
        {
            if(stock.getItemId().equals(id) && stock.getItemName().equals(name)){
                return stock;
            }
        }
        return null;
    }
    public Stock findItemById(String id){
        for(Stock stock : stocks)
        {
            if (stock.getItemId().equals(id)){
                return stock;
            }
        }
        return null;
    }

    public void addStock(String itemId , String itemName , int amount , String location , String storageDate, String categoryId) {
        itemId = itemId.trim();
        itemName = itemName.trim();
        location = location.trim();
        storageDate = storageDate.trim();
        if (!itemId.equals("") && !itemName.equals("")) {
            Stock exist = findItemByIdAndName(itemId, itemName);
            if (exist == null) {
                stocks.add(new Stock(itemId, itemName, amount, location, storageDate, categoryId));
            }
        }
    }

    public static void updateAmount(){

    }

}
