package io.github.ajits01.activemq.ptp;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class JMSAMQMessageListener implements MessageListener {

  @Override
  public void onMessage(Message message) {
    if (message instanceof TextMessage) {
      TextMessage txtMsg = (TextMessage) message;
      try {
        System.out.printf("\nMessage Received: %s", txtMsg.getText());
      } catch (JMSException e) {
        System.out.println(e.getMessage());
      }
    } else {
      System.out.printf("\nMessage Received: %s", message);
    }
  }
}
