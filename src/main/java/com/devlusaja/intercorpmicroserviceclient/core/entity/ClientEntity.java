package com.devlusaja.intercorpmicroserviceclient.core.entity;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ClientEntity {
    private @Id String uuid;
    private String name;
    private String lastName;
    private int age;
    private String dateOfBirth;
    private String probableDateOfDeath;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String id) {
        this.uuid = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String birthday) {
        this.dateOfBirth = birthday;
    }

    public String getProbableDateOfDeath() {
        return probableDateOfDeath;
    }

    public void setProbableDateOfDeath(String probableDateOfDeath) {
        this.probableDateOfDeath = probableDateOfDeath;
    }
}
