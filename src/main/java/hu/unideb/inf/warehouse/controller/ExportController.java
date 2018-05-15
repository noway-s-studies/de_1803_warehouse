package hu.unideb.inf.warehouse.controller;

import com.itextpdf.text.Element;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.GrayColor;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import hu.unideb.inf.warehouse.model.PurveyorModel;
import hu.unideb.inf.warehouse.pojo.Purveyor;
import hu.unideb.inf.warehouse.utility.PdfExportUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Az adatok exportálásáért felelős osztály.
 */
public class ExportController {
    /**
     * Logger osztály egy példánya.
     */
    private static final Logger logger = LoggerFactory.getLogger(ExportController.class);
    /**
     * A PurveyorModel osztály egy példánya.
     */
    private PurveyorModel pm = new PurveyorModel();
    /**
     * A Purveyor osztály elemeinek listája.
     */
    private ObservableList<Purveyor> data = FXCollections.observableArrayList();
    /**
     * Exportált adatok mentéséhez használt fájl neve.
     */
    @FXML
    TextField exportFileName;
    /**
     * Könyvtárbetöltő példány az adatok exportálásához használt fájl eléréséhez.
     */
    ClassLoader classLoader = getClass().getClassLoader();

    /**
     * Egérkattintást kezelő metódus, használatával mentésre kerülnek az exportálni kívánt adatok.
     *
     * @param event egéresemény aktuális eseményobjektuma
     */
    @FXML
    public void exportButtonClicked(MouseEvent event){
        if (exportFileName.getText() != null && exportFileName.getText().length()>0){
            data.addAll(pm.getPurveyor());
            float[] columnWidths = {2, 4, 4, 6};
            PdfPTable pdfTable = new PdfPTable(columnWidths);
            pdfTable.setWidthPercentage(100);
            PdfPCell cell = new PdfPCell(new Phrase("KontaktLista"));
            cell.setBackgroundColor(GrayColor.GRAYWHITE);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setColspan(4);
            pdfTable.addCell(cell);
            pdfTable.getDefaultCell().setBackgroundColor(new GrayColor(0.75f));
            pdfTable.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            pdfTable.addCell("Sorszám");
            pdfTable.addCell("Megnevezés");
            pdfTable.addCell("Elérhetőség");
            pdfTable.addCell("Kedvezmény");
            pdfTable.setHeaderRows(1);
            pdfTable.getDefaultCell().setBackgroundColor(GrayColor.GRAYWHITE);
            pdfTable.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            for (int i = 1; i <= data.size(); i++) {
                Purveyor actualPurveyor = data.get(i - 1);
                pdfTable.addCell(""+i);
                pdfTable.addCell(actualPurveyor.getLabel());
                pdfTable.addCell(actualPurveyor.getAvailability());
                pdfTable.addCell(String.valueOf(actualPurveyor.getDiscount()));
            }
            PdfExportUtil pdf = new PdfExportUtil();
            String fileName = exportFileName.getText();
            pdf.pdfGenerator(fileName, pdfTable);
            logger.info("Adatmentés PDF álományba.");
        } else {
            logger.info("Adatmentés PDF álományba nem sikerült, nincs megadva a fájl neve.");
        }
    }
}
