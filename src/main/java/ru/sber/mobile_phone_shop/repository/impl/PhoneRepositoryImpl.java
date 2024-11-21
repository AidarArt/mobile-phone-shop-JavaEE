package ru.sber.mobile_phone_shop.repository.impl;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sber.mobile_phone_shop.datasource.ConnectionManager;
import ru.sber.mobile_phone_shop.model.Phone;
import ru.sber.mobile_phone_shop.repository.PhoneRepository;
import ru.sber.mobile_phone_shop.repository.mapper.PhoneResultMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

public class PhoneRepositoryImpl implements PhoneRepository {
    private static final Logger log = LogManager.getLogger(PhoneRepositoryImpl.class);
    private final ConnectionManager connectionManager;
    private final PhoneResultMapper phoneResultMapper;
    private static final String ERR_MSG = "Не верно введены данные для запроса";

    public PhoneRepositoryImpl(ConnectionManager connectionManager, PhoneResultMapper phoneResultMapper) {
        this.connectionManager = connectionManager;
        this.phoneResultMapper = phoneResultMapper;
    }

    @Override
    public List<Phone> findAll() {
        String query = "SELECT id, model, manufacturer, production_date, serial_number, color FROM phones;";
        try (Connection connection = connectionManager.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            return phoneResultMapper.mapAll(resultSet);
        } catch (SQLException e) {
            log.log(Level.ERROR, e.getMessage());
        }
        return List.of();
    }

    @Override
    public Phone save(Phone phone) {
        String query = "INSERT INTO phones (model, manufacturer, production_date, serial_number, color) VALUES (?, ?, ?, ?, ?);";
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, phone.getModel());
            statement.setString(2, phone.getManufacturer());
            statement.setString(3, phone.getProductionDate().toString());
            statement.setString(4, phone.getSerialNumber().toString());
            statement.setString(5, phone.getColor());
            ResultSet resultSet = statement.executeQuery();
            return phoneResultMapper.map(resultSet);
        } catch (SQLException e) {
            log.log(Level.ERROR, e.getMessage());
        }
        throw new IllegalArgumentException(ERR_MSG);
    }

    @Override
    public Optional<Phone> findById(Long id) {
        String query = "SELECT id, model, manufacturer, production_date, serial_number, color FROM phones WHERE id = ?;";
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, id.toString());
            ResultSet resultSet = statement.executeQuery();
            return Optional.of(phoneResultMapper.map(resultSet));
        } catch (SQLException e) {
            log.log(Level.ERROR, e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public Phone update(Long id, Phone phone) {
        String query = "INSERT INTO phones(id, model, manufacturer, production_date, serial_number, color) VALUES (?, ?, ?, ?, ?, ?);";
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            statement.setString(2, phone.getModel());
            statement.setString(3, phone.getManufacturer());
            statement.setString(4, phone.getProductionDate().toString());
            statement.setString(5, phone.getSerialNumber().toString());
            statement.setString(6, phone.getColor());
            ResultSet resultSet = statement.executeQuery();
            return phoneResultMapper.map(resultSet);
        } catch (SQLException e) {
            log.log(Level.ERROR, e.getMessage());
        }
        throw new IllegalArgumentException(ERR_MSG);
    }

    @Override
    public Phone delete(Phone phone) {
        String query = "DELETE FROM phones WHERE id = ?;";
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, phone.getId().toString());
            ResultSet resultSet = statement.executeQuery();
            return phoneResultMapper.map(resultSet);
        } catch (SQLException e) {
            log.log(Level.ERROR, e.getMessage());
        }
        throw new IllegalArgumentException(ERR_MSG);
    }
}
