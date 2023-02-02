package ru.yandex.practicum.filmorate.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice(value = "ru.yandex.practicum.storage")
public class WrongInputException extends Exception{

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleWrongInput(final WrongInputException e) {
        return Map.of("Ошибка ввода, неправильный ввод данных", e.getMessage());
    }

    public WrongInputException(String s){
        super(s);
    }
}
