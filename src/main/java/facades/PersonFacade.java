package facades;

import Exceptions.MissingInputException;
import Exceptions.PersonNotFoundException;

import entities.Person;
import interfaces.IPersonFacade;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;


public class PersonFacade implements IPersonFacade {

    private static PersonFacade instance;
    private static EntityManagerFactory emf;

    private PersonFacade() {
    }

    public static PersonFacade getPersonFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new PersonFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
  
    @Override
    public Person addPerson(String fName) throws MissingInputException {
        EntityManager em = emf.createEntityManager();
        try {
                   em.getTransaction().begin();
                Person p = new Person(fName);
                em.persist(p);
                em.getTransaction().commit();
                return p;
        } finally {
            em.close();
        }
    }

    @Override
    public Person deletePerson(int id) {
        EntityManager em = emf.createEntityManager();
        try {
            Query q1 = em.createQuery("DELETE FROM Person p where p.id = :id")
                    .setParameter("id", id);
            em.getTransaction().begin();
            Person p = getPerson(id);
            q1.executeUpdate();
            em.getTransaction().commit();
            return p;
        } finally {
            em.close();
        }
    }

    @Override
    public Person getPerson(int id) {
        EntityManager em = emf.createEntityManager();
        try {
            Person p = em.find(Person.class, id);
            return p;
        } finally {
            em.close();
        }
    }

    @Override
    public List<Person> getAllPersons() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery q1 = em.createQuery("Select p from Person p", Person.class);
            return q1.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public Person editPerson(Person person) {
        EntityManager em = emf.createEntityManager();
        try {
            Query q1 = em.createQuery("UPDATE Person p SET p.firstName = :firstName WHERE p.id = :id")
                    .setParameter("firstName", person.getFirstName())
                    .setParameter("id", person.getId());
            em.getTransaction().begin();
            q1.executeUpdate();
            em.getTransaction().commit();
            return getPerson(person.getId());
        } finally {
            em.close();
        }
    }

    public static void main(String[] args) throws MissingInputException {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");
        PersonFacade pf = PersonFacade.getPersonFacade(emf);
        pf.addPerson("NewP");
        pf.addPerson("NewP2");
    }
}
