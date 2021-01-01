package christian.duvick;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.concurrent.*;

/**
 * This class will serve as a facade.
 * It will serve as the 'user system'
 * allowing all of the other classes to interact
 */
public class ProgramSystem {
    private static ProgramSystem instance;
    private static Stats stats;
    private final int numberOfThreads = 20;
    private final String PATH = "Data_Sets/";
    private String fileName;
    private int valuesToKeep;

    /**
     * Private constructor as this class is a singleton
     */
    private ProgramSystem() {
        stats = Stats.getInstance();
    }

    /**
     * gets the instance of the class
     * @return ProgramSystem
     */
    public static ProgramSystem getInstance() {
        if (instance == null) {
            instance = new ProgramSystem();
        }
        return instance;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setVALUES_TO_KEEP(int VALUES_TO_KEEP) {
        this.valuesToKeep = VALUES_TO_KEEP;
    }

    public LinkedList<Double> getNMaximum() throws FileNotFoundException {
        return stats.getNMaximum(Executors.newFixedThreadPool(numberOfThreads),
                new Scanner(new File((PATH.concat(fileName)))), valuesToKeep);
    }

    public LinkedList<Double> getNMinimum() throws FileNotFoundException {
        return stats.getNMinimum(Executors.newFixedThreadPool(numberOfThreads),
                new Scanner(new File((PATH.concat(fileName)))), valuesToKeep);
    }

    public double getRange() throws FileNotFoundException {
        return stats.getRange(Executors.newFixedThreadPool(numberOfThreads),
                new Scanner(new File((PATH.concat(fileName)))));
    }

    public double getMean() throws FileNotFoundException {
        return stats.getMean(Executors.newFixedThreadPool(numberOfThreads),
                new Scanner(new File((PATH.concat(fileName)))));
    }

    public double getMedian() throws FileNotFoundException {
        return stats.getMedian(new Scanner(new File((PATH.concat(fileName)))));
    }
}
