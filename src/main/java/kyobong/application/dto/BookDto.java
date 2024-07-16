package kyobong.application.dto;


import java.util.List;
import lombok.Builder;
import lombok.Value;

@Value
public class BookDto {

    long id;
    String title;
    String author;
    boolean isRentable;
    List<CategoryDto> categoryList;


    @Builder
    private BookDto(long id, String title, String author, boolean isRentable, List<CategoryDto> categoryList) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isRentable = isRentable;
        this.categoryList = categoryList;
    }
}
