package ru.sber.mobile_phone_shop.servlet.mapper;

import ru.sber.mobile_phone_shop.model.Phone;
import ru.sber.mobile_phone_shop.servlet.dto.PhoneRequest;
import ru.sber.mobile_phone_shop.servlet.dto.PhoneResponse;

/**
 * Маппер для сущности Phone в дто и обратно
 */
public class PhoneMapper {
    private static final String ERR_MSG = "Входной объект не может быть null";

    /**
     * Получить модель 'Phone' сущности из входящей дто 'PhoneRequest'
     * @param phoneRequest - входящая дто 'PhoneRequest'
     * @return модель 'Phone' сущности
     */
    public Phone map(PhoneRequest phoneRequest) {
        if (phoneRequest != null) {
            Phone phone = new Phone();
            phone.setModel(phoneRequest.getModel());
            phone.setManufacturer(phoneRequest.getManufacturer());
            phone.setSerialNumber(phoneRequest.getSerialNumber());
            phone.setProductionDate(phoneRequest.getProductionDate());
            phone.setColor(phoneRequest.getColor());
            return phone;
        }
        throw new IllegalArgumentException(ERR_MSG);
    }

    /**
     * Получить исходящую сущность 'PhoneResponse' из модели 'Phone'
     * @param phone - модель 'Phone'
     * @return исходящая сущность 'PhoneResponse'
     */
    public PhoneResponse map(Phone phone) {
        if (phone != null) {
            PhoneResponse response = new PhoneResponse();
            response.setId(phone.getId());
            response.setModel(phone.getModel());
            response.setManufacturer(phone.getManufacturer());
            response.setSerialNumber(phone.getSerialNumber());
            response.setColor(phone.getColor());
            response.setProductionDate(phone.getProductionDate());
            return response;
        }
        throw new IllegalArgumentException(ERR_MSG);
    }

    /**
     * Предназначен для изменения модели 'Phone', заменяя значения её полей на значения из входящей дто 'PhoneRequest'
     * @param phone - модель 'Phone'
     * @param phoneRequest - входящая дто 'PhoneRequest'
     * @return модель 'Phone' с измененными полями
     */
    public Phone map(Phone phone, PhoneRequest phoneRequest) {
        if (phoneRequest != null) {
            if (phoneRequest.getModel() != null && !phoneRequest.getModel().isEmpty())
                phone.setModel(phoneRequest.getModel());
            if (phoneRequest.getManufacturer() != null && !phoneRequest.getManufacturer().isEmpty())
                phone.setManufacturer(phoneRequest.getManufacturer());
            if (phoneRequest.getSerialNumber() != null)
                phone.setSerialNumber(phoneRequest.getSerialNumber());
            if (phoneRequest.getProductionDate() != null)
                phone.setProductionDate(phoneRequest.getProductionDate());
            if (phoneRequest.getColor() != null && !phoneRequest.getColor().isEmpty())
                phone.setColor(phoneRequest.getColor());
            return phone;
        }
        throw new IllegalArgumentException(ERR_MSG);
    }

}
