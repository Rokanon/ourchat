package app.core.consumer;

import app.core.utils.LogicChooser;

public class Consumer implements Runnable {

    @Override
    public void run() {
        LogicChooser.chooseConsumerLogic().execute();
    }

}
