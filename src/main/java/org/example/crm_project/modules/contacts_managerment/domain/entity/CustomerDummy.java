package org.example.crm_project.modules.contacts_managerment.domain.entity;

import java.util.List;

public class CustomerDummy {

    private Long id;

    private String name;

    private String shortName;

    private String phone;

    private String email;

    private List<Contact> contacts;

    public CustomerDummy() {
    }

    public CustomerDummy(Long id, String name, String shortName, String phone, String email) {
        this.id = id;
        this.name = name;
        this.shortName = shortName;
        this.phone = phone;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }
}
