package ru.sber.mobile_phone_shop.repository;

import java.util.Optional;

public interface CrudRepository <T, K> {
    T save(T t);
    Optional<T> findById(K k);
    T update(K k, T t);
    T delete(T t);
}
