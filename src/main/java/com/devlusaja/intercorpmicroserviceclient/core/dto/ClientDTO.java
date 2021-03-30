package com.devlusaja.intercorpmicroserviceclient.core.dto;
import java.text.ParseException;

public class ClientDTO {
    private String uuid;
    private String name;
    private String lastName;
    private int age;
    private String dateOfBirth;
    private String probableDateOfDeath;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
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

    public String getDateOfBirth() { return dateOfBirth; }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getProbableDateOfDeath() { return probableDateOfDeath; }

    public void setProbableDateOfDeath(String probableDateOfDeath) {
        this.probableDateOfDeath = probableDateOfDeath;
    }
}
