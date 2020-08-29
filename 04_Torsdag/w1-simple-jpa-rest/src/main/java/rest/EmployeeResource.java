package rest;

import com.google.gson.Gson;
import dto.EmployeeDTO;
import facades.EmployeeFacade;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("employee")
public class EmployeeResource {

    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");
    EmployeeFacade facade = EmployeeFacade.getFacade(emf);

    @GET
    @Path("/all")
    @Produces({MediaType.APPLICATION_JSON})
    public String getAllEmployeesJSON() {
        List<EmployeeDTO> employees = facade.getAllEmployees();
        return new Gson().toJson(employees);
    }

    @GET
    @Path("/highestpaid")
    @Produces({MediaType.APPLICATION_JSON})
    public String getHighestPaidEmployeeJSON() {
        List<EmployeeDTO> employees = facade.getEmployeesWithHighestSalery(1);
        return new Gson().toJson(employees);
    }

    @GET
    @Path("/name/{name}")
    @Produces({MediaType.APPLICATION_JSON})
    public String getEmployeeByNameJSON(@PathParam("name") String name) {
        List<EmployeeDTO> employees = facade.getEmployeeByName(name);
        return new Gson().toJson(employees);
    }

    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public String getEmployeeByIdJSON(@PathParam("id") int id) {
        EmployeeDTO employee = facade.getEmployeeById(id);
        return new Gson().toJson(employee);
    }

    @GET
    @Path("/create_employees")
    @Produces({MediaType.APPLICATION_JSON})
    public String createEmployees() {
        try {
            facade.createEmployees();
            return "Employees created";
        } catch (Exception e) {
            return e.toString();
        }
    }
}
