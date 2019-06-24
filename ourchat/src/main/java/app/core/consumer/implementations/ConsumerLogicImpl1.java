package app.core.consumer.implementations;
import rs.netset.consumer.ConsumerLogic;

public class ConsumerLogicImpl1 extends ConsumerLogic {

	@Override
	public void execute() {
		System.out.println("Hello from consumer logic #1");
	}

}
