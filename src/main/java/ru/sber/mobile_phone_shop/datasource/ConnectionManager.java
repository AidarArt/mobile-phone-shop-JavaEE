package ru.sber.mobile_phone_shop.datasource;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Базовый интерфейс описывающий метод получения Connection, для общения с БД
 */
public interface ConnectionManager {
    Connection getConnection() throws SQLException;
}
