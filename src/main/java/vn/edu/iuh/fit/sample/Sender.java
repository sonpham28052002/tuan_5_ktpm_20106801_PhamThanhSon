package vn.edu.iuh.fit.sample;

import org.apache.activemq.ActiveMQConnectionFactory;
import vn.edu.iuh.fit.model.MyContants;
import vn.edu.iuh.fit.model.Student;
import vn.edu.iuh.fit.model.User;

import javax.jms.*;
import java.util.ArrayList;

public class Sender {
    public static void main(String[] args) throws JMSException {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory();

        ArrayList<String> trustPackage = new ArrayList<>();
        trustPackage.add("vn.edu.iuh.fit.model");
        factory.setTrustedPackages(trustPackage);

        try (Connection connection = factory.createConnection(User.userName, User.password)) {
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            connection.start();
            Destination destination = session.createQueue(MyContants.QUEUSE_PHAMTHANHSON);
            MessageProducer messageProducer = session.createProducer(destination);
            // send text message
            TextMessage text = session.createTextMessage("iam son");
            messageProducer.send(text);
            //send object message
            Student student = new Student(20106801L, "pham thanh son", 22, "566/137/60 nguyen thai son");
            ObjectMessage studentMessage = session.createObjectMessage(student);
            messageProducer.send(studentMessage);
        } catch (Exception exception) {
            exception.printStackTrace();
        }

    }
}
