package pe.mil.microservices.components.validations;

import pe.mil.microservices.components.enums.MilitarValidationResult;
import pe.mil.microservices.components.enums.PageValidationResult;
import pe.mil.microservices.dto.requests.PageMilitarRequest;
import pe.mil.microservices.dto.requests.RegisterMilitarRequest;

import java.util.function.Function;
import java.util.function.Predicate;

@FunctionalInterface
public interface IPageMilitarValidation
    extends Function<PageMilitarRequest, PageValidationResult> {

    static IPageMilitarValidation isPageMilitarLimitValidation() {
        return request ->
            request.getLimit() != null
                && !request.getLimit().isEmpty()
                && !request.getLimit().isBlank()

                ? PageValidationResult.PAGE_VALID
                : PageValidationResult.INVALID_PAGE_LIMIT;
    }

    static IPageMilitarValidation isPageMilitarPageValidation() {
        return request ->
            request.getPage() != null
                && !request.getPage().isEmpty()
                && !request.getPage().isBlank()

                ? PageValidationResult.PAGE_VALID
                : PageValidationResult.INVALID_PAGE;
    }


    static IPageMilitarValidation customValidation(Predicate<PageMilitarRequest> validate) {
        return request -> validate.test(request)
            ? PageValidationResult.PAGE_VALID
            : PageValidationResult.INVALID_PAGE;
    }

    default IPageMilitarValidation and(IPageMilitarValidation andValidation) {
        return request -> {
            PageValidationResult validation = this.apply(request);
            return validation.equals(PageValidationResult.PAGE_VALID)
                ? andValidation.apply(request)
                : validation;
        };
    }

    default IPageMilitarValidation or(IPageMilitarValidation orValidation) {
        return request -> {
            PageValidationResult validation = this.apply(request);
            return validation.equals(PageValidationResult.PAGE_VALID)
                ? orValidation.apply(request)
                : validation;
        };
    }
}
