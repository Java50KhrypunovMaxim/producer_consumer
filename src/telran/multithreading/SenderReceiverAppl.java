package telran.multithreading;

import java.util.Arrays;
import java.util.concurrent.*;
import java.util.stream.IntStream;

import javax.sound.midi.Receiver;

public class SenderReceiverAppl {

	private static final int N_MESSAGES = 2000;
	private static final int N_RECEIVERS = 10;

	public static void main(String[] args) throws InterruptedException {
		BlockingQueue<String> evenMessageBox = new LinkedBlockingQueue<String>();
		BlockingQueue<String> oddMessageBox = new LinkedBlockingQueue<String>();
		ProducerSender sender = startSender(evenMessageBox, oddMessageBox, N_MESSAGES);
		ConsumerReceiver[] receivers = startReceivers(evenMessageBox, oddMessageBox, N_RECEIVERS);
		sender.join();
		stopReceivers(receivers);
		displayResult();
	}

	private static void displayResult() {
		System.out.printf("counter of processed messsages is %d\n", ConsumerReceiver.getMessagesCounter());

	}

	private static void stopReceivers(ConsumerReceiver[] receivers) throws InterruptedException {
		for (ConsumerReceiver receiver : receivers) {
			receiver.interrupt();
			receiver.join();
		}

	}

	private static ConsumerReceiver[] startReceivers(BlockingQueue<String> evenMessageBox,
		BlockingQueue<String> oddMessageBox, int nReceivers) {
		ConsumerReceiver[] receivers = new ConsumerReceiver[nReceivers];
		for (int i = 0; i < nReceivers; i++) {
			receivers[i] = new ConsumerReceiver();
			long threadId = receivers[i].getId();
			if (threadId % 2 == 0) {
				receivers[i].setMessageBox(evenMessageBox);
			} else {
				receivers[i].setMessageBox(oddMessageBox);
			}
			receivers[i].start();
		}
		return receivers;
	}

	private static ProducerSender startSender(BlockingQueue<String> evenMessageBox, BlockingQueue<String> oddMessageBox,
			int nMessages) {
		ProducerSender sender = new ProducerSender(evenMessageBox, oddMessageBox, nMessages);
		sender.start();
		return sender;
	}

}