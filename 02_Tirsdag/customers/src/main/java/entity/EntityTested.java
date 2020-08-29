/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Lasse Emil
 */
public class EntityTested {
    
    public static void main(String[] args){
        
        Customer c1 = new Customer("John", "Silver");
        Customer c2 = new Customer("Captain", "Haddock");
        Customer c3 = new Customer("Tin", "Tin");
        Customer c4 = new Customer("Sort", "Skæg");
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");
        EntityManager em = emf.createEntityManager();
        
        em.getTransaction().begin();
        em.persist(c1);
        em.persist(c2);
        em.persist(c3);
        em.persist(c4);
        em.getTransaction().commit();
        
        em.close();
        emf.close();
        
    }
    
}
