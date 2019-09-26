package rest;

import Exceptions.MissingInputException;
import Exceptions.PersonNotFoundException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.PersonDTO;
import entities.Person;
import utils.EMF_Creator;
import facades.PersonFacade;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

//Todo Remove or change relevant parts before ACTUAL use
@Path("person")
public class ResourcePerson {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory(
            "pu",
            //"jdbc:mysql://localhost:3307/person",
            "jdbc:mysql://157.230.18.125:3306/person",
            "dev",
            "ax2",
            EMF_Creator.Strategy.CREATE);
    private static final PersonFacade FACADE = PersonFacade.getPersonFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String demo() {
        return "{\"msg\":\"PersonWorks\"}";
    }
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    public void create(Person entity) {
        throw new UnsupportedOperationException();
    }

    @GET
    @Path("all")
    @Produces({MediaType.APPLICATION_JSON})
    public String getAllEmployees() {
        List<Person> p = FACADE.getAllPersons();
        return GSON.toJson(p);
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public String getEmployeeById(@PathParam("id") int id) throws PersonNotFoundException {
        Person p = FACADE.getPerson(id);
        if (p == null) {
            throw new PersonNotFoundException("No person with provided id found");
        }
        return GSON.toJson(p);
    }

    @Path("add")
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public String add(String Person) throws MissingInputException {
        Person p = GSON.fromJson(Person, Person.class);
        Person pAdded = FACADE.addPerson(p.getFirstName());
        if (p == null) {
            throw new MissingInputException("First Name and/or Last Name is missing");
        }
        return GSON.toJson(new PersonDTO(pAdded));
    }

    @Path("edit")
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public String edit(String person) throws MissingInputException {
        Person p = GSON.fromJson(person, Person.class);
        Person pEdited = FACADE.editPerson(p);
        if (p == null) {
            throw new MissingInputException("First Name and/or Last Name is missing");
        }
        return GSON.toJson(new PersonDTO(pEdited));
    }

    @DELETE
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public String deleteEmployeeById(@PathParam("id") int id) throws PersonNotFoundException {
        Person p = FACADE.deletePerson(id);
        if (p == null) {
            throw new PersonNotFoundException("Could not delete, provided id does not exist");
        }
        return GSON.toJson(p);
    }
}
