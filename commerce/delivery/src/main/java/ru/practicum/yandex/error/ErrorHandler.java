package ru.practicum.yandex.error;

import jakarta.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.yandex.error.model.ErrorResponse;
import ru.yandex.practicum.exception.NoDeliveryFoundException;
import ru.yandex.practicum.exception.NotAuthorizedUserException;
import ru.yandex.practicum.exception.ProductNotFoundException;

@RestControllerAdvice
public class ErrorHandler {
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler({MethodArgumentNotValidException.class, MissingServletRequestParameterException.class})
	public ErrorResponse handleDateTimeParseException(MethodArgumentNotValidException e) {
		return new ErrorResponse(HttpStatus.BAD_REQUEST,
				"Ошибка Валидации",
				e.getMessage());
	}

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler({NoDeliveryFoundException.class})
	public ErrorResponse handleNotFoundException(ProductNotFoundException e) {
		return new ErrorResponse(HttpStatus.NOT_FOUND, "Сущность не найдена", e.getMessage());
	}

	@ExceptionHandler
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ErrorResponse handleException(final Exception e) {
		return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
				"Произошла непредвиденная ошибка.",
				e.getMessage()
		);
	}

	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	@ExceptionHandler({NotAuthorizedUserException.class})
	public ErrorResponse handleAuthorizedUserException(MethodArgumentNotValidException e) {
		return new ErrorResponse(HttpStatus.UNAUTHORIZED,
				"Ошибка авторизации",
				e.getMessage());
	}

	@ExceptionHandler({ValidationException.class})
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorResponse handleValidationException(final ValidationException e) {
		return new ErrorResponse(
				HttpStatus.BAD_REQUEST,
				"Ошибка валидации",
				e.getMessage()
		);
	}
}
