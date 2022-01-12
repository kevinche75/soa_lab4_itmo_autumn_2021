package ru.itmo.validator;

import lombok.Getter;
import ru.itmo.converter.FieldConverter;
import stringEntity.Discipline;

import java.io.Serializable;

@Getter
public class Validator implements Serializable {

    public static ValidatorResult validateDiscipline(String disciplineName){
        ValidatorResult validatorResult = new ValidatorResult();
        FieldConverter.stringConvert(disciplineName, "Discipline Name", validatorResult);
        return validatorResult;
    }
}
