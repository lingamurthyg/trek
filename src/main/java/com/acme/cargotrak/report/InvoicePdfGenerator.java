package com.acme.cargotrak.report;

import java.io.OutputStream;
import java.util.Iterator;

import com.acme.cargotrak.domain.Invoice;
import com.acme.cargotrak.domain.InvoiceLineItem;
import com.acme.cargotrak.util.Util;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

/**
 * @author M. Iyer 2010-09 (original)
 * @author L. Chen 2012-09 added line items table
 */
public class InvoicePdfGenerator {

    private static final Font H1   = new Font(Font.HELVETICA, 18, Font.BOLD);
    private static final Font H2   = new Font(Font.HELVETICA, 12, Font.BOLD);
    private static final Font BODY = new Font(Font.HELVETICA, 10, Font.NORMAL);

    public static void write(OutputStream out, Invoice invoice) throws Exception {
        Document doc = new Document(PageSize.LETTER);
        PdfWriter.getInstance(doc, out);
        doc.open();
        doc.add(new Paragraph("ACME Freight Logistics", H1));
        doc.add(new Paragraph("INVOICE", H1));
        doc.add(new Paragraph(" ", BODY));
        doc.add(new Paragraph("Invoice No:  " + invoice.getInvoiceNo(), H2));
        doc.add(new Paragraph("Customer:    #" + invoice.getCustomerId(), BODY));
        doc.add(new Paragraph("Invoice date: " + Util.formatDate(invoice.getInvoiceDate()), BODY));
        doc.add(new Paragraph("Due date:     " + Util.formatDate(invoice.getDueDate()), BODY));
        doc.add(new Paragraph("Status:       " + invoice.getStatus(), BODY));
        doc.add(new Paragraph(" ", BODY));

        PdfPTable t = new PdfPTable(4);
        t.setWidthPercentage(100);
        t.addCell(headerCell("Description"));
        t.addCell(headerCell("Quantity"));
        t.addCell(headerCell("Unit price"));
        t.addCell(headerCell("Line total"));
        if (invoice.getLineItems() != null) {
            Iterator it = invoice.getLineItems().iterator();
            while (it.hasNext()) {
                InvoiceLineItem li = (InvoiceLineItem) it.next();
                t.addCell(new Phrase(li.getDescription(), BODY));
                t.addCell(new Phrase(li.getQuantity() == null ? "" : li.getQuantity().toString(), BODY));
                t.addCell(new Phrase(Util.formatMoney(li.getUnitPrice()), BODY));
                t.addCell(new Phrase(Util.formatMoney(li.getLineTotal()), BODY));
            }
        }
        doc.add(t);

        doc.add(new Paragraph(" ", BODY));
        doc.add(new Paragraph("Subtotal: " + Util.formatMoney(invoice.getSubtotal()), BODY));
        doc.add(new Paragraph("Tax:      " + Util.formatMoney(invoice.getTax()), BODY));
        doc.add(new Paragraph("Total:    " + Util.formatMoney(invoice.getTotal()), H2));
        doc.add(new Paragraph("Paid:     " + Util.formatMoney(invoice.getAmountPaid()), BODY));
        doc.add(new Paragraph("Balance:  " + Util.formatMoney(invoice.getOutstanding()), H2));
        doc.add(new Paragraph(" ", BODY));
        doc.add(new Paragraph("Thank you for shipping with ACME.", BODY));
        doc.close();
    }

    private static PdfPCell headerCell(String label) {
        PdfPCell c = new PdfPCell(new Phrase(label, H2));
        c.setHorizontalAlignment(Element.ALIGN_LEFT);
        return c;
    }
}
