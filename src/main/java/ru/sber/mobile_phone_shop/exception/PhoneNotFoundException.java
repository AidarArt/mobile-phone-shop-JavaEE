package ru.sber.mobile_phone_shop.exception;

public class PhoneNotFoundException extends RuntimeException {
    public PhoneNotFoundException() {
        super("Информация о телефоне не найдена или не существует");
    }
}
