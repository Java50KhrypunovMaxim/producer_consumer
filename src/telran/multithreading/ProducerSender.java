package telran.multithreading;

import java.util.concurrent.BlockingQueue;
import java.util.stream.IntStream;

public class ProducerSender extends Thread{
BlockingQueue<String> oddMessageBox;
BlockingQueue<String> evenMessageBox;
private int nMassages;

public ProducerSender(BlockingQueue<String> oddMessageBox, BlockingQueue<String> evenMessageBox, int nMassages) {
	
	this.oddMessageBox = oddMessageBox;
	this.evenMessageBox = evenMessageBox;
	this.nMassages = nMassages;
}


public void run() {
	IntStream.rangeClosed(1,  nMassages).forEach(i -> {
        String message = "message" + i;
        try {
            if (i % 2 == 0) {
                evenMessageBox.put(message);
            } else {
                oddMessageBox.put(message);
            }
        } catch (InterruptedException e) {
           
        }
    });
}
}
