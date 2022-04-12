package mackansw.cows.service;

import mackansw.cows.gui.gridImage.GridImage;
import mackansw.cows.gui.gridImage.GridImageTypes;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * The applications service.
 * Contains the logic for the applications calculations and random number generation.
 */
public class CowsService {

    /**
     * Generates a random number between 1 and a upper bound
     *
     * @param bound the highest number that can be generated
     * @return an integer between 1 and the bound
     */
    public int generateRandomNumber(int bound) {
        return new Random().nextInt(bound) + 1;
    }

    /**
     * Calculates the number of cows that are in the farthest diagonal corners of the grid
     *
     * @param gridRows the number of rows in the grid
     * @param gridImages an arraylist of the GridImages in the grid
     * @return an integer of the number of cows in the corners
     */
    public int countCowsInCorners(int gridRows, List<GridImage> gridImages) {
        int cornerCows = 0;
        int gridSize = gridRows * gridRows;

        for (int i = 0; i < gridSize; i++) {
            if(isGridCorner(i, gridRows) && isElementCow(i, gridImages)) {
                cornerCows++;
            }
        }

        return cornerCows;
    }

    /**
     * Checks if a specific index in a grid is a far diagonal corner
     *
     * @param index the index to check
     * @param gridRows the number of rows in the grid
     * @return true if the index equals the index of a corner
     */
    private boolean isGridCorner(int index, int gridRows) {
        int topLeftCorner = 0;
        int topRightCorner = gridRows - 1;
        int bottomLeftCorner = gridRows * gridRows - gridRows;
        int bottomRightCorner = gridRows * gridRows - 1;
        return gridRows > 1 && index == topLeftCorner || gridRows > 1 && index == topRightCorner || gridRows > 1 && index == bottomLeftCorner || gridRows > 1 && index == bottomRightCorner;
    }

    /**
     * Checks if the index of a GridImage element in an arrayList is a cow
     *
     * @param index the index in the GridImageList to check
     * @param gridImages an arraylist of the GridImages in the grid
     * @return true if the GridImageType of the element is equal to GridImageTypes.COW
     */
    private boolean isElementCow (int index, List<GridImage> gridImages) {
        return gridImages.get(index).getGridImageType() == GridImageTypes.COW;
    }

    /**
     * Calculates the number of cows in the gird that have a neighbor.
     *
     * @param gridRows the number of rows in the grid
     * @param gridImages an arraylist of the GridImages in the grid
     * @return an integer of the number of cows that have neighbors
     */
    public int countNeighborElements (int gridRows, List<GridImage> gridImages) {
        int neighborCows = 0;

        for (int i = 0; i < gridImages.size(); i++) {
            if (isElementCow(i, gridImages) && hasCowNeighbor(i, gridRows, gridImages)) {
                neighborCows++;
            }
        }

        return neighborCows;
    }

    /**
     * Checks if the index of a GridImage element in an arrayList has a cow neighbor
     * Only elements to the right/left or directly above/below the index with the GridImageType.COW
     * qualifies as a neighbor. Diagonal neighbors are excluded.
     *
     * @param index the index in the GridImageList to check
     * @param gridRows the number of rows of the grid
     * @param gridImages an arrayList of GridImages from the grid
     * @return true if the index element has a cow neighbor to its left/right or top/bottom
     */
    private boolean hasCowNeighbor(int index, int gridRows, List<GridImage> gridImages) {
        boolean foundNeighbor = false;

        int topNeighbor = index - gridRows;
        int bottomNeighbor = index + gridRows;
        int leftNeighbor = index - 1;
        int rightNeighbor = index + 1;
        List<Integer> neighbors = new ArrayList<>(List.of(topNeighbor, bottomNeighbor, leftNeighbor, rightNeighbor));

        //Validates that no neighbor is outside of list size bounds
        neighbors.removeIf(neighbor -> neighbor < 0 || neighbor >= gridImages.size());

        GridEdgeIndexRecord edgeIndexes = calculateEdgeIndexes(gridRows);

        if(edgeIndexes.getLeftIndexes().contains(index)) {
            if(edgeIndexes.rightIndexes.contains(leftNeighbor)) {
                neighbors.removeIf(neighbor -> neighbor == leftNeighbor);
            }
        }
        if(edgeIndexes.getRightIndexes().contains(index)) {
            if(edgeIndexes.getLeftIndexes().contains(rightNeighbor)) {
                neighbors.removeIf(neighbor -> neighbor == rightNeighbor);
            }
        }

        for(Integer neighbor : neighbors) {
            if (isElementCow(neighbor, gridImages)) {
                foundNeighbor = true;
                break;
            }
        }

        return foundNeighbor;
    }

    /**
     * Calculates all indexes of the furthest left/right elements of a grid
     *
     * @param gridRows the number of rows of the grid
     * @return An EdgeElementsRecord containing two arraylists with the indexes for all elements on the right/left edges
     */
    private GridEdgeIndexRecord calculateEdgeIndexes(int gridRows) {

        //Starts at -1 to match index values in an ArrayList
        int element = -1;

        List<Integer> rightIndexes = new ArrayList<>();
        List<Integer> leftIndexes = new ArrayList<>();

        for(int i = 0; i < gridRows; i++) {
            element = element + gridRows;
            rightIndexes.add(element);
        }

        int leftIndexesAdjuster = gridRows - 1;

        //Adjusts the elements to the left side of the grid before calculation of left indexes
        element = element - leftIndexesAdjuster;

        for(int i = 0; i < gridRows; i++) {
            leftIndexes.add(element);
            element = element - gridRows;
        }
        return new GridEdgeIndexRecord(rightIndexes, leftIndexes);
    }

    /**
     * Record-class for containing two lists of the furthest left/right indexes of a grid
     * TODO should be replaced with a actual record when updating to java 17
     */
    private static class GridEdgeIndexRecord {

        private final List<Integer> rightIndexes;
        private final List<Integer> leftIndexes;

        public GridEdgeIndexRecord(List<Integer> rightIndexes, List<Integer> leftIndexes) {
            this.rightIndexes = rightIndexes;
            this.leftIndexes = leftIndexes;
        }

        public List<Integer> getRightIndexes() {
            return rightIndexes;
        }

        public List<Integer> getLeftIndexes() {
            return leftIndexes;
        }
    }
}