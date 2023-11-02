package ku.cs.models;

import java.time.LocalDate;

public class Invoice {
    private String itemId;
    private String itemName;
    private Float qty;
    private String description;
    private String vendor;
    private String invoiceNo;
    private String poNo;
    private String dueDate;
    private String line;
    private String keepLoc;
    private String receiveLoc;
    private String status;

    public Invoice(String itemId, String itemName, Float qty, String description, String vendor, String invoiceNo, String poNo, String dueDate, String line, String keepLoc, String receiveLoc, String status) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.qty = qty;
        this.description = description;
        this.vendor = vendor;
        this.invoiceNo = invoiceNo;
        this.poNo = poNo;
        this.dueDate = dueDate;
        this.line = line;
        this.keepLoc = keepLoc;
        this.receiveLoc = receiveLoc;
        this.status = status;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Float getQty() {
        return qty;
    }

    public void setQty(Float qty) {
        this.qty = qty;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public String getPoNo() {
        return poNo;
    }

    public void setPoNo(String poNo) {
        this.poNo = poNo;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public String getKeepLoc() {
        return keepLoc;
    }

    public void setKeepLoc(String keepLoc) {
        this.keepLoc = keepLoc;
    }

    public String getReceiveLoc() {
        return receiveLoc;
    }

    public void setReceiveLoc(String receiveLoc) {
        this.receiveLoc = receiveLoc;
    }

    public String getStatus() {
        return status;
    }


    public void setStatus(String status) {
        this.status = status;
    }
    public boolean isId(String id){
        return this.itemId.equals(id);
    }
}
