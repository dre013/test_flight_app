package by.dre.je.jdbc.validator;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class ValidatorResult {

    @Getter
    private final List<Error> errors = new ArrayList<>();

    public void add(Error error) {
        this.errors.add(error);
    }

    public boolean isValid() {
        return errors.isEmpty();
    }
}
