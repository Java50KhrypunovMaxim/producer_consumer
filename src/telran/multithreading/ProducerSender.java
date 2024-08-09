package telran.multithreading;

import java.util.concurrent.BlockingQueue;
import java.util.stream.IntStream;

public class ProducerSender extends Thread{
BlockingQueue<String> messageBox;
private int nMassages;

public ProducerSender(BlockingQueue<String> messageBox, int nMassages) {
	
	this.messageBox = messageBox;
	this.nMassages = nMassages;
}
public void run() {
	IntStream.rangeClosed(1, nMassages).mapToObj(i -> "message" + i)
	.forEach(m->
	{
		try {
			messageBox.put(m);
		} catch (InterruptedException e) {
			
		}
	});

}
}
