package com.cardif.framework.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

public class NumberValidator implements Validator {
	private static final String NUMBER_PATTERN = "[0-9]*";

	  private Pattern pattern;
	  private Matcher matcher;

	  public NumberValidator() {
	    pattern = Pattern.compile(NUMBER_PATTERN);
	  }
	@Override
	public void validate(FacesContext context, UIComponent component, Object value)
			throws ValidatorException {
		 matcher = pattern.matcher(value.toString());
		
		    if (!matcher.matches()) {
		
		      FacesMessage msg = new FacesMessage("Falló la validación de la cuenta.", "Formato de cuenta inválido.");
		      msg.setSeverity(FacesMessage.SEVERITY_ERROR);
		      throw new ValidatorException(msg);
		
	}
		  

	}
}

