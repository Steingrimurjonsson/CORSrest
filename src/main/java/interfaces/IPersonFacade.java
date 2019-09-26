/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import Exceptions.MissingInputException;
import Exceptions.PersonNotFoundException;

import entities.Person;
import java.util.List;

/**
 *
 * @author stein
 */
public interface IPersonFacade {
  public Person addPerson(String fName) throws MissingInputException;  
  public Person deletePerson(int id) throws PersonNotFoundException;
  public Person getPerson(int id) throws PersonNotFoundException; 
  public List<Person> getAllPersons();  
  public Person editPerson(Person p) throws MissingInputException;    

}



