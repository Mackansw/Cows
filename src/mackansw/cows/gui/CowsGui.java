package mackansw.cows.gui;

import mackansw.cows.gui.gridImage.GridImage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * The applications Gui.
 * Contains the applications viewable components and constants for its WIDTH, HEIGHT, etc
 */
public class CowsGui {

    private JFrame window;
    private JPanel basePanel;
    private JPanel gridPanel;
    private JPanel actionPanel;
    private JButton generateGridButton;
    private JButton aboutButton;
    private JLabel cornerCounterLabel;
    private JLabel neighborCounterLabel;

    private static final Color BACKGROUND_COLOR = Color.darkGray;
    private static final Color BUTTON_BACKGROUND_COLOR = Color.gray;
    private static final Color FOREGROUND_COLOR = Color.white;
    private static final int WIDTH = 700;
    private static final int HEIGHT = 600;
    private static final int ACTIONPANEL_HGAP = 40;

    public CowsGui() {
        window = new JFrame("Cows!");
        window.setSize(WIDTH, HEIGHT);
        window.setLocationRelativeTo(null);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        basePanel = new JPanel(new BorderLayout());

        gridPanel = new JPanel();
        basePanel.add(gridPanel, BorderLayout.CENTER);

        FlowLayout actionPanelLayout = new FlowLayout();
        actionPanelLayout.setHgap(ACTIONPANEL_HGAP);

        actionPanel = new JPanel(actionPanelLayout);
        basePanel.add(actionPanel, BorderLayout.SOUTH);
        actionPanel.setBackground(BACKGROUND_COLOR.brighter());
        actionPanel.setPreferredSize(new Dimension(actionPanel.getWidth(), 40));

        generateGridButton = new JButton("Generate grid");
        actionPanel.add(generateGridButton);
        generateGridButton.setBackground(BUTTON_BACKGROUND_COLOR);
        generateGridButton.setForeground(FOREGROUND_COLOR);

        cornerCounterLabel = new JLabel("Cows in corners: ");
        actionPanel.add(cornerCounterLabel);
        cornerCounterLabel.setForeground(FOREGROUND_COLOR);

        neighborCounterLabel = new JLabel("Cows with neighbors: ");
        actionPanel.add(neighborCounterLabel);
        neighborCounterLabel.setForeground(FOREGROUND_COLOR);

        aboutButton = new JButton("About");
        actionPanel.add(aboutButton);
        aboutButton.setBackground(BUTTON_BACKGROUND_COLOR);
        aboutButton.setForeground(FOREGROUND_COLOR);

        window.add(basePanel);
        window.setVisible(true);
    }

    /**
     * Creates and shows a themed dialog message
     * @param message the message to display
     */
    public void showMessageDialog(String message) {
        UIManager.put("Panel.background", BACKGROUND_COLOR);
        UIManager.put("Button.background", BACKGROUND_COLOR.brighter().brighter());
        UIManager.put("Button.foreground", FOREGROUND_COLOR);
        UIManager.put("OptionPane.messageForeground", FOREGROUND_COLOR);
        UIManager.put("OptionPane.background", BACKGROUND_COLOR);
        JOptionPane.showMessageDialog(this.window, message);
    }

    /**
     * Adds a list of GridImages to the gridPanel
     * @param gridImages an arraylist of GridImages to be added
     */
    public void addGridImagesToGrid(List<GridImage> gridImages) {
        gridImages.forEach(e -> {
            gridPanel.add(e.getGridImageLabel());
        });
        gridPanel.revalidate();
        gridPanel.repaint();
    }

    public void setGrid (int rows, int columns) {
        this.gridPanel.setLayout(new GridLayout(rows, columns));
    }

    public void addGenerateGridButtonActionListener (ActionListener listener) {
        this.generateGridButton.addActionListener(listener);
    }

    public void addAboutButtonActionListener (ActionListener listener) {
        this.aboutButton.addActionListener(listener);
    }

    public void setCornerCounterText (String text) {
        this.cornerCounterLabel.setText(text);
    }

    public void setNeighborCounterText (String text) {
        this.neighborCounterLabel.setText(text);
    }

    public void clearGrid() {
        this.gridPanel.removeAll();
    }

    /**
     * Returns a description of the application and how to use it.
     * */
    public String getApplicationDescription() {
        return "This program is a recreation of a test from futureskill.com.\n \n" +
                "The functions of this program are based on the original test exercises\n" +
                "as close as I can remember them, with some added humor ;)\n \n" +
                "Pressing 'Generate Grid' will generate a new random grid with a value between 0-8.\n" +
                "The grid will always be generated as a square. \n \n" +
                "'Cows in corners' calculates the sum of how many cows are hiding in the corners.\n" +
                "Only cows that are in the furthest diagonal corners of the grid are calculated. \n \n" +
                "'Cows with neighbors' calculates how many cows that have neighbors.\n" +
                "Only cows with a cow next to them or directly above/below them are calculated.\n" +
                "Diagonal neighbors are not included in this calculation.\n";
    }
}