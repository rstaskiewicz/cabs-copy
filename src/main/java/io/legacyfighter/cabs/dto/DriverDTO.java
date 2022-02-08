package io.legacyfighter.cabs.dto;

import io.legacyfighter.cabs.entity.Driver;

public class DriverDTO {
    private Long id;

    private String firstName;

    private String lastName;

    private String driverLicense;

    private String photo;

    private Driver.Status status;

    private Driver.Type type;

    public DriverDTO(Driver driver) {
        this.id = driver.getId();
        firstName = driver.getFirstName();
        lastName = driver.getLastName();
        driverLicense = driver.getDriverLicense();
        photo = driver.getPhoto();
        status = driver.getStatus();
        type = driver.getType();

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDriverLicense() {
        return driverLicense;
    }

    public void setDriverLicense(String driverLicense) {
        this.driverLicense = driverLicense;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Driver.Status getStatus() {
        return status;
    }

    public void setStatus(Driver.Status status) {
        this.status = status;
    }

    public Driver.Type getType() {
        return type;
    }

    public void setType(Driver.Type type) {
        this.type = type;
    }


}
