package io.github.ajits01.activemq.ptp;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * Vanilla JMS Message Sender Implementation with ActiveMQ
 *
 * @author AJIT
 */
public class JMSMessageSender {

  private static final String BROKER_URL = ActiveMQConnection.DEFAULT_BROKER_URL;

  /**
   * Send {@code message} to {@code QUEUE}
   *
   * <p>Broker URL used: ActiveMQConnection.DEFAULT_BROKER_URL
   *
   * @param message Message to send
   * @param QUEUE Queue name
   */
  public static void sendMessage(final String message, final String QUEUE) {

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

      /** Create MessageProducer */
      MessageProducer producer = session.createProducer(destination);

      /** Create Message */
      TextMessage textMessage = session.createTextMessage(message);

      /** Optionally, set a String property value with the specified name into the message. */
      textMessage.setStringProperty("sender-application", "Mailbox Sender Client");

      /** Send Message */
      producer.send(textMessage);
      System.out.printf("\nMessage sent successfully: %s", textMessage.getText());

      /** Clean up */
      producer.close();
      session.close();
      conn.close();

    } catch (JMSException ex) {
      System.out.println(ex.getMessage());
    }
  }
}
