package kyobong.controller.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import static java.lang.annotation.ElementType.TYPE;

@Documented
@Constraint(validatedBy = RentalStatusValidator.class)
@Target({TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface RentalStatusValid {

    String message() default "대여 불가능일 때는 불가능 사유를 입력해주세요";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
