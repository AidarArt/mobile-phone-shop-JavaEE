package ru.sber.mobile_phone_shop.service.impl;

import ru.sber.mobile_phone_shop.model.Phone;
import ru.sber.mobile_phone_shop.repository.PhoneRepository;
import ru.sber.mobile_phone_shop.service.PhoneService;
import ru.sber.mobile_phone_shop.servlet.dto.PhoneRequest;
import ru.sber.mobile_phone_shop.servlet.dto.PhoneResponse;
import ru.sber.mobile_phone_shop.servlet.mapper.PhoneMapper;

import java.util.List;

public class PhoneServiceImpl implements PhoneService {
    private final PhoneRepository phoneRepository;
    private final PhoneMapper phoneMapper;

    public PhoneServiceImpl(PhoneRepository phoneRepository, PhoneMapper phoneMapper) {
        this.phoneRepository = phoneRepository;
        this.phoneMapper = phoneMapper;
    }

    @Override
    public PhoneResponse getPhoneById(Long id) throws IllegalArgumentException {
        Phone phone = getPhoneModelById(id);
        return phoneMapper.map(phone);
    }

    @Override
    public List<PhoneResponse> getAllPhones() {
        List<Phone> phones = phoneRepository.findAll();
        return phones.stream()
                .map(phoneMapper::map)
                .toList();
    }

    @Override
    public PhoneResponse create(PhoneRequest phoneRequest) {
        return phoneMapper.map(
                phoneRepository.save(
                        phoneMapper.map(phoneRequest)
                )
        );
    }

    @Override
    public PhoneResponse update(Long id, PhoneRequest phoneRequest) {
        Phone phone = phoneMapper.map(phoneRequest);
        return phoneMapper.map(
                phoneRepository.update(id, phone)
        );
    }

    @Override
    public PhoneResponse delete(Long id) {
        return phoneMapper.map(
                phoneRepository.delete(
                        getPhoneModelById(id)
                )
        );
    }

    private Phone getPhoneModelById(Long id) {
        return phoneRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Введен не верный или не существующий ID"));
    }
}
