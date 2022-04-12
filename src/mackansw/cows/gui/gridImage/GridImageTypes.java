package mackansw.cows.gui.gridImage;

/**
 * Defines what image a GridImage should display
 */
public enum GridImageTypes {

    COW("/resources/cow.png"),
    GRASS("/resources/grass.jpg");

    /**
     * Constructor with resourcePath parameter
     * @param resourcePath the filepath to the image
     */
    GridImageTypes(String resourcePath) {
        this.resourcePath = resourcePath;
    }

    private final String resourcePath;

    public String getResourcePath() {
        return resourcePath;
    }
}