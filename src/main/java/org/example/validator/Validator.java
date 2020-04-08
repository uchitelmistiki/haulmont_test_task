package org.example.validator;

import java.time.LocalDate;

public class Validator {

    protected  boolean isValidNumber(String num){
        try {
            if(num.length()!=0)
            Long.valueOf(num);
        } catch (NumberFormatException ex) {
            return false;
        }
        return true;
    }

    protected  String isEmptyValue(String field, String value) {
        String status = "Ok";
        if (value.equals("") || value.length() == 0) {
            status = "Поле "+field+" должно быть заполнено !";
        }
        return status;
    }

    protected  String isNullDateValue(String dateField, LocalDate dateValue) {
        String status = "Ok";
        if (dateValue == null) {
            status = "Некорректное значение даты в поле "+dateField+" !";
        }
        return status;
    }

    protected String transformToOnlyText(String value){
        value = value.replaceAll("[^а-яА-я]", "");
        return value;
    }

}
