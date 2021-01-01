package christian.duvick.parsers;

import java.util.LinkedList;

/**
 * This class parses the line given to find the range within that line.
 */
public class RangeParser extends Parser<LinkedList<Double>> {
    double maxValue = Integer.MIN_VALUE;
    double minValue = Integer.MAX_VALUE;
    LinkedList<Double> values = new LinkedList<>();
    String[] line;

    public RangeParser(String[] line) {
        this.line = line;
    }

    /**
     * The biggest value is found and kept
     * The smallest value is found and kept
     * the values are stored within a LinkedList
     * view this as a pair where the first is the max
     * the second is the min.
     * @return LinkedList
     */
    @Override
    public LinkedList<Double> call() {
        for (int string = 0; string < line.length; string++) {
            String numberString = super.cleanTextContent(line[string]);
            try {
                double number = Double.parseDouble(numberString);
                if (number > maxValue) {
                    maxValue = number;
                }
                if (number < minValue) {
                    minValue = number;
                }
            } catch (NumberFormatException numberFormatException) {
                numberFormatException.printStackTrace();
            }
        }
        values.addFirst(maxValue);
        values.addLast(minValue);
        return values;
    }
}
