package com.example.persistence;

public class Contact {
    public long id;
    public String firstName;
    public String lastName;
    public String emailAddress;

    Contact(long id, String firstName, String lastName, String emailAddress) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName + " (" + emailAddress + ")";
    }
}
