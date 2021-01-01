package christian.duvick;

import java.io.FileNotFoundException;

/**
 * @author Christian Duvick
 *
 * This class serves as the driver for this program.
 * The goals of this program are to be able to read through a .csv file
 * and grab statics from that file.
 */
public class Driver {
    public static void main(String[] args) throws FileNotFoundException {
        UI.getInstance().setFileName();
        UI.getInstance().menu();
    }
}
