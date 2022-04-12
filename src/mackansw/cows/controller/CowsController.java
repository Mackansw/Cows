package mackansw.cows.controller;

import mackansw.cows.gui.CowsGui;
import mackansw.cows.gui.gridImage.GridImage;
import mackansw.cows.gui.gridImage.GridImageTypes;
import mackansw.cows.service.CowsService;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The applications controller.
 * Passes communication between the applications GUI and service.
 */
public class CowsController {

    private final CowsGui gui;
    private final CowsService service;

    public CowsController (CowsGui gui, CowsService service) {
        this.gui = gui;
        this.service = service;

        gui.addGenerateGridButtonActionListener(new GenerateGridButtonListener());
        gui.addAboutButtonActionListener(new AboutButtonListener());

        //Generates initial grid that the application starts with
        generateRandomGrid();
    }

    /**
     * Clears grid and generates a new square grid with a random value between 0-8,
     * Then performs corner and neighbors calculations.
     * Generates new Grid on a separate thread to prevent Gui thread freeze
     */
    private void generateRandomGrid() {
        int randomGrid = service.generateRandomNumber(7);
        List<GridImage> gridImages = populateGrid(randomGrid, randomGrid);

        new Thread (() -> {
            gui.clearGrid();
            gui.setGrid(randomGrid, randomGrid);
            gui.addGridImagesToGrid(gridImages);
        }).start();

        gui.setCornerCounterText("Cows in corners: " + service.countCowsInCorners(randomGrid, gridImages));
        gui.setNeighborCounterText("Number of cows with neighbors: " + service.countNeighborElements(randomGrid, gridImages));
    }

    /**
     * Populates a grid with a randomly generated number of cows and grass as GridImages
     * and returns them as an arrayList.
     *
     * @param rows the number of rows of the grid
     * @param columns the number of columns in the grid
     * @return an arrayList of GridImages
     */
    private List<GridImage> populateGrid (int rows, int columns) {
        List<GridImage> gridImages = new ArrayList<>();
        int gridSize = rows * columns;
        int cows = service.generateRandomNumber(gridSize);
        int tiles = gridSize - cows;

        gridImages.addAll(generateGridImages(GridImageTypes.COW, cows));
        gridImages.addAll(generateGridImages(GridImageTypes.GRASS, tiles));

        //Spreads out the cows and grass over the grid
        Collections.shuffle(gridImages);

        return gridImages;
    }

    /**
     * Generates a number of gridImages and returns them as an arrayList.
     *
     * @param gridImageType the type of gridImage to generate (COW or GRASS)
     * @param amount the number of gridImages to generate
     * @return an ArrayList of GridImages
     */
    private List<GridImage> generateGridImages(GridImageTypes gridImageType, int amount) {
        List<GridImage> images = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            images.add(new GridImage(gridImageType));
        }
        return images;
    }

    /**
     * The action of the generate grid button
     */
    private class GenerateGridButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            generateRandomGrid();
        }
    }

    /**
     * The action of the generate about button
     */
    private class AboutButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            gui.showMessageDialog(gui.getApplicationDescription());
        }
    }
}