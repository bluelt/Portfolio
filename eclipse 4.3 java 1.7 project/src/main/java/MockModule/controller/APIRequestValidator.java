package MockModule.controller;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class APIRequestValidator implements Validator {

	@Override
	public boolean supports( Class<?> clazz ) {
		return APIRequest.class.isAssignableFrom( clazz );
	}

	@Override
	public void validate( Object target, Errors errors ) {
		((APIRequest)target).validate();
	}

}
