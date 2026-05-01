package com.acme.cargotrak.report;

import java.io.OutputStream;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.acme.cargotrak.util.Util;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

public class ArAgingPdfGenerator {

    private static final Font H1   = new Font(Font.HELVETICA, 18, Font.BOLD);
    private static final Font H2   = new Font(Font.HELVETICA, 12, Font.BOLD);
    private static final Font BODY = new Font(Font.HELVETICA, 10, Font.NORMAL);

    public static void write(OutputStream out, List rows, Date asOf) throws Exception {
        Document doc = new Document(PageSize.LETTER.rotate());
        PdfWriter.getInstance(doc, out);
        doc.open();
        doc.add(new Paragraph("AR Aging Report", H1));
        doc.add(new Paragraph("As of " + Util.formatDate(asOf), BODY));
        doc.add(new Paragraph(" ", BODY));

        PdfPTable t = new PdfPTable(7);
        t.setWidthPercentage(100);
        t.addCell(new PdfPCell(new Phrase("Customer code", H2)));
        t.addCell(new PdfPCell(new Phrase("Customer name", H2)));
        t.addCell(new PdfPCell(new Phrase("0-30",   H2)));
        t.addCell(new PdfPCell(new Phrase("31-60",  H2)));
        t.addCell(new PdfPCell(new Phrase("61-90",  H2)));
        t.addCell(new PdfPCell(new Phrase("90+",    H2)));
        t.addCell(new PdfPCell(new Phrase("Total",  H2)));

        Iterator it = rows.iterator();
        while (it.hasNext()) {
            Map row = (Map) it.next();
            t.addCell(new Phrase(String.valueOf(row.get("customerCode")), BODY));
            t.addCell(new Phrase(String.valueOf(row.get("customerName")), BODY));
            t.addCell(new Phrase(Util.formatMoney((java.math.BigDecimal) row.get("bucket0_30")), BODY));
            t.addCell(new Phrase(Util.formatMoney((java.math.BigDecimal) row.get("bucket31_60")), BODY));
            t.addCell(new Phrase(Util.formatMoney((java.math.BigDecimal) row.get("bucket61_90")), BODY));
            t.addCell(new Phrase(Util.formatMoney((java.math.BigDecimal) row.get("bucket90Plus")), BODY));
            t.addCell(new Phrase(Util.formatMoney((java.math.BigDecimal) row.get("totalOutstanding")), BODY));
        }
        doc.add(t);
        doc.close();
    }
}
