package com.coaching.util;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.PageSize;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import java.io.OutputStream;
import java.util.List;

/**
 * Generates simple tabular PDF reports (fee transactions, salary slips,
 * exam result reports) using the OpenPDF library (LGPL/MPL, no Spring needed).
 */
public class PdfGenerator {

    private PdfGenerator() {
    }

    public static void generateTablePdf(OutputStream out, String title, String subTitle,
                                         String[] headers, List<String[]> rows) throws Exception {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, out);
        document.open();

        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
        Font subFont = FontFactory.getFont(FontFactory.HELVETICA, 11);
        Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, java.awt.Color.WHITE);
        Font cellFont = FontFactory.getFont(FontFactory.HELVETICA, 9);

        Paragraph p1 = new Paragraph(title, titleFont);
        p1.setAlignment(Element.ALIGN_CENTER);
        document.add(p1);

        if (subTitle != null) {
            Paragraph p2 = new Paragraph(subTitle, subFont);
            p2.setAlignment(Element.ALIGN_CENTER);
            p2.setSpacingAfter(15);
            document.add(p2);
        }

        PdfPTable table = new PdfPTable(headers.length);
        table.setWidthPercentage(100);
        for (String h : headers) {
            PdfPCell cell = new PdfPCell(new Phrase(h, headFont));
            cell.setBackgroundColor(new java.awt.Color(40, 60, 90));
            cell.setPadding(6);
            table.addCell(cell);
        }
        for (String[] row : rows) {
            for (String val : row) {
                PdfPCell cell = new PdfPCell(new Phrase(val == null ? "" : val, cellFont));
                cell.setPadding(5);
                table.addCell(cell);
            }
        }
        document.add(table);

        Paragraph footer = new Paragraph("\nGenerated on " + new java.util.Date(),
                FontFactory.getFont(FontFactory.HELVETICA_OBLIQUE, 8));
        document.add(footer);

        document.close();
    }
}
