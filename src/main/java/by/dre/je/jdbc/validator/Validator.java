package by.dre.je.jdbc.validator;

public interface Validator<T> {
    ValidatorResult isValid(T object);
}
