import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

public class TRS_Input_Producer {

	private Session session = null;

	private MessageProducer producer = null;

	public TRS_Input_Producer() {
		// Create a ConnectionFactory
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
				ActiveMQConnection.DEFAULT_BROKER_URL);

		// Create a Connection
		Connection connection;
		try {
			connection = connectionFactory.createConnection();
			connection.start();

			// Create a Session
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

			// Create the destination (Topic or Queue)
			Destination destination = session.createQueue("TRS_Input_Queue");

			// Create a MessageProducer from the Session to the Topic or Queue
			producer = session.createProducer(destination);
			producer.setDeliveryMode(DeliveryMode.PERSISTENT);
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void send(String message) throws JMSException {
		// Create a messages

		TextMessage txtMessage = session.createTextMessage(message);

		// Tell the producer to send the message
		System.out.println("Sent message: " + txtMessage.hashCode() + " : " + Thread.currentThread().getName());
		producer.send(txtMessage);

	}

	public static void main(String[] args) {
		TRS_Input_Producer producer = new TRS_Input_Producer();
		try {
			producer.send("This is sample 2 message to input queue");
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
