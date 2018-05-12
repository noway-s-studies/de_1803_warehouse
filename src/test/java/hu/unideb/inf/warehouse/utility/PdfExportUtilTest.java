package hu.unideb.inf.warehouse.utility;

import com.itextpdf.text.pdf.PdfPTable;
import org.junit.Test;

public class PdfExportUtilTest {

    PdfExportUtil pdf;
    PdfPTable pdfTable = new PdfPTable(new float[]{2, 4, 4, 6});

    @Test(expected = Exception.class)
    public void pdfGeneratorFalseTest(){
        pdf = new PdfExportUtil();
        pdf.pdfGenerator(null, new PdfPTable(new PdfPTable(new float[]{2, 4, 4, 6})));
    }
}
