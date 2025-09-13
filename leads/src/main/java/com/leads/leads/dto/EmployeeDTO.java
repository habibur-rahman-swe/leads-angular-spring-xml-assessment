package com.leads.leads.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class EmployeeDTO {

    private String firstname;
    private String lastname;
    private String division;
    private String building;
    private String title;
    private String room;

    // getters & setters
    public String getFirstname() { return firstname; }
    public void setFirstaame(String firstName) { this.firstname = firstName; }

    public String getLastname() { return lastname; }
    public void setLastname(String lastName) { this.lastname = lastName; }

    public String getDivision() { return division; }
    public void setDivision(String division) { this.division = division; }

    public String getBuilding() { return building; }
    public void setBuilding(String building) { this.building = building; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getRoom() { return room; }
    public void setRoom(String room) { this.room = room; }
}
