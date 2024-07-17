package kyobong.controller.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import kyobong.controller.dto.UpdateBookDto;

class RentalStatusValidator implements ConstraintValidator<RentalStatusValid, UpdateBookDto> {

    @Override
    public boolean isValid(UpdateBookDto updateBookDto, ConstraintValidatorContext context) {

        if(Boolean.FALSE.equals(updateBookDto.getIsRentable())) {
            return updateBookDto.getRentalStatus() != null && !kyobong.persistence.RentalStatus.AVAILABLE.equals(
                    updateBookDto.getRentalStatus());
        }

        return true;
    }
}
