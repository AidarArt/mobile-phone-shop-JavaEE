package ru.sber.mobile_phone_shop.service.impl;

import ru.sber.mobile_phone_shop.exception.PhoneNotFoundException;
import ru.sber.mobile_phone_shop.model.Phone;
import ru.sber.mobile_phone_shop.repository.PhoneRepository;
import ru.sber.mobile_phone_shop.service.PhoneService;
import ru.sber.mobile_phone_shop.servlet.dto.PhoneRequest;
import ru.sber.mobile_phone_shop.servlet.dto.PhoneResponse;
import ru.sber.mobile_phone_shop.servlet.mapper.PhoneMapper;

import java.util.List;

/**
 * Реализация сервиса обрабатывающего информацию о телефонах
 */
public class PhoneServiceImpl implements PhoneService {
    private final PhoneRepository phoneRepository;
    private final PhoneMapper phoneMapper;

    public PhoneServiceImpl(PhoneRepository phoneRepository, PhoneMapper phoneMapper) {
        this.phoneRepository = phoneRepository;
        this.phoneMapper = phoneMapper;
    }

    /**
     * Найти телефон по id
     * @param id уникальный идентификатор присвоеный записи в БД
     * @return информацию о телефоне
     */
    @Override
    public PhoneResponse getPhoneById(Long id) {
        Phone phone = getPhoneModelById(id);
        return phoneMapper.map(phone);
    }

    /**
     * Получить список информации о всех телефонах записанных в БД
     * @return список информации о всех телефонах
     */
    @Override
    public List<PhoneResponse> getAllPhones() {
        List<Phone> phones = phoneRepository.findAll();
        return phones.stream()
                .map(phoneMapper::map)
                .toList();
    }

    /**
     * Создать новую запись о телефоне и сохранить ее в БД
     * @param phoneRequest информация о новом телефоне
     * @return информация о созданном и сохраненном в БД телефоне
     */
    @Override
    public PhoneResponse create(PhoneRequest phoneRequest) {
        return phoneMapper.map(
                phoneRepository.save(
                        phoneMapper.map(phoneRequest)
                )
        );
    }

    /**
     * Изменить поля с информацией о телефоне в БД
     * @param id уникальный идентификатор записи о телефоне в БД
     * @param phoneRequest информация об изменениях (поля, которые необходимо изменить)
     * @return измененная информация о телефоне из БД
     */
    @Override
    public PhoneResponse update(Long id, PhoneRequest phoneRequest) {
        Phone phone = getPhoneModelById(id);
        return phoneMapper.map(
                phoneRepository.update(
                        id,
                       phoneMapper.map(phone, phoneRequest))
        );
    }

    /**
     * Удаление записи информации о телефоне из БД
     * @param id уникальный идентификатор записи о телефоне в БД
     * @return информация об удаленной записи
     */
    @Override
    public PhoneResponse delete(Long id) {
        return phoneMapper.map(
                phoneRepository.delete(
                        getPhoneModelById(id)
                )
        );
    }

    /**
     * Получение записи о телефоне из БД
     * @param id уникальный идентификатор записи о телефоне в БД
     * @return модель для работы с информацией о телефоне из БД
     */
    private Phone getPhoneModelById(Long id) {
        return phoneRepository.findById(id).orElseThrow(PhoneNotFoundException::new);
    }
}
