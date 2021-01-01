package christian.duvick.parsers;

import java.util.LinkedList;

/**
 * This class is the parser for finding mean of a file.
 */
public class MeanParser extends Parser<LinkedList<Double>> {
    LinkedList<Double> values = new LinkedList<>();
    String[] line;

    public MeanParser(String[] line) {
        this.line = line;
    }

    /**
     * This method find the mean of the lines that are given
     * @return LinkedList
     */
    @Override
    public LinkedList<Double> call() {
        double total = 0;
        int count = 0;
        for (int string = 0; string < line.length; string++) {
            String numberString = super.cleanTextContent(line[string]);
            try {
                double number = Double.parseDouble(numberString);
                total += number;
                count++;
            } catch (NumberFormatException numberFormatException) {
                numberFormatException.printStackTrace();
            }
        }
        double mean = total / count;
        values.add(mean);
        return values;
    }
}
