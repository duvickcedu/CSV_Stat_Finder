package christian.duvick;

import christian.duvick.parsers.*;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Scanner;
import java.util.concurrent.*;

/**
 * This class has all of the methods that do stat collecting on the given file.
 */
public class Stats {
    private static Stats instance;

    /**
     * Private constructor to follow singleton pattern.
     */
    private Stats() {
    }

    /**
     * gets the instance of the class
     * @return Stats
     */
    public static Stats getInstance() {
        if (instance == null) {
            instance = new Stats();
        }
        return instance;
    }

    /**
     * This method gets the top x amount of maximum values.
     * @param executor used for reading through the file using multithreading
     * @param scanner used for reading the file
     * @param valuesToKeep the amount of values to save
     * @return LinkedList of doubles
     */
    public LinkedList<Double> getNMaximum(ExecutorService executor, Scanner scanner, int valuesToKeep) {
        ConcurrentLinkedQueue<Future<LinkedList<Double>>> topValues = new ConcurrentLinkedQueue<>();
        String line;
        while (scanner.hasNextLine()) {
            line = scanner.nextLine();
            String[] data = line.split(",");
            Future<LinkedList<Double>> futures = executor.submit(new MaxParser(data, valuesToKeep));
            topValues.add(futures);
        }
        scanner.close();
        LinkedList<Double> values = new LinkedList<>();
        while (topValues.size() != 0) {
            try {
                if (topValues.peek().isDone()) {
                    values = consolidateMaxResults(values, Objects.requireNonNull(topValues.poll()).get(), valuesToKeep);
                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        executor.shutdown();
        return values;
    }

    /**
     * This method gets the top x amount of minimum values.
     * @param executor used for reading through the file using multithreading
     * @param scanner used for reading the file
     * @param valuesToKeep the amount of values to save
     * @return LinkedList of doubles
     */
    public LinkedList<Double> getNMinimum(ExecutorService executor, Scanner scanner, int valuesToKeep) {
        ConcurrentLinkedQueue<Future<LinkedList<Double>>> topValues = new ConcurrentLinkedQueue<>();
        String line;
        while (scanner.hasNextLine()) {
            line = scanner.nextLine();
            String[] data = line.split(",");
            Future<LinkedList<Double>> futures = executor.submit(new MinParser(data, valuesToKeep));
            topValues.add(futures);
        }
        scanner.close();
        LinkedList<Double> values = new LinkedList<>();
        while (topValues.size() != 0) {
            try {
                if (topValues.peek().isDone()) {
                    values = consolidateMinResults(values, Objects.requireNonNull(topValues.poll()).get(), valuesToKeep);
                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        executor.shutdown();
        return values;
    }

    /**
     * This method finds the range of the provided file
     * @param executor used for reading through the file using multithreading
     * @param scanner used for reading the file
     * @return double which is the mean
     */
    public double getRange(ExecutorService executor, Scanner scanner) {
        ConcurrentLinkedQueue<Future<LinkedList<Double>>> highLowPairs = new ConcurrentLinkedQueue<>();
        String line;
        while (scanner.hasNextLine()) {
            line = scanner.nextLine();
            String[] data = line.split(",");
            Future<LinkedList<Double>> futures = executor.submit(new RangeParser(data));
            highLowPairs.add(futures);
        }
        scanner.close();
        LinkedList<Double> values = new LinkedList<>();
        double maxValue = Integer.MIN_VALUE;
        double minValue = Integer.MAX_VALUE;
        while (highLowPairs.size() != 0) {
            try {
                if (highLowPairs.peek().isDone()) {
                    values = consolidateRangeResults(values, Objects.requireNonNull(highLowPairs.poll()).get(), minValue, maxValue);
                    maxValue = values.getFirst();
                    minValue = values.getLast();
                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        executor.shutdown();
        return (values.getFirst() - values.getLast());
    }

    /**
     * This method finds the mean of the provided file
     * @param executor used for reading through the file using multithreading
     * @param scanner used for reading the file
     * @return double which is the mean
     */
    public double getMean(ExecutorService executor, Scanner scanner) {
        ConcurrentLinkedQueue<Future<LinkedList<Double>>> means = new ConcurrentLinkedQueue<>();
        String line;
        while (scanner.hasNextLine()) {
            line = scanner.nextLine();
            String[] data = line.split(",");
            Future<LinkedList<Double>> futures = executor.submit(new MeanParser(data));
            means.add(futures);
        }
        scanner.close();
        double total = 0;
        int counter = 0;
        while (means.size() != 0) {
            try {
                if (means.peek().isDone()) {
                    total += Objects.requireNonNull(means.poll()).get().getFirst();
                    counter++;
                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        double mean = total/counter;
        executor.shutdown();
        return mean;
    }

    /**
     * This method finds the median of the provided file
     * @param scanner used for reading the file
     * @return double which is the median
     */
    public double getMedian(Scanner scanner) {
        LinkedList<Double> list = new LinkedList<>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] data = line.split(",");
            for (int i = 0; i < data.length; i++) {
                list.add(Double.parseDouble(Parser.cleanTextContent(data[i])));
            }
        }
        scanner.close();
        Collections.sort(list);
        double median;
        if (!isEven(list.size(), 2)) {
            int middleIndex = list.size()/2;
            median = list.get(middleIndex);
        } else {
            int upperMid = list.size()/2;
            int lowerMid = upperMid - 1;
            median = ((list.get(upperMid) + list.get(lowerMid)) / 2);
        }
        return median;
    }

    /**
     * This method determines if two numbers are evenly divisible.
     * @param number1 first number
     * @param number2 second number
     * @return boolean
     */
    public boolean isEven(int number1, int number2) {
        return number1 % number2 == 0;
    }

    /**
     * This is a helper function that consolidates the futures extracted futures into one given result
     * @param consolidated the aggregated result
     * @param values linked list of values
     * @param minValue lowest value
     * @param maxValue highest value
     * @return range
     */
    public LinkedList<Double> consolidateRangeResults(LinkedList<Double> consolidated, LinkedList<Double> values,
                                                      double minValue, double maxValue) {
        if (values.getFirst() > maxValue) {
            maxValue = values.getFirst();
        }
        if (values.getLast() < minValue) {
            minValue = values.getLast();
        }
        consolidated.clear();
        consolidated.addFirst(maxValue);
        consolidated.addLast(minValue);
        return consolidated;
    }

    /**
     * This is a helper function that consolidates the futures extracted futures into one given result
     * @param consolidated the aggregated result
     * @param values linked list of values
     * @param valuesToKeep number of values to keep
     * @return LinkedList of results
     */
    public LinkedList<Double> consolidateMaxResults(LinkedList<Double> consolidated, LinkedList<Double> values, int valuesToKeep) {
        for (Double value: values) {
            if (consolidated.size() == valuesToKeep) {
                if (value.compareTo(consolidated.getLast()) > 0) {
                    consolidated.removeLast();
                    consolidated.addLast(value);
                }
            } else {
                consolidated.addLast(value);
            }
            consolidated.sort(Collections.reverseOrder());
        }
        return consolidated;
    }

    /**
     * This is a helper function that consolidates the futures extracted futures into one given result
     * @param consolidated the aggregated result
     * @param values linked list of values
     * @param valuesToKeep number of values to keep
     * @return LinkedList of results
     */
    public LinkedList<Double> consolidateMinResults(LinkedList<Double> consolidated, LinkedList<Double> values, int valuesToKeep) {
        for (Double value: values) {
            if (consolidated.size() == valuesToKeep) {
                if (value.compareTo(consolidated.getLast()) < 0) {
                    consolidated.removeLast();
                    consolidated.addLast(value);
                }
            } else {
                consolidated.addLast(value);
            }
            Collections.sort(consolidated);
        }
        return consolidated;
    }
}
