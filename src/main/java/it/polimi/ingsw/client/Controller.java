package it.polimi.ingsw.client;

import it.polimi.ingsw.bean.action.Action;
import it.polimi.ingsw.bean.options.Options;
import it.polimi.ingsw.client.view.View;

public class Controller {

    private final View clientView;
    Client client;
    private Options currentOption;
    private Action currentAction;

    public Controller(View clientView) {
        this.clientView = clientView;
    }

    public void handleOptions(Options inputObject) {
        this.currentOption = inputObject;
        currentOption.execute(clientView);
    }


    public boolean isValidAction() {
        if (currentAction == null)
            throw new IllegalStateException();
        return currentOption.isValid(currentAction);
    }

    public Action getCurrentAction() {
        return currentAction;
    }

    public void setCurrentAction(Action currentAction) {
        synchronized (client.sendActionLock) {
            this.currentAction = currentAction;
            client.sendActionLock.notifyAll();
        }
    }

    public void reportInvalidAction() {
        clientView.showMessage("Invalid user input, try again.\n");
        currentOption.execute(clientView);
    }
}
