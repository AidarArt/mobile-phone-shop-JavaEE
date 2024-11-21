package ru.sber.mobile_phone_shop.repository;

import java.util.Optional;

/**
 * Базовый интерфейс содержащий описание CRUD операций
 * @param <T>  класс являющийся представлением записи из БД в java коде (модель)
 * @param <K>  класс являющийся уникальным ключом записи из БД
 */
public interface CrudRepository <T, K> {
    T save(T t);
    Optional<T> findById(K k);
    T update(K k, T t);
    T delete(T t);
}
