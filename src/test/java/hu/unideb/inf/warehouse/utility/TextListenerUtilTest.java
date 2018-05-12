package hu.unideb.inf.warehouse.utility;

import javafx.scene.control.TextField;
import org.junit.Assert;
import org.junit.Test;

public class TextListenerUtilTest {

    TextListenerUtil textListenerUtil;

    @Test
    public void numberMaxMinTextFieldListenerTest(){
        textListenerUtil = new TextListenerUtil();
        TextField textField = new TextField();
        textField.setText("44");
        textListenerUtil.numberMaxMinTextFieldListener(textField, 1, 100);
        Assert.assertEquals(44, 44);
    }
}
