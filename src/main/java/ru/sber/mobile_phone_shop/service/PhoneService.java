package ru.sber.mobile_phone_shop.service;

import ru.sber.mobile_phone_shop.servlet.dto.PhoneRequest;
import ru.sber.mobile_phone_shop.servlet.dto.PhoneResponse;

import java.util.List;

/**
 * Интерфейс для сервиса обрабатывающего информацию о телефонах
 * описывает CRUD операции над объектом и дополнительную для получения списка всех объектов
 */
public interface PhoneService {
    PhoneResponse getPhoneById(Long id);
    List<PhoneResponse> getAllPhones();
    PhoneResponse create(PhoneRequest phoneRequest);
    PhoneResponse update(Long id, PhoneRequest phoneRequest);
    PhoneResponse delete(Long id);
}
