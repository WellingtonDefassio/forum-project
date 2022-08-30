package com.forum.config.validacao;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ErroValidacaoHandler {

    private MessageSource messageSource;

    public ErroValidacaoHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<ErroDeFormulaioDto> handle(MethodArgumentNotValidException exception) {
        List<ErroDeFormulaioDto> dto = new ArrayList<>();
        List<FieldError> errors = exception.getBindingResult().getFieldErrors();

        errors.stream().forEach(erro -> {
            String message = messageSource.getMessage(erro, LocaleContextHolder.getLocale());
            ErroDeFormulaioDto erroFormulario = new ErroDeFormulaioDto(erro.getField(), message);
            dto.add(erroFormulario);
        });
        return dto;
    }


}
