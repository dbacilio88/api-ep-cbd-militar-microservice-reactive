package pe.mil.microservices.constants;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ValidationConstants {

    public static final String REGEX_DOCUMENT_NUMBER = "/[0-9]{8}$/";
    public static final String REGEX_CIP_NUMBER = "/^\\d{8}(?:[-\\s]\\d{4})?$/";
    public static final String REGEX_DOCUMENT_NUMBER_MESSAGE = "Is not a valid document number";
    public static final String REGEX_CIP_NUMBER_MESSAGE = "Is not a valid cip number";
}
