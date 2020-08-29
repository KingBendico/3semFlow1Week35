package facades;

import dto.EmployeeDTO;
import entities.Employee;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * Rename Class to a relevant name Add add relevant facade methods
 */
public class EmployeeFacade {

    private static EmployeeFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    public EmployeeFacade() {
    }

    public void createEmployees() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");
        EmployeeFacade ef = getFacade(emf);

        //Create employees
        ef.createEmployee("Master Jacob", "Bornholmerurvej", 30000);
        ef.createEmployee("Captain Haddock", "Hjørnet 4", 35000);
        ef.createEmployee("Tin Tin", "Eventyrvej 1", 37000);
        ef.createEmployee("Ole Lukøje", "Søvnland", 38000);
        ef.createEmployee("Manden med Leen", "Farvelvej", 38000);
        ef.createEmployee("Asterix", "GallerLand", 88000);
    }

    /**
     *
     * @param _emf
     * @return an instance of this facade class.
     */
    public static EmployeeFacade getFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new EmployeeFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public EmployeeDTO getEmployeeById(int id) {
        EntityManager em = emf.createEntityManager();
        try {
            Employee employee = em.find(Employee.class, id);
            return new EmployeeDTO(employee);
        } finally {
            em.close();
        }
    }

    public List<EmployeeDTO> getEmployeeByName(String name) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Employee> query = em.createQuery("SELECT e from Employee e where e.name = ?1", Employee.class);
            List<Employee> result = query.setParameter(1, name).getResultList();
            ListIterator<Employee> listIt = result.listIterator();
            List<EmployeeDTO> wrapped = new ArrayList<>();
            while (listIt.hasNext()) {
                wrapped.add(new EmployeeDTO(listIt.next()));
            }
            return wrapped;

        } finally {
            em.close();
        }
    }

    public List<EmployeeDTO> getAllEmployees() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Employee> query = em.createQuery("SELECT employee from Employee employee", Employee.class);
            List<Employee> result = query.getResultList();
            ListIterator<Employee> listIt = result.listIterator();
            List<EmployeeDTO> wrapped = new ArrayList<>();
            while (listIt.hasNext()) {
                wrapped.add(new EmployeeDTO(listIt.next()));
            }
            return wrapped;
        } finally {
            em.close();
        }
    }

    public List<EmployeeDTO> getEmployeesWithHighestSalery(int count) {
        EntityManager em = emf.createEntityManager();
        try {
            Query query = em.createQuery("SELECT e FROM Employee e ORDER BY e.salary DESC").setMaxResults(count);
            List<Employee> result = query.getResultList();
            ListIterator<Employee> listIt = result.listIterator();
            List<EmployeeDTO> wrapped = new ArrayList<>();
            while (listIt.hasNext()) {
                wrapped.add(new EmployeeDTO(listIt.next()));
            }
            return wrapped;
        } finally {
            em.close();
        }
    }

    public EmployeeDTO createEmployee(String name, String address, int salary) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Employee employee = new Employee(name, address, salary);
            em.persist(employee);
            em.getTransaction().commit();
            return new EmployeeDTO(employee);
        } finally {
            em.close();
        }
    }
}
