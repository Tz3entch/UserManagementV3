package validaton;

import beans.Bean;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

/**
 * Created by Сережа on 25.08.2015.
 */
public class BeanValidator {
    private static Validator validator;
    static {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }
    public static String validateBean(Bean bean) {
        Set<ConstraintViolation<Bean>> validationErrors = validator.validate(bean);
        if(!validationErrors.isEmpty()){
            String message="";
            for(ConstraintViolation<Bean> error : validationErrors){
                message+=(error.getMessageTemplate()+"::"+error.getPropertyPath()+"::"+error.getMessage()+"\n");
            }
            return message;
        }
        else return null;
    }
}
