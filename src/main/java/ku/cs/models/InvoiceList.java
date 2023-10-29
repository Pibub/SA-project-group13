package ku.cs.models;

import java.util.ArrayList;
public class InvoiceList{
    private ArrayList<Invoice> invoices;
    public InvoiceList() {invoices = new ArrayList<>();}
    public void addInvoice(Invoice invoice){invoices.add(invoice);}
    public ArrayList<Invoice> getInvoices(){
        return invoices;
    }

    public Invoice findInvoiceByID(String invoiceNo) {
        for(Invoice invoice : invoices)
        {
            if(invoice.isId(invoiceNo)){
                return invoice;
            }
        }
        return null;
    }
}
