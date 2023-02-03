package pe.mil.microservices.components.validations;

import pe.mil.microservices.components.enums.MilitarValidationResult;
import pe.mil.microservices.dto.requests.RegisterMilitarRequest;

import java.util.function.Function;
import java.util.function.Predicate;

@FunctionalInterface
public interface IMilitarRegisterValidation
    extends Function<RegisterMilitarRequest, MilitarValidationResult> {

    static IMilitarRegisterValidation isMilitarIdValidation() {
        return request ->
            request.getMilitarId() != null
                && !request.getMilitarId().isBlank()
                && !request.getMilitarId().isEmpty()
                ? MilitarValidationResult.MILITAR_VALID
                : MilitarValidationResult.INVALID_MILITAR_ID;
    }

    static IMilitarRegisterValidation isMilitarPersonValidation() {
        return request ->
            request.getPerson() != null
                ? MilitarValidationResult.MILITAR_VALID
                : MilitarValidationResult.INVALID_PERSON;
    }

    static IMilitarRegisterValidation isMilitarGradeValidation() {
        return request ->
            request.getGrade() != null
                ? MilitarValidationResult.MILITAR_VALID
                : MilitarValidationResult.INVALID_GRADE;
    }

    static IMilitarRegisterValidation isMilitarSpecialtyValidation() {
        return request ->
            request.getSpecialty() != null
                ? MilitarValidationResult.MILITAR_VALID
                : MilitarValidationResult.INVALID_SPECIALTY;
    }

    static IMilitarRegisterValidation customValidation(Predicate<RegisterMilitarRequest> validate) {
        return request -> validate.test(request)
            ? MilitarValidationResult.MILITAR_VALID
            : MilitarValidationResult.INVALID_MILITAR_ID;
    }

    default IMilitarRegisterValidation and(IMilitarRegisterValidation andValidation) {
        return request -> {
            MilitarValidationResult validation = this.apply(request);
            return validation.equals(MilitarValidationResult.MILITAR_VALID)
                ? andValidation.apply(request)
                : validation;
        };
    }

    default IMilitarRegisterValidation or(IMilitarRegisterValidation orValidation) {
        return request -> {
            MilitarValidationResult validation = this.apply(request);
            return validation.equals(MilitarValidationResult.MILITAR_VALID)
                ? orValidation.apply(request)
                : validation;
        };
    }
}
