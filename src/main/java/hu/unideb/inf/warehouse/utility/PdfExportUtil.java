package hu.unideb.inf.warehouse.utility;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.FileOutputStream;

/**
 * PDF fájl létrehozásáért felelős osztály.
 */
public class PdfExportUtil {

    private static Logger logger = LoggerFactory.getLogger(PdfExportUtil.class);

    /**
     * PDF fájl létrehozása a őaraméterként kapott adatok segítségével.
     *
     * @param fName fájl neve
     * @param text fájlban megjelenő adatok
     */
    public void pdfGenerator(String fName, PdfPTable text){
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream("src/main/resources/export/" + fName + ".pdf"));
            document.open();
            Image topLogo = Image.getInstance(getClass().getResource("/pic/logo512.png"));
            topLogo.scaleToFit(100,100);
            topLogo.setAbsolutePosition(250f,720f);
            document.add(topLogo);
            document.add(new Paragraph("\n\n\n\n\n\n"));
            document.add(text);
            Chunk signo = new Chunk("Ez a raktárkészlet nyilvántartó program által generált PDF fájl.");
            document.add(new Paragraph("\n\n\n\n"+signo, FontFactory.getFont("Betűtípus", BaseFont.IDENTITY_H,BaseFont.EMBEDDED)));
            logger.info("PDF fájl mentése sikeres.");
        } catch (Exception ex) {
            logger.error("PDF fájl létrehozásánál hiba lépett fel.");
        }
        document.close();
    }
}