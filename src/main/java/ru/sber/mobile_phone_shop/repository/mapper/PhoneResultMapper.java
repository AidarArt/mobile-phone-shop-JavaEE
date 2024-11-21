package ru.sber.mobile_phone_shop.repository.mapper;

import ru.sber.mobile_phone_shop.model.Phone;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface PhoneResultMapper extends ResultMapper<Phone> {
    List<Phone> mapAll(ResultSet resultSet) throws SQLException;
}
