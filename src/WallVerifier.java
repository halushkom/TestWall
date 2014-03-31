/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package testwall;

/**
 *
 * @author kava
 */
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * A simple utility class, which has several static methods
 *
 *
 */
public class WallVerifier {

    // Number of "1" in wall matrix, that isn't covers of bricks
    private static int wallSize = 0;
    // List of parts of wall regard of wall configuration
    public static List<Integer> partsOfWall = null;
    // Utility arrays for brute-force implementation
    private static Integer[] bricksArr = null;
    private static Integer[] wallArr = null;

    /**
     * Returns true, if a specified wall can be built by specified list of
     * bricks
     *
     * @param matr the wall matrix, that provided by data input
     * @param bricks the list of bricks, that provided by data input
     * @return true, if a specified wall can be built by specified list of
     * bricks
     */
    public static boolean canBuiltWall(int[][] matr, List<Integer> bricks) {

        partsOfWall = new ArrayList<Integer>();
        partsOfWall = matrIntoList(matr);
        bricksArr = TestWall.bricks.toArray(new Integer[TestWall.bricks.size()]);
        wallArr = partsOfWall.toArray(new Integer[partsOfWall.size()]);
        if (!isEnoughBricks(partsOfWall, bricks)) {
            return false;
        } else {
            placeMatchBricks();
            if (partsOfWall.isEmpty()) {
                return true;
            } else {
                if (placeByOneBrick(bricksArr, wallArr)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isEnoughBricks(List<Integer> listParts, List<Integer> bricks) {

        int bricksBody = 0;
        int wallBody = 0;
        for (int i = 0; i < bricks.size(); i++) {
            bricksBody += bricks.get(i);
        }
        for (int i = 0; i < listParts.size(); i++) {
            wallBody += listParts.get(i);
        }
        wallSize = wallBody;
        if (bricksBody >= wallBody) {
            return true;
        } else {
            return false;
        }
    }

    // A method that places bricks into equal wall parts in the wall
    public static void placeMatchBricks() {

        for (Iterator<Integer> it = partsOfWall.iterator(); it.hasNext();) {
            Integer e = it.next();
            if (TestWall.bricks.contains(e)) {
                it.remove();
                TestWall.bricks.remove(e);
                wallSize -= e;
            }
        }
    }

    // Convertting of two-dimensions array of wall matrix 
    // into list of solid parts of wall
    public static List<Integer> matrIntoList(int[][] matr) {
        int currentSolid = 0;
        for (int i = 0; i < matr.length; i++) {
            for (int j = 0; j < matr[i].length; j++) {
                if (matr[i][j] == 1) {
                    currentSolid++;
                } else {
                    if (currentSolid > 0) {
                        partsOfWall.add(currentSolid);
                        currentSolid = 0;
                    }
                }
            }
            if (currentSolid > 0) {
                partsOfWall.add(currentSolid);
                currentSolid = 0;
            }
        }
        return partsOfWall;
    }

    // Method tries to build the wall by brute-force search and recursion
    public static boolean placeByOneBrick(Integer[] bricks, Integer[] wallParts) {

        for (int i = bricks.length - 1; i >= 0; i--) {

            if (bricks[i] > 0) {

                for (int j = 0; j < wallParts.length; j++) {

                    if (bricks[i] <= wallParts[j]) {
                        int tempBrick = bricks[i];
                        wallParts[j] -= tempBrick;
                        bricks[i] = 0;
                        wallSize -= tempBrick;
                        if (wallSize == 0) {
                            return true;
                        } else {
                            if (placeByOneBrick(bricksArr, wallArr)) {
                                return true;
                            }

                            wallParts[j] += tempBrick;
                            bricks[i] = tempBrick;
                            wallSize += tempBrick;
                        }
                    }
                }
            }
        }
        return false;
    }
}
