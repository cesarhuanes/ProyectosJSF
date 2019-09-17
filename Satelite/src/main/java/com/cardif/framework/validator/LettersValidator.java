package com.cardif.framework.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

public class LettersValidator implements Validator {

  private static final String TEXT_PATTERN = "^[a-zA-Z0-9]*$";

  private Pattern pattern;
  private Matcher matcher;

  public LettersValidator() {
    pattern = Pattern.compile(TEXT_PATTERN);
  }

  @Override
  public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {

    matcher = pattern.matcher(value.toString());
    if (!matcher.matches()) {

      FacesMessage msg = new FacesMessage("Falló la validación del texto.", "Formato de texto inválido.");
      msg.setSeverity(FacesMessage.SEVERITY_ERROR);
      throw new ValidatorException(msg);

    }

  }
}