package mackansw.cows.main;

import mackansw.cows.controller.CowsController;
import mackansw.cows.gui.CowsGui;
import mackansw.cows.service.CowsService;

public class Main {

    public static void main(String[] args) {
        CowsService service = new CowsService();
        CowsGui gui = new CowsGui();
        new CowsController(gui, service);
    }
}