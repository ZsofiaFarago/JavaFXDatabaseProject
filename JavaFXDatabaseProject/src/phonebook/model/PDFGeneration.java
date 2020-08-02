package phonebook.model;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.collections.ObservableList;

import java.io.FileOutputStream;

public class PDFGeneration {

    public void pdfGeneration(String fileName, ObservableList<PersonModel> data) {
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(fileName + ".pdf"));
            document.open();

            // Céges logó
            Image image = Image.getInstance(getClass().getResource("/logo.jpg"));
            image.scaleToFit(200, 86);
            image.setAbsolutePosition(200f, 750f);
            document.add(image);

            // document.add(new Paragraph("\n\n\n\n\n\n\n\n\n\n\n\n\n" + text, FontFactory.getFont("betutipus", BaseFont.IDENTITY_H, BaseFont.EMBEDDED)));

            // Táblázat
            float[] columnWidths = new float[] {3, 3, 4};
            PdfPTable table = new PdfPTable(columnWidths);
            table.setWidthPercentage(100);
            // Fejléccella hozzáadása
            PdfPCell cell = new PdfPCell(new Phrase("Kontaktlista"));
            cell.setColspan(3);
            cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            table.getDefaultCell().setBackgroundColor(BaseColor.DARK_GRAY);
            table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell("Vezetéknév");
            table.addCell("Keresztnév");
            table.addCell("EMail");
            table.getDefaultCell().setBackgroundColor(BaseColor.WHITE);

            for (PersonModel person : data) {
                table.addCell(person.getFirstName());
                table.addCell(person.getLastName());
                table.addCell(person.getEmail());
            }

            document.add(table);

            // Aláírás
            Chunk signature = new Chunk("\n\nGenerálva a Telefonkönyv alkalmazás segítségével.");
            Paragraph base = new Paragraph(signature);
            document.add(base);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        document.close();
    }
}
