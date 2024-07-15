package kyobong.controller.dto;


import java.util.List;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Value;

@Value
public class EnrollBookDto {

    @NotBlank String title;
    @NotBlank String author;
    @NotEmpty List<Long> categoryList;


    @Builder
    private EnrollBookDto(String title, String author, List<Long> categoryList) {
        this.title = title;
        this.author = author;
        this.categoryList = List.copyOf(categoryList);
    }
}
