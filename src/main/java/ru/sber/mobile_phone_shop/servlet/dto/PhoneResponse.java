package ru.sber.mobile_phone_shop.servlet.dto;

import java.time.LocalDate;
import java.util.UUID;

public class PhoneResponse {
    private Long id;
    private String manufacturer;
    private String model;
    private UUID serialNumber;
    private String color;
    private LocalDate productionDate;

    public PhoneResponse() {}

    public PhoneResponse(Long id, String manufacturer, String model, UUID serialNumber, String color, LocalDate productionDate) {
        this.id = id;
        this.manufacturer = manufacturer;
        this.model = model;
        this.serialNumber = serialNumber;
        this.color = color;
        this.productionDate = productionDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public UUID getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(UUID serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public LocalDate getProductionDate() {
        return productionDate;
    }

    public void setProductionDate(LocalDate productionDate) {
        this.productionDate = productionDate;
    }
}
