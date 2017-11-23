package edu.mum.domain;

/**
 * Created by Erdenebayar on 11/21/2017
 */
public enum Role {

    ROLE_ADMIN("Admin"), ROLE_COUNCELOR("Councelor"), ROLE_CUSTOMER("Customer");

    private String name;

    Role(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
