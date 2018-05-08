package hu.unideb.inf.warehouse.controller;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;

public class PdfController {

    public void pdfGenerator(String fName, PdfPTable text){
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(fName + ".pdf"));
            document.open();
            Image topLogo = Image.getInstance(getClass().getResource("/pic/logo512.png"));
            topLogo.scaleToFit(100,100);
            topLogo.setAbsolutePosition(250f,720f);
            document.add(topLogo);
            document.add(new Paragraph("\n\n\n\n\n\n"));

            document.add(text);

            Chunk signo = new Chunk("Ez a raktárkészlet nyilvántartó program által generált PDF fájl.");
            document.add(new Paragraph("\n\n\n\n"+signo, FontFactory.getFont("Betűtípus", BaseFont.IDENTITY_H,BaseFont.EMBEDDED)));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (BadElementException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        document.close();
    }
}