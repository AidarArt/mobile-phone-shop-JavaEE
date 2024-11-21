package ru.sber.mobile_phone_shop.repository.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ResultMapper <T> {
    T map(ResultSet resultSet) throws SQLException;
}
