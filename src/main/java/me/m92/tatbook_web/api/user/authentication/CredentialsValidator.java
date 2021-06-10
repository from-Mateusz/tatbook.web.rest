package me.m92.tatbook_web.api.user.authentication;

import me.m92.tatbook_web.api.common.validations.CombinedValidator;
import me.m92.tatbook_web.api.common.validations.DataIntegrityGuard;
import me.m92.tatbook_web.api.common.validations.ValidationFailureBundle;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@DataIntegrityGuard
public class CredentialsValidator extends CombinedValidator<Credentials> {

    private List<CombinedValidator<Credentials>> supportingValidators;

    @Autowired
    public CredentialsValidator(List<CombinedValidator<Credentials>> supportingValidators) {
        this.supportingValidators = supportingValidators;
    }

    @Override
    public ValidationFailureBundle validate(Credentials projection) {
        CombinedValidator<Credentials> unifiedValidator = unifySupportingValidators();
        if(null == unifiedValidator) {
            return ValidationFailureBundle.ofEmpty();
        }
        ValidationFailureBundle failureBundle = unifiedValidator.validate(projection);
        failureBundle.combineWith(validateWithAnother(projection));
        return failureBundle;
    }

    private CombinedValidator<Credentials> unifySupportingValidators() {
        CombinedValidator<Credentials> combinedValidator = null;
        for(CombinedValidator<Credentials> validator : supportingValidators) {
            if(null == combinedValidator) {
                combinedValidator = validator;
                continue;
            }
            combinedValidator.combine(validator);
        }
        return combinedValidator;
    }
}
