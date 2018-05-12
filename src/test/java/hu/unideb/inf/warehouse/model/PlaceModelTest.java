package hu.unideb.inf.warehouse.model;

import hu.unideb.inf.warehouse.pojo.Place;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class PlaceModelTest {

    PlaceModel placeModel01;
    PlaceModel placeModel02;
    Place place01;
    Place place02;
    Place place03;
    String label01;
    String label02;
    String label03;

    @Before
    public void init() {
        placeModel01 = new PlaceModel();
        label01 = "Hotel Aurum";
        label02 = "Arany Bika";
        label03 = "Ezüst Kanál";
        place01 = new Place(label01, "Hajdúszoboszló", 1);
        place02 = new Place(label02, "Debrecen", 2);
        place03 = new Place(label03, "Porcsalma", 3);

    }


    @Test
    public void addPlace() {
        placeModel01.addPlace(place01);
        placeModel01.addPlace(place02);
        placeModel01.addPlace(place03);
    }


    @Test
    public void getPlace() {
        Assert.assertNull("Még nincs id", place03.getId());
        placeModel02 = new PlaceModel();
        placeModel02.getPlace();
        placeModel01.modPlace(place03);
        List<Place> placeList = placeModel02.getPlace();
        int weighting = -1;
        for (Place place : placeList){
            if (place.getLabel().equals("Ezüst Kanál")){
                Assert.assertNotNull("Van id: " + place.getId(), place.getId());
                weighting = place.getWeighting();
            }
        }
        Assert.assertEquals("Sikeres lekérdezés", weighting,3);
    }

    @Test
    public void modPlace() {
        placeModel01.modPlace(place03);
    }

    @Test
    public void getPlaceName() {
        List<String> stringList = null;
        stringList = placeModel01.getPlaceName();
        System.out.println(stringList);
        Assert.assertFalse("Nem üeress név lista", stringList.isEmpty());
    }

    @Test
    public void removePlace() {
        placeModel01.removePlace(new Place("delLabel", "delAdd", 0));
    }

}