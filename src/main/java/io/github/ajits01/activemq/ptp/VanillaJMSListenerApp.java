package io.github.ajits01.activemq.ptp;

import java.net.URI;
import java.net.URISyntaxException;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.broker.TransportConnector;

/**
 * Class demonstrating JMSReceiver by implementing the {@code javax.jms.MessageListener} interface
 *
 * @author AJIT
 */
public class VanillaJMSListenerApp {

  private static final String BROKER_URL = "tcp://localhost:61617";
  private static final String QUEUE = "JMSAPP.QUEUE";

  public static void main(String[] args) {

    try {

      /** Create an embedded broker */
      BrokerService broker = new BrokerService();
      TransportConnector connector = new TransportConnector();
      connector.setUri(new URI(BROKER_URL));
      broker.addConnector(connector);
      broker.start();

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

      /** Create MessageConsumer and register MessageListener object */
      MessageConsumer consumer = session.createConsumer(destination);
      consumer.setMessageListener(new JMSAMQMessageListener());
      Thread.sleep(2000);

      for (int i = 0; i < 5; i++) {
        /** Create Message */
        String message = String.format("Test Message for MessageListener - %d", (i + 1));
        TextMessage textMessage = session.createTextMessage(message);

        /** Send Message */
        System.out.printf("\nMessage sent successfully: %s", textMessage.getText());
        producer.send(textMessage);
        Thread.sleep(2000);
      }

      /** Clean up */
      producer.close();
      consumer.close();
      session.close();
      conn.close();
      broker.stop();
    } catch (URISyntaxException e) {
      System.out.println(e.getMessage());
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }
}
