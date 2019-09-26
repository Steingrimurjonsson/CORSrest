/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;


import entities.Person;

/**
 *
 * @author stein
 */
public class PersonDTO {

    private int id;
    private String fName;
 


    public PersonDTO(Person p) {
        this.fName = p.getFirstName();
          this.id = p.getId();

    }

    public PersonDTO(String fn) {
        this.fName = fn;
        }

    public PersonDTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

   
}
