package christian.duvick.parsers;

import java.util.LinkedList;

public class MeanParser extends Parser<LinkedList<Double>> {
    LinkedList<Double> values = new LinkedList<>();
    String[] line;

    public MeanParser(String[] line) {
        this.line = line;
    }

    //My issue https://www.java67.com/2016/10/10-reasons-of-javalangnumberformatexception-in-java-solution.html number 9
    //Be careful with non-printable characters. (these can come from Excel.)
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
