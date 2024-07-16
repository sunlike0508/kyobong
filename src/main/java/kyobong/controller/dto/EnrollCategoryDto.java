package kyobong.controller.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;


@Getter
public class EnrollCategoryDto {

    @NotBlank
    private String name;


    public EnrollCategoryDto() {

    }


    @Builder
    public EnrollCategoryDto(String name) {
        this.name = name;
    }

}
