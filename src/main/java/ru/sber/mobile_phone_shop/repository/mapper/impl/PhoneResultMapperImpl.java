package ru.sber.mobile_phone_shop.repository.mapper.impl;

import ru.sber.mobile_phone_shop.model.Phone;
import ru.sber.mobile_phone_shop.repository.mapper.PhoneResultMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PhoneResultMapperImpl implements PhoneResultMapper {
    @Override
    public List<Phone> mapAll(ResultSet resultSet) throws SQLException {
        List<Phone> phones = new ArrayList<>();
        while (resultSet.next()) {
            Phone phone = new Phone();
            phone.setId(resultSet.getLong(1));
            phone.setModel(resultSet.getString(2));
            phone.setManufacturer(resultSet.getString(3));
            phone.setProductionDate(
                    LocalDate.parse(
                            resultSet.getString(4)
                    )
            );
            phone.setSerialNumber(
                    UUID.fromString(
                            resultSet.getString(5)
                    )
            );
            phone.setColor(resultSet.getString(6));
            phones.add(phone);
        }
        return phones;
    }

    @Override
    public Phone map(ResultSet resultSet) throws SQLException {
        Phone phone = new Phone();
        while (resultSet.next()) {
            phone.setId(resultSet.getLong(1));
            phone.setModel(resultSet.getString(2));
            phone.setManufacturer(resultSet.getString(3));
            phone.setProductionDate(
                    LocalDate.parse(
                            resultSet.getString(4)
                    )
            );
            phone.setSerialNumber(
                    UUID.fromString(
                            resultSet.getString(5)
                    )
            );
            phone.setColor(resultSet.getString(6));
        }
        return phone;
    }
}
