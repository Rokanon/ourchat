package app.core.logic;

import app.core.utils.LogicChooser;

public class Consumer implements Runnable {

    @Override
    public void run() {
        LogicChooser.chooseConsumerLogic().execute();
    }

}
