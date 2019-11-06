package io.github.ajits01.activemq.ptp;

import static io.github.ajits01.activemq.ptp.JMSMessageReceiver.receiveAndPrintMessage;
import static io.github.ajits01.activemq.ptp.JMSMessageSender.sendMessage;

public class VanillaJMSApp {

  private static final String QUEUE = "JMSAPP.QUEUE";

  public static void main(String[] args) throws InterruptedException {

    sendMessage("Test ActiveMQ Message - 1", QUEUE);
    Thread.sleep(1000);
    sendMessage("Test ActiveMQ Message - 2", QUEUE);
    Thread.sleep(1000);
    sendMessage("Test ActiveMQ Message - 3", QUEUE);
    Thread.sleep(1000);

    receiveAndPrintMessage(QUEUE);
    Thread.sleep(2000);
    receiveAndPrintMessage(QUEUE);
    Thread.sleep(2000);
    receiveAndPrintMessage(QUEUE);
  }
}
