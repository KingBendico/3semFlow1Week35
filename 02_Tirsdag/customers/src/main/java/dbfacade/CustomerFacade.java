/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbfacade;

import entity.*;
import java.util.List;
import java.util.ListIterator;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

/**
 *
 * @author Joakim
 */
public class CustomerFacade {

    private static EntityManagerFactory emf;
    private static CustomerFacade instance;

    public CustomerFacade() {
    }

    public static void main(String[] args) {
        emf = Persistence.createEntityManagerFactory("pu");
        CustomerFacade facade = CustomerFacade.getCustomerFacade(emf);
        
        //Facade method calls
        
        //Add customer
        Customer cust1 = facade.addCustomer("Ulrik", "Jensen");
        Customer cust2 = facade.addCustomer("Ole", "Henriksen");
        Customer cust3 = facade.addCustomer("Uffe", "Holm");
        
        //Find customer by ID
        System.out.println("Customer 1: " + facade.findCustomerByID(cust1.getId()).getFullName());
        System.out.println("Customer 2: " + facade.findCustomerByID(cust2.getId()).getFullName());

        //Find by lastName
        System.out.println("Customer 3: " + facade.findCustomerByLastName(cust3.getLastName()));

        //Get number of customers
        System.out.println("Number of customers: " + facade.getAllCustomers().size());

        //Get all customers
        List<Customer> customers = facade.getAllCustomers();
        ListIterator<Customer> listIterator = customers.listIterator();
        System.out.println("All customers:");
        while (listIterator.hasNext()) {
            Customer customer = listIterator.next();
            System.out.println(customer.getId() + ": " + customer.getFullName());
        }
        
        
    }

    public static CustomerFacade getCustomerFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new CustomerFacade();
        }
        return instance;
    }

    //Add customer
    public Customer addCustomer(String firstName, String lastName) {
        Customer customer = new Customer(firstName, lastName);
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(customer);
            em.getTransaction().commit();
            return customer;

        } finally {
            em.close();
        }
    }

    //Find customer by ID
    public Customer findCustomerByID(int id) {
        EntityManager em = emf.createEntityManager();
        try {
            Customer customer = em.find(Customer.class, id);
            return customer;
        } finally {
            em.close();
        }
    }

    //Find customer by lastname
    public List<Customer> findCustomerByLastName(String name) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Customer> query = em.createQuery("SELECT a FROM a where a.lastname=:lastname", Customer.class);
            query.setParameter("lastname", name);
            List<Customer> customer = query.getResultList();
            return customer;
        } finally {
            em.close();
        }
    }

//Get all customers
    public List<Customer> getAllCustomers() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Customer> query
                    = em.createQuery("SELECT customer FROM Customer customer", Customer.class
                    );
            return query.getResultList();
        } finally {
            em.close();
        }
    }
}
