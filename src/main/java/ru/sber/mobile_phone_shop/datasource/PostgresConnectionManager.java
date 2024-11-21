package ru.sber.mobile_phone_shop.datasource;

import org.postgresql.Driver;
import ru.sber.mobile_phone_shop.util.ConfigReader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Класс инкапсулирующий логику получения Connection для базы данных PostgreSQL
 */
public class PostgresConnectionManager implements ConnectionManager {
    private static final String DB_URL = "datasource.url";
    private static final String DB_USERNAME = "datasource.username";
    private static final String DB_PASSWORD = "datasource.password";

    /**
     * Получить Connection для БД PostgreSQL
     * @return Connection
     * @throws SQLException - Исключение при неверно введеных данных: url, username, password
     */
    @Override
    public Connection getConnection() throws SQLException {
        String url = ConfigReader.getProperty(DB_URL);
        String username = ConfigReader.getProperty(DB_USERNAME);
        String password = ConfigReader.getProperty(DB_PASSWORD);

        DriverManager.registerDriver(new Driver());
        return DriverManager.getConnection(url, username, password);
    }
}
