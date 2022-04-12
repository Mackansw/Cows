package mackansw.cows.gui.gridImage;

import javax.swing.*;
import java.awt.*;

/**
 * A component containing a gridImageLabel that renders an image in the grid and scales it to a square size.
 * Also contains a GridImageType to determine what type of image the
 * component contains (Cow, Grass, etc)
 */
public class GridImage {

    private final JLabel gridImageLabel;
    private final GridImageTypes gridImageType;

    private static final int IMAGE_SCALE = 100;

    /**
     * Constructor with gridImageType parameter
     * @param gridImageType the GridImageType the gridImageLabel should render (Cow or Grass)
     */
    public GridImage(GridImageTypes gridImageType) {
        this.gridImageLabel = new JLabel (new ImageIcon (new ImageIcon (getClass()
                .getResource(gridImageType.getResourcePath()))
                .getImage()
                .getScaledInstance(IMAGE_SCALE, IMAGE_SCALE, Image.SCALE_DEFAULT)));
        this.gridImageType = gridImageType;
    }

    public JLabel getGridImageLabel() {
        return gridImageLabel;
    }

    public GridImageTypes getGridImageType() {
        return gridImageType;
    }
}