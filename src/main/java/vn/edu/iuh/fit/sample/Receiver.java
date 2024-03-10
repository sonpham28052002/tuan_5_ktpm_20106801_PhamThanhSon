package vn.edu.iuh.fit.sample;

import org.apache.activemq.ActiveMQConnectionFactory;
import vn.edu.iuh.fit.model.MyContants;
import vn.edu.iuh.fit.model.Student;
import vn.edu.iuh.fit.model.User;

import javax.jms.*;
import java.util.ArrayList;


public class Receiver {
    public static void main(String[] args) throws Exception {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory();

        ArrayList<String> trustPackage = new ArrayList<>();
        trustPackage.add("vn.edu.iuh.fit.model");
        factory.setTrustedPackages(trustPackage);

        Connection connection = factory.createConnection(User.userName, User.password);
            connection.start();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            Destination destination = session.createQueue(MyContants.QUEUSE_PHAMTHANHSON);
            MessageConsumer messageConsumer = session.createConsumer(destination);
            System.out.println("Waiting");

               messageConsumer.setMessageListener(message -> {
                   try {
                       if (message instanceof TextMessage) {
                           String textMessage = ((TextMessage) message).getText();
                           System.out.println("Text Message");
                           System.out.println(textMessage);
                       } else if (message instanceof ObjectMessage) {
                           Student student = message.getBody(Student.class);
                           System.out.println("Object Message");
                           System.out.println(student);
                       }
                   } catch (Exception exception) {
                       exception.printStackTrace();
                   }
               });
    }
}
