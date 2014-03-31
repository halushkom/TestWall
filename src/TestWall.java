/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package testwall;

/**
 *
 * @author kava
 */
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Executes checking whether wall can built or not. Collects input data
 * from file, or from command line depending what kind of input was chosen.
 * @author Kazymov N. e-mail: aviavto@ukr.net
 */
public class TestWall {
    
    // Rows and columns of wall matrix, number of types of bricks, 
    // two-dimensions array that holds "1" and "0" elements of wall matrix.
    // Integer lists that hold a bricks set and set of solid parts of wall.
    private static int rows = 0;
    private static int columns = 0;
    private static int brickSorts = 0;
    private static int matr[][] = null;
    public static List<Integer> bricks = null;
    public static List<Integer> partsOfWall = null;
    private static final int maxSizeofBricks = 8;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Use diferent arguments in main method to use diferent source
        // input data. "-f path" - for using text file as source input data, 
        // where path is path of source file, or "-c" for using command line as 
        // input date source.
        
        if (args.length == 0) {
            System.out.println("Input help as an argument of the program");
        } else if (args[0].equals("help")) {
            System.out.println("-f file");
            System.out.println("-c input data from keyboard");
        } else if (args[0].equals("-f") && args.length == 2) {
            String file = args[1];
            FileInputStream fileIn = null;
            try {
                fileIn = new FileInputStream(file);
            } catch (FileNotFoundException ex) {
                System.err.println("File is not open");
            }
            if (inputData(fileIn)) {
                if (WallVerifier.canBuiltWall(matr, bricks)) {
                    System.out.println("Yes\n");
                } else {
                    System.out.println("No\n");
                }

            }
        } else if (args[0].equals("-c")) {
            if (inputData(System.in)) {
                if (WallVerifier.canBuiltWall(matr, bricks)) {
                    System.out.println("Yes\n");
                } else {
                    System.out.println("No\n");
                }
            }
        }
    }

    /**
     * Accepts an input stream what a data source was chosen. Assigns entering 
     * input data to static variables of class.
     * @param in
     * @return 
     */    
    private static boolean inputData(InputStream in) {
        
        // Asks for entering data if it was chosen command line as data source.
        if (!(in instanceof FileInputStream)) {
            System.out.println("Enter input data");
        }

        Scanner scanner = null;
        Scanner scanLine = null;
        Scanner scann = null;
        try {
            scanner = new Scanner(in);
            scanLine = new Scanner(scanner.nextLine());
            columns = scanLine.nextInt();
            rows = scanLine.nextInt();
            if (columns <= 0 || rows <= 0) {
                    System.err.println("Incorect input data. Please check the spec.");
                    return false;
                }
            matr = new int[rows][columns];
            for (int i = 0; i < rows; i++) {
                String line = scanner.nextLine();
                for (int j = 0; j < columns; j++) {
                    matr[i][j] = Character.getNumericValue(line.charAt(j));
                }
            }
            
            bricks = new ArrayList<Integer>();
            brickSorts = Integer.valueOf(scanner.nextLine());
            
            if (brickSorts <= 0) {
                    System.err.println("Incorect input data. Please check the spec.");
                    return false;
                }
            
            for (int i = 0; i < brickSorts; i++) {
                String line = scanner.nextLine();

                scann = new Scanner(line);
                int brickLength = scann.nextInt();
                
                if (brickLength > maxSizeofBricks || brickLength <= 0) {
                    System.err.println("Incorect input data. Please check the spec.");
                    return false;
                }
                
                int bricksCount = scann.nextInt();
                
                if (bricksCount <= 0) {
                    System.err.println("Incorect input data. Please check the spec.");
                    return false;
                }
                
                for (int j = 0; j < bricksCount; j++) {
                    bricks.add(brickLength);
                }
            }
        } catch (Throwable e) {
            System.err.println("Error: " + "\n" + e);
            return false;
        } finally {
            if (scanner != null) {
                scanner.close();
            }
            if (scanLine != null) {
                scanLine.close();
            }
            if (scann != null) {
                scann.close();
            }
        }
        return true;
    }
}
