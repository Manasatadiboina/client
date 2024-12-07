package com.klef.jfsd.exam;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.List;

public class ClientDemo {

    public static void main(String[] args) {
        // Step 1: Set up SessionFactory
        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Client.class)
                .buildSessionFactory();

        // Step 2: Get Session
        Session session = factory.getCurrentSession();

        try {
            // Insert a new client
            insertClient(session, "John Doe", "Male", 30, "New York", "john@example.com", "1234567890");

            // Retrieve all clients
            List<Client> clients = getClients(session);

            // Print all clients
            printClients(clients);

        } finally {
            factory.close();
        }
    }

    // Method to insert a client record
    private static void insertClient(Session session, String name, String gender, int age, String location, String email, String mobile) {
        Client client = new Client();
        client.setName(name);
        client.setGender(gender);
        client.setAge(age);
        client.setLocation(location);
        client.setEmail(email);
        client.setMobile(mobile);

        session.beginTransaction();
        session.save(client); // Save the client object
        session.getTransaction().commit();
    }

    // Method to get all clients using HQL
    private static List<Client> getClients(Session session) {
        session.beginTransaction();
        
        Query<Client> query = session.createQuery("from Client", Client.class); // HQL Query
        List<Client> clients = query.getResultList();
        
        session.getTransaction().commit();
        return clients;
    }

    // Method to print all client records
    private static void printClients(List<Client> clients) {
        for (Client client : clients) {
            System.out.println(client.getId() + " | " + client.getName() + " | " + client.getGender() + " | " +
                    client.getAge() + " | " + client.getLocation() + " | " + client.getEmail() + " | " + client.getMobile());
        }
    }
}
