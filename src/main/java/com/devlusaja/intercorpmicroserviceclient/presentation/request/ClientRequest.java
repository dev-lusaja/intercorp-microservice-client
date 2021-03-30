package com.devlusaja.intercorpmicroserviceclient.presentation.request;

import io.swagger.annotations.ApiModelProperty;

public class ClientRequest {
    @ApiModelProperty(notes = "Nombre del nuevo cliente a crear")
    private String name;
    @ApiModelProperty(notes = "Apellido del nuevo cliente a crear")
    private String lastName;
    @ApiModelProperty(notes = "Edad del nuevo cliente a crear")
    private int age;
    @ApiModelProperty(notes = "Fecha de nacimiento del nuevo cliente a crear")
    private String dateOfBirth;

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

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}
