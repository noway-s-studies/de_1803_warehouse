package hu.unideb.inf.warehouse.model;

import hu.unideb.inf.warehouse.pojo.Place;
import hu.unideb.inf.warehouse.pojo.Place;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertFalse;

public class PlaceModelTest {

    PlaceModel PlaceModel01 = new PlaceModel();
    Place Place01 = new Place();
    Place Place02 = new Place();
    Place Place03 = new Place();
    Place PlaceDb01 = new Place();
    Place PlaceDb02 = new Place();
    Place PlaceDb03 = new Place();
    String label01 = "";
    String label02 = "";
    String label03 = "";
    String text = "\n";


    @Test
    public void initPlaceModelTest() {
        label01 = "Aurum_Test";
        label02 = "Bika_Test";
        label03 = "Kanál_Test";

        /**
         * Place()
         */
        Place01 = new Place(label01, "Budapest", 1);
        Place02 = new Place(label02, "Kecskemét", 2);
        Place03 = new Place(label03, "Paks", 3);

        /**
         * addPlace()
         */
        PlaceModel01.addPlace(Place01);
        PlaceModel01.addPlace(Place02);
        PlaceModel01.addPlace(Place03);

        /**
         * getPlace()
         */
        List<Place> placeList = PlaceModel01.getPlace();
        for (Place Place : placeList){
            if (Place.getLabel().equals(label01)){
                Assert.assertNotNull("Van id: " + Place.getId(), Place.getId());
                PlaceDb01 = Place;
                text += "Id: "+Place.getId()+", Label: "+Place.getLabel() +", Elérhetőség: "+Place.getAvailability()+", Súlyozás: "+String.valueOf(Place.getWeighting()) +"\n";
            }
            if (Place.getLabel().equals(label02)){
                Assert.assertNotNull("Van id: " + Place.getId(), Place.getId());
                PlaceDb02 = Place;
                text += "Id: "+Place.getId()+", Label: "+Place.getLabel() +", Elérhetőség: "+Place.getAvailability()+", Súlyozás: "+String.valueOf(Place.getWeighting())+"\n";
            }
            if (Place.getLabel().equals(label03)){
                Assert.assertNotNull("Van id: " + Place.getId(), Place.getId());
                PlaceDb03 = Place;
                text += "Id: "+Place.getId()+", Label: "+Place.getLabel() +", Elérhetőség: "+Place.getAvailability()+", Súlyozás: "+String.valueOf(Place.getWeighting())+"\n";
            }
        }

        /**
         * setStatus()
         * getStatus()
         * modPlace()
         */
        Assert.assertTrue("Van beállított státusz érték ami igaz.", PlaceDb01.getStatus());
        PlaceDb01.setStatus(false);
        PlaceModel01.modPlace(PlaceDb01);
        List<Place> placeList2 = PlaceModel01.getPlace();
        for (Place Place : placeList2){
            if (Place.getLabel().equals(label01)){
                assertFalse("Beállított státusz érték hamis.", Place.getStatus());
                text += "Státusz mod: " + Place.getStatus() + "\n";
            }
        }

        /**
         * getPlaceName()
         */
        List<String> placeList3 = PlaceModel01.getPlaceName();
        Assert.assertNotNull("Üress áru név lista", placeList3);
        for (String label : placeList3)
            text += label + "\n";

        /**
         * removePlace()
         */
        PlaceModel01.removePlace(PlaceDb01);
        PlaceModel01.removePlace(PlaceDb02);
        PlaceModel01.removePlace(PlaceDb03);
        List<String> PlaceListEND = PlaceModel01.getPlaceName();
        Assert.assertTrue("Sikeres törlés", PlaceListEND.size() == 0);

        System.out.println(text);
    }
}