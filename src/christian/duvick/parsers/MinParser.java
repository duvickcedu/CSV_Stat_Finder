package christian.duvick.parsers;

import java.util.Collections;
import java.util.LinkedList;

public class MinParser extends Parser<LinkedList<Double>> {
    LinkedList<Double> values = new LinkedList<>();
    String[] line;
    int valuesToKeep;

    public MinParser(String[] line, int valuesToKeep) {
        this.valuesToKeep = valuesToKeep;
        this.line = line;
    }

    //My issue https://www.java67.com/2016/10/10-reasons-of-javalangnumberformatexception-in-java-solution.html number 9
    //Be careful with non-printable characters. (these can come from Excel.)
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
