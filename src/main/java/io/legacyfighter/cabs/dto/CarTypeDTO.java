package io.legacyfighter.cabs.dto;

import io.legacyfighter.cabs.entity.CarType;

public class CarTypeDTO {

    private Long id;

    private CarType.CarClass carClass;

    private CarType.Status status;

    private int carsCounter;

    private String description;

    private int activeCarsCounter;

    private int minNoOfCarsToActivateClass;

    public CarTypeDTO(CarType carType) {
        this.id = carType.getId();
        this.carClass = carType.getCarClass();
        this.status = carType.getStatus();
        this.carsCounter = carType.getCarsCounter();
        this.description = carType.getDescription();
        this.activeCarsCounter = carType.getActiveCarsCounter();
        this.minNoOfCarsToActivateClass = carType.getMinNoOfCarsToActivateClass();
    }

    public CarTypeDTO() {

    }

    public Long getId() {
        return id;
    }

    public CarType.CarClass getCarClass() {
        return carClass;
    }

    public void setCarClass(CarType.CarClass carClass) {
        this.carClass = carClass;
    }

    public CarType.Status getStatus() {
        return status;
    }

    public void setStatus(CarType.Status status) {
        this.status = status;
    }

    public int getCarsCounter() {
        return carsCounter;
    }

    public void setCarsCounter(int carsCounter) {
        this.carsCounter = carsCounter;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getActiveCarsCounter() {
        return activeCarsCounter;
    }

    public void setActiveCarsCounter(int activeCarsCounter) {
        this.activeCarsCounter = activeCarsCounter;
    }


    public int getMinNoOfCarsToActivateClass() {
        return minNoOfCarsToActivateClass;
    }

    public void setMinNoOfCarsToActivateClass(int minNoOfCarsToActivateClass) {
        this.minNoOfCarsToActivateClass = minNoOfCarsToActivateClass;
    }
}


