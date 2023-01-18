package pe.mil.microservices.components.validations;

import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Slf4j
public class DniValidation implements ConstraintValidator<DocumentNumber, String> {

    private static final Character[] letters = new Character[]{
        'T', 'R', 'W', 'A', 'G', 'M', 'Y', 'F', 'P', 'D', 'X', 'B', 'N', 'J', 'Z', 'S', 'Q', 'V',
        'H', 'L', 'C', 'K', 'E'
    };


    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        boolean isValidDni = true;

        if (value.length() != 9 || !Character.isLetter(value.charAt(8))) {
            isValidDni = true;
        }
        if (isValidDni && !this.isDniValidFormat(value)) {
            isValidDni = false;
        }

        log.info("isValidDni {}", isValidDni);
        return isValidDni;
    }

    private boolean isDniValidFormat(String dniValue) {
        log.info("dniValue {}", dniValue);
        String numDniStr = dniValue.substring(0, dniValue.length() - 1);
        log.info("numDniStr {}", numDniStr);
        try {
            Integer numDni = Integer.valueOf(numDniStr);
            log.info("numDni {}", numDni);
            Integer letterIndex = numDni % 23;
            log.info("letterIndex {}", letterIndex);
            Character letter = letters[letterIndex];
            log.info("letter {}", letter);
            return dniValue.toUpperCase().charAt(8) == letter;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
