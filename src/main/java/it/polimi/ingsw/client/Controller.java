package it.polimi.ingsw.client;

import it.polimi.ingsw.client.view.View;

public class Controller {

    private final View clientView;

    public Controller(View clientView) {
        this.clientView = clientView;
    }

/*    public void checkValidAction(Action action) {
        currentOptions.isValid(action)
    }
    public void handleOption(Options message) {
        message.execute();
        this.currentOptions = message;
    }*/
}
