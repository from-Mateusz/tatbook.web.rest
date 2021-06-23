package me.m92.tatbook_web.api.user.registration;

import me.m92.tatbook_web.api.common.projection.ProjectionWrapper;
import me.m92.tatbook_web.api.common.validations.CombinedValidator;
import me.m92.tatbook_web.api.common.validations.DataIntegrityGuard;
import me.m92.tatbook_web.api.common.validations.ValidationFailureBundle;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@DataIntegrityGuard
public class RegistrationValidator extends CombinedValidator<ProjectionWrapper<PersonalProfileRegistration>> {

    private List<CombinedValidator<ProjectionWrapper<PersonalProfileRegistration>>> validators;

    @Autowired
    public RegistrationValidator(List<CombinedValidator<ProjectionWrapper<PersonalProfileRegistration>>> validators) {
        this.validators = validators;
    }

    @Override
    public ValidationFailureBundle validate(ProjectionWrapper<PersonalProfileRegistration> projection) {
        ValidationFailureBundle failureBundle = ValidationFailureBundle.ofEmpty();
        CombinedValidator combinedValidator = combineValidators();
        if(null != combinedValidator) {
            failureBundle.combineWith(combinedValidator.validate(projection));
        }
        return failureBundle;
    }

    private CombinedValidator<ProjectionWrapper<PersonalProfileRegistration>> combineValidators() {
        CombinedValidator<ProjectionWrapper<PersonalProfileRegistration>> combinedValidator = null;
        for(CombinedValidator<ProjectionWrapper<PersonalProfileRegistration>> validator : validators) {
            if(null == combinedValidator) {
                combinedValidator = validator;
                continue;
            }
            combinedValidator.combine(validator);
        }
        return combinedValidator;
    }
}
