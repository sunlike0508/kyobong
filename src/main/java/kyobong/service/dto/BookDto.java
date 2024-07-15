package kyobong.service.dto;


import java.util.List;
import lombok.Builder;
import lombok.Value;

@Value
public class BookDto {

    long id;
    String title;
    String author;
    List<CategoryDto> categoryList;


    @Builder
    private BookDto(long id, String title, String author, List<CategoryDto> categoryList) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.categoryList = categoryList;
    }
}
