package io.github.ajits01.activemq.ptp;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * Vanilla JMS Message Receiver Implementation with ActiveMQ
 *
 * @author AJIT
 */
public class JMSMessageReceiver {

  private static final String BROKER_URL = ActiveMQConnection.DEFAULT_BROKER_URL;

  /**
   * Retrieve message from provided {@code QUEUE} and print to output stream
   *
   * <p>Broker URL used: ActiveMQConnection.DEFAULT_BROKER_URL
   *
   * @param QUEUE Queue name
   */
  public static void receiveAndPrintMessage(final String QUEUE) {

    try {
      /** Create ConnectionFactory */
      ConnectionFactory cf = new ActiveMQConnectionFactory(BROKER_URL);

      /** Create Connection */
      Connection conn = cf.createConnection();
      conn.start();

      /** Create Session */
      Session session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);

      /** Create Destination (Queue) */
      Destination destination = session.createQueue(QUEUE);

      /** Create MessageConsumer */
      MessageConsumer consumer = session.createConsumer(destination);

      /** Look for a Message */
      Message message = consumer.receive();
      if (message instanceof TextMessage) {
        TextMessage txtMsg = (TextMessage) message;
        System.out.printf("\nMessage Received: %s", txtMsg.getText());
      } else {
        System.out.printf("\nMessage Received: %s", message);
      }

      /** Clean up */
      consumer.close();
      session.close();
      conn.close();

    } catch (JMSException ex) {
      System.out.println(ex.getMessage());
    }
  }
}
