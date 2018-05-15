package hu.unideb.inf.warehouse.model;

import hu.unideb.inf.warehouse.pojo.Purveyor;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class PurveyorModelTest {

    PurveyorModel PurveyorModel01 = new PurveyorModel();
    Purveyor Purveyor01 = new Purveyor();
    Purveyor Purveyor02 = new Purveyor();
    Purveyor Purveyor03 = new Purveyor();
    Purveyor PurveyorDb01 = new Purveyor();
    Purveyor PurveyorDb02 = new Purveyor();
    Purveyor PurveyorDb03 = new Purveyor();
    String label01 = "";
    String label02 = "";
    String label03 = "";
    String text = "\n";


    @Test
    public void initPurveyorModelTest() {
        label01 = "Metro_Test";
        label02 = "Tesco_Test";
        label03 = "Coop_Test";

        /**
         * Purveyor()
         */
        Purveyor01 = new Purveyor(label01, "Debrecen", 10);
        Purveyor02 = new Purveyor(label02, "Mátészalka", 20);
        Purveyor03 = new Purveyor(label03, "Porcsalma", 30);

        /**
         * addPurveyor()
         */
        PurveyorModel01.addPurveyor(Purveyor01);
        PurveyorModel01.addPurveyor(Purveyor02);
        PurveyorModel01.addPurveyor(Purveyor03);

        /**
         * getPurveyor()
         */
        List<Purveyor> PurveyorList = PurveyorModel01.getPurveyor();
        for (Purveyor Purveyor : PurveyorList){
            if (Purveyor.getLabel().equals(label01)){
                Assert.assertNotNull("Van id: " + Purveyor.getId(), Purveyor.getId());
                PurveyorDb01 = Purveyor;
                text += "Id: "+Purveyor.getId()+", Label: "+Purveyor.getLabel() +", Elérhetőség: "+Purveyor.getAvailability()+", Kedvezmény: "+String.valueOf(Purveyor.getDiscount()) +"\n";
            }
            if (Purveyor.getLabel().equals(label02)){
                Assert.assertNotNull("Van id: " + Purveyor.getId(), Purveyor.getId());
                PurveyorDb02 = Purveyor;
                text += "Id: "+Purveyor.getId()+", Label: "+Purveyor.getLabel() +", Elérhetőség: "+Purveyor.getAvailability()+", Kedvezmény: "+String.valueOf(Purveyor.getDiscount())+"\n";
            }
            if (Purveyor.getLabel().equals(label03)){
                Assert.assertNotNull("Van id: " + Purveyor.getId(), Purveyor.getId());
                PurveyorDb03 = Purveyor;
                text += "Id: "+Purveyor.getId()+", Label: "+Purveyor.getLabel() +", Elérhetőség: "+Purveyor.getAvailability()+", Kedvezmény: "+String.valueOf(Purveyor.getDiscount())+"\n";
            }
        }

        /**
         * setStatus()
         * getStatus()
         * modPurveyor()
         */
        Assert.assertTrue("Van beállított státusz érték ami igaz.", PurveyorDb01.getStatus());
        PurveyorDb01.setStatus(false);
        PurveyorModel01.modPurveyor(PurveyorDb01);
        List<Purveyor> PurveyorList2 = PurveyorModel01.getPurveyor();
        for (Purveyor Purveyor : PurveyorList2){
            if (Purveyor.getLabel().equals(label01)){
                assertFalse("Beállított státusz érték hamis.", Purveyor.getStatus());
                text += "Státusz mod: " + Purveyor.getStatus() + "\n";
            }
        }

        /**
         * getPurveyorName()
         */
        List<String> PurveyorList3 = PurveyorModel01.getPurveyorName();
        Assert.assertNotNull("Üress beszerző név lista", PurveyorList);
        for (String label : PurveyorList3)
            text += label + "\n";

        /**
         * removePurveyor()
         */
        PurveyorModel01.removePurveyor(PurveyorDb01);
        PurveyorModel01.removePurveyor(PurveyorDb02);
        PurveyorModel01.removePurveyor(PurveyorDb03);
        List<String> PurveyorListEND = PurveyorModel01.getPurveyorName();
        Assert.assertTrue("Sikeres törlés", PurveyorListEND.size() == 0);

        System.out.println(text);
    }
}