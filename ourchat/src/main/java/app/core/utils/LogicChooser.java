package app.core.utils;

import app.core.consumer.ConsumerLogic;
import app.core.consumer.implementations.ConsumerLogicImpl1;
import app.core.consumer.implementations.ConsumerLogicImpl2;

public class LogicChooser {

    private static final int CONSUMER_LOGIC = Config.getInt(ConfigKeys.CONSUMER_LOGIC);
    private static final int PRODUCER_LOGIC = Config.getInt(ConfigKeys.PRODUCER_LOGIC);

    public static ConsumerLogic chooseConsumerLogic() {
        switch (CONSUMER_LOGIC) {
            case 1:
                return new ConsumerLogicImpl1();
            case 2:
                return new ConsumerLogicImpl2();
            default:
                return null;
        }
    }
}
