package ru.sber.mobile_phone_shop.repository.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sber.mobile_phone_shop.datasource.ConnectionManager;
import ru.sber.mobile_phone_shop.exception.NotValidFieldInputException;
import ru.sber.mobile_phone_shop.model.Phone;
import ru.sber.mobile_phone_shop.repository.PhoneRepository;
import ru.sber.mobile_phone_shop.repository.mapper.PhoneResultMapper;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.List;
import java.util.Optional;

/**
 * Репозиторий для работы с базой данных (БД) содержащей информацию о телефонах
 */
public class PhoneRepositoryImpl implements PhoneRepository {
    private static final Logger logger = LogManager.getLogger(PhoneRepositoryImpl.class);
    private final ConnectionManager connectionManager;
    private final PhoneResultMapper phoneResultMapper;

    public PhoneRepositoryImpl(ConnectionManager connectionManager, PhoneResultMapper phoneResultMapper) {
        this.connectionManager = connectionManager;
        this.phoneResultMapper = phoneResultMapper;
    }

    /**
     * Найти и вернуть все записи из БД с информацией о телефонах
     * @return все записи о телефонах
     */
    @Override
    public List<Phone> findAll() {
        String query = "SELECT id, model, manufacturer, production_date, serial_number, color FROM phones;";
        try (Connection connection = connectionManager.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            return phoneResultMapper.mapAll(resultSet);
        } catch (SQLException e) {
            logger.error(e);
        }
        return List.of();
    }

    /**
     * Записать новую запись о телефоне
     * @param phone информация о телефоне
     * @return запись сохраненная в БД
     */
    @Override
    public Phone save(Phone phone) {
        String query = "INSERT INTO phones (model, manufacturer, production_date, serial_number, color) VALUES (?, ?, ?, ?, ?) RETURNING *;";
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, phone.getModel());
            statement.setString(2, phone.getManufacturer());
            statement.setDate(3, Date.valueOf(phone.getProductionDate()));
            statement.setObject(4, phone.getSerialNumber().toString(), Types.OTHER);
            statement.setString(5, phone.getColor());
            ResultSet resultSet = statement.executeQuery();
            return phoneResultMapper.map(resultSet);
        } catch (SQLException e) {
            logger.error(e);
        }
        throw new NotValidFieldInputException();
    }

    /**
     * Найти запись о телефоне по id
     * @param id уникальный ключ записи в БД
     * @return запись о телефоне обернутая в класс Optional, для недопущения вызова NullPointerException
     */
    @Override
    public Optional<Phone> findById(Long id) {
        String query = "SELECT id, model, manufacturer, production_date, serial_number, color FROM phones WHERE id = ?;";
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            return Optional.of(phoneResultMapper.map(resultSet));
        } catch (SQLException e) {
            logger.error(e);
        }
        return Optional.empty();
    }

    /**
     * Изменить запись о телефоне в БД
     * @param id уникальный ключ записи в БД
     * @param phone информация о телефоне
     * @return измененная запись о телефоне
     */
    @Override
    public Phone update(Long id, Phone phone) {
        String query = "UPDATE phones SET model=?, manufacturer=?, production_date=?, serial_number=?, color=? WHERE id = ? RETURNING *;";
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, phone.getModel());
            statement.setString(2, phone.getManufacturer());
            statement.setDate(3, Date.valueOf(phone.getProductionDate()));
            statement.setObject(4, phone.getSerialNumber().toString(), Types.OTHER);
            statement.setString(5, phone.getColor());
            statement.setLong(6, id);
            ResultSet resultSet = statement.executeQuery();
            return phoneResultMapper.map(resultSet);
        } catch (SQLException e) {
            logger.error(e);
        }
        throw new  NotValidFieldInputException();
    }

    /**
     * Удалить запись о телефоне из БД
     * @param phone информация о телефоне
     * @return информация о удаленной записи
     */
    @Override
    public Phone delete(Phone phone) {
        String query = "DELETE FROM phones WHERE id = ? RETURNING *;";
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, phone.getId());
            ResultSet resultSet = statement.executeQuery();
            return phoneResultMapper.map(resultSet);
        } catch (SQLException e) {
            logger.error(e);
        }
        throw new  NotValidFieldInputException();
    }
}
