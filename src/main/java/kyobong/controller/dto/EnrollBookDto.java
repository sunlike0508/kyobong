package kyobong.controller.dto;


import java.util.List;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Value;

@Value
public class EnrollBookDto {

    @NotBlank(message = "제목을 입력해주세요") String title;
    @NotBlank(message = "지은이를 입력해주세요") String author;
    @NotEmpty(message = "최소 한 개 이상 카테고리를 입력해주세요") List<Long> categoryList;


    @Builder
    private EnrollBookDto(String title, String author, List<Long> categoryList) {
        this.title = title;
        this.author = author;
        this.categoryList = List.copyOf(categoryList);
    }
}
