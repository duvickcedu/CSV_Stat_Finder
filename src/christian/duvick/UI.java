package christian.duvick;

import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;

import static christian.duvick.parsers.Parser.cleanTextContent;

/**
 * This class will be a UI class (non-GUI)
 */
public class UI {
    private static UI instance;
    private static ProgramSystem programSystem;
    private final Scanner scanner = new Scanner(System.in);
    private final int EXIT = 0;
    private final int GET_MAX = 1;
    private final int GET_MIN = 2;
    private final int GET_N_MAX = 3;
    private final int GET_N_MIN = 4;
    private final int GET_MEDIAN = 5;
    private final int GET_MEAN = 6;
    private final int GET_RANGE = 7;
    private final int SET_FILE_NAME = 8;


    private UI() {
        programSystem = ProgramSystem.getInstance();
    }

    public static UI getInstance() {
        if (instance == null) {
            instance = new UI();
        }
        return instance;
    }

    public void menu() throws FileNotFoundException {
        getOptions();
        System.out.println();
        System.out.print("Enter choice: ");
        String choiceString = scanner.nextLine();
        choiceString = choiceString.trim();
        int choice;
        try{
            choice = Integer.parseInt(choiceString);
        } catch (NumberFormatException nfe) {
            choice = -1;
        }
        System.out.println();
        switch (choice) {
            case EXIT:
                System.out.println("Program ending");
                System.exit(0);
                break;
            case GET_MAX:
                System.out.println("Get max selected.");
                getMaximum();
                menu();
                break;
            case GET_MIN:
                System.out.println("Get min selected.");
                getMinimum();
                menu();
                break;
            case GET_N_MAX:
                System.out.println("Get N max selected.");
                getNMaximum();
                menu();
                break;
            case GET_N_MIN:
                System.out.println("Get N min selected.");
                getNMinimum();
                menu();
                break;
            case GET_MEDIAN:
                System.out.println("Get median selected.");
                getMedian();
                menu();
                break;
            case GET_MEAN:
                System.out.println("Get mean selected.");
                getMean();
                menu();
                break;
            case GET_RANGE:
                System.out.println("Get range selected.");
                getRange();
                menu();
                break;
            case SET_FILE_NAME:
                setFileName();
                menu();
                break;
            default:
                System.out.println("Invalid choice; please try again.");
                menu();
                break;
        }
    }

    private void setFileName() {
        System.out.println("Enter the file name (must be located within /data_sets folder)");
        System.out.print("data_sets/");
        String file = cleanTextContent(scanner.nextLine());
        programSystem.setFileName(file);
    }

    public void getMaximum() throws FileNotFoundException {
        System.out.println("Getting the max value for file: " + programSystem.getFileName());
        programSystem.setVALUES_TO_KEEP(1);
        printResults(programSystem.getNMaximum());
    }

    public void getNMaximum() throws FileNotFoundException {
        System.out.print("Values to find: ");
        int valuesToKeep = Integer.parseInt(cleanTextContent(scanner.nextLine()));
        programSystem.setVALUES_TO_KEEP(valuesToKeep);
        System.out.println("Getting " + valuesToKeep + " max values for file: " + programSystem.getFileName());
        printResults(programSystem.getNMaximum());
    }

    public void getMinimum() throws FileNotFoundException {
        System.out.println("Getting the min value for file: " + programSystem.getFileName());
        programSystem.setVALUES_TO_KEEP(1);
        printResults(programSystem.getNMinimum());
    }

    public void getNMinimum() throws FileNotFoundException {
        System.out.print("Values to find: ");
        int valuesToKeep = Integer.parseInt(cleanTextContent(scanner.nextLine()));
        programSystem.setVALUES_TO_KEEP(valuesToKeep);
        System.out.println("Getting " + valuesToKeep + " min values for file: " + programSystem.getFileName());
        printResults(programSystem.getNMinimum());
    }

    public void getRange() throws FileNotFoundException {
        System.out.println("Getting range.");
        System.out.println(programSystem.getRange());
    }

    public void getMean() throws FileNotFoundException {
        System.out.println("Getting mean.");
        System.out.println(programSystem.getMean());
    }

    public void getMedian() throws FileNotFoundException {
        System.out.println("Getting the Median.");
        System.out.println(programSystem.getMedian());
    }

    public void printResults(LinkedList<Double> values) {
        System.out.print("Results: ");
        Iterator<Double> iterator = values.iterator();
        while(iterator.hasNext()) {
            Double value = iterator.next();
            if (iterator.hasNext()) {
                System.out.print(value + ", ");
            } else {
                System.out.println(value);
            }
        }
//        System.out.println();
    }

    private void getOptions() {
        System.out.println();
        System.out.println("Press " + EXIT + " to exit the program.");
        System.out.println("Press " + GET_MAX + " to get the max number.");
        System.out.println("Press " + GET_MIN + " to get the min number.");
        System.out.println("Press " + GET_N_MAX + " to get the top n max numbers.");
        System.out.println("Press " + GET_N_MIN + " to get the top n min numbers.");
        System.out.println("Press " + GET_MEDIAN + " to get the median.");
        System.out.println("Press " + GET_MEAN + " to get the mean.");
        System.out.println("Press " + GET_RANGE + " to get the range.");
        System.out.println("Press " + SET_FILE_NAME + " to set the file.");
    }

    public static void main(String[] args) throws FileNotFoundException {
        UI.getInstance().setFileName();
        UI.getInstance().menu();
    }
}
