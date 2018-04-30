package com.prasad.ticketbooking.jpa;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String email;

    protected Customer() {}

    public Customer( String email) {
        this.email = email;
       
    }

    @Override
    public String toString() {
        return String.format(
                "Customer[id=%d, email='%s'']",
                id, email);
    }

}