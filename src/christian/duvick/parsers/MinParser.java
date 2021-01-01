package christian.duvick.parsers;

import java.util.Collections;
import java.util.LinkedList;

/**
 * This class is the parser for finding min numbers within a file.
 */
public class MinParser extends Parser<LinkedList<Double>> {
    LinkedList<Double> values = new LinkedList<>();
    String[] line;
    int valuesToKeep;

    public MinParser(String[] line, int valuesToKeep) {
        this.valuesToKeep = valuesToKeep;
        this.line = line;
    }

    /**
     * This method find the top x amount of numbers to keep
     * @return LinkedList
     */
    @Override
    public LinkedList<Double> call() {
        for (int string = 0; string < line.length; string++) {
            String numberString = super.cleanTextContent(line[string]);
            try {
                double number = Double.parseDouble(numberString);
                values.add(number);
            } catch (NumberFormatException numberFormatException) {
                numberFormatException.printStackTrace();
            }
        }
        Collections.sort(values);
        while (values.size() > valuesToKeep) {
            values.removeLast();
        }
        return values;
    }
}
