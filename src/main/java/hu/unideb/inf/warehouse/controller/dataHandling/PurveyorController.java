package hu.unideb.inf.warehouse.controller.dataHandling;

import hu.unideb.inf.warehouse.model.PurveyorModel;
import hu.unideb.inf.warehouse.pojo.Purveyor;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import javafx.util.converter.NumberStringConverter;

import java.net.URL;
import java.util.ResourceBundle;

public class PurveyorController implements Initializable {

    private PurveyorModel pm;
    private ObservableList<Purveyor> data = FXCollections.observableArrayList();

    @FXML
    TableView table;
    @FXML
    TextField inputDescription;
    @FXML
    TextField inputAddress;
    @FXML
    TextField inputDiscount;
    @FXML
    Button addNewContactButton;

    private TableColumn<Purveyor, String> label = null;
    private TableColumn<Purveyor, String> addressColumn = null;
    private TableColumn<Purveyor, Number> discountColumn = null;

    @FXML
    public void actionNewContactButton(MouseEvent event){
        if (inputDescription != null && inputAddress != null && inputDiscount != null){
            Purveyor newPureyor = new Purveyor(
                    inputDescription.getText(),
                    inputAddress.getText(),
                    Integer.parseInt(inputDiscount.getText().trim())
            );
            data.add(newPureyor);
            pm.addPurveyor(newPureyor);
            inputDescription.clear();
            inputDiscount.clear();
            inputAddress.clear();
        }
    }

    @FXML
    public void exportButtonClicked(MouseEvent event){
//        if (exportFileName.getText() != null && exportFileName.getText().length()>0){
//            float[] columnWidths = {2, 4, 4, 6};
//            PdfPTable pdfTable = new PdfPTable(columnWidths);
//            pdfTable.setWidthPercentage(100);
//            PdfPCell cell = new PdfPCell(new Phrase("KontaktLista"));
//            cell.setBackgroundColor(GrayColor.GRAYWHITE);
//            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//            cell.setColspan(4);
//            pdfTable.addCell(cell);
//
//            pdfTable.getDefaultCell().setBackgroundColor(new GrayColor(0.75f));
//            pdfTable.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
//            pdfTable.addCell("Sorszám");
//            pdfTable.addCell("Megnevezés");
//            pdfTable.addCell("Elérhetőség");
//            pdfTable.addCell("Kedvezmény");
//            pdfTable.setHeaderRows(1);
//
//            pdfTable.getDefaultCell().setBackgroundColor(GrayColor.GRAYWHITE);
//            pdfTable.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
//
//            for (int i = 1; i <= data.size(); i++) {
//                Purveyor actualPurveyor = data.get(i - 1);
//
//                pdfTable.addCell(""+i);
//                pdfTable.addCell(actualPurveyor.getLabel());
//                pdfTable.addCell(actualPurveyor.getAvailability());
//                pdfTable.addCell(String.valueOf(actualPurveyor.getDiscount()));
//            }
//            PdfController pdf = new PdfController();
//            String fileName = exportFileName.getText();
//            pdf.pdfGenerator(fileName, pdfTable);
//
//        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        pm = new PurveyorModel();
        setTableData();
    }

    private void setTableData() {

        label = new TableColumn("Megnevezés");
        label.setMinWidth(200);
        label.setCellFactory(TextFieldTableCell.forTableColumn());
        label.setCellValueFactory(new PropertyValueFactory<Purveyor, String>("label"));

        addressColumn = new TableColumn("Elérhetőség");
        addressColumn.setMinWidth(100);
        addressColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        addressColumn.setCellValueFactory(new PropertyValueFactory<Purveyor, String>("availability"));

        discountColumn = new TableColumn("Kedvezmény");
        discountColumn.setMinWidth(100);
        discountColumn.setCellFactory(TextFieldTableCell.<Purveyor, Number>forTableColumn(new NumberStringConverter()));
        discountColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Purveyor, Number>, ObservableValue<Number>>() {
            @Override
            public ObservableValue<Number> call(TableColumn.CellDataFeatures<Purveyor, Number> param) {
                return new SimpleIntegerProperty(param.getValue().getDiscount());
            }
        });


        table.getColumns().addAll(label, addressColumn, discountColumn);


        data.addAll(pm.getPurveyor());
        table.setItems(data);
    }
}

