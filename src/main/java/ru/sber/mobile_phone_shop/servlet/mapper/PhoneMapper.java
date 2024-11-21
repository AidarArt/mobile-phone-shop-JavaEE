package ru.sber.mobile_phone_shop.servlet.mapper;

import ru.sber.mobile_phone_shop.model.Phone;
import ru.sber.mobile_phone_shop.servlet.dto.PhoneRequest;
import ru.sber.mobile_phone_shop.servlet.dto.PhoneResponse;

public class PhoneMapper {
    private static final String ERR_MSG = "Входной объект не может быть null";

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

}
