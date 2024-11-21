package ru.sber.mobile_phone_shop.exception;

public class NotValidFieldInputException extends RuntimeException {
    public NotValidFieldInputException() {
        super("Не верно введены данные для запроса");
    }
}
