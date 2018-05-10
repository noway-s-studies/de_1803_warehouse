package hu.unideb.inf.warehouse.utility;

import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A beviteli mezők ellenőrzéséért felelős osztály.
 *
 */
public class TextListenerUtil {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * Ellenőrzi és javítja a paraméterként kapott beviteli mező értékét minimum és maximum közötti számértékre.
     *
     * @param tf beviteli mezőt reprezentáló osztály
     * @param min minimum számérték
     * @param max maximum számérték
     */
    public void numberMaxMinTextFieldListener(TextField tf, int min, int max) {
        tf.setTextFormatter(new TextFormatter<Integer>(change -> {
            if (change.isDeleted()) {
                log.debug("Módosított TextField mező.");
                return change;
            }
            String txt = change.getControlNewText();
            if (txt.matches("0\\d+")) {
                return null;
            }
            try {
                int n = Integer.parseInt(txt);
                return min <= n && n <= max ? change : null;
            } catch (NumberFormatException e) {
                log.debug(this.getClass() + " : " + e);
                return null;
            }
        }));
    }
}