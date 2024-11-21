package ru.sber.mobile_phone_shop.repository;

import ru.sber.mobile_phone_shop.model.Phone;

import java.util.List;

public interface PhoneRepository extends CrudRepository<Phone, Long> {
    List<Phone> findAll();
}
