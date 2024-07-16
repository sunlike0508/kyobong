package kyobong.controller.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Value;

@Value
public class EnrollCategoryDto {

    @NotBlank String name;


    @Builder
    private EnrollCategoryDto(String name) {
        this.name = name;
    }
}
