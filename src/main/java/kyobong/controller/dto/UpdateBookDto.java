package kyobong.controller.dto;


import java.util.List;
import lombok.Builder;
import lombok.Value;

@Value
public class UpdateBookDto {

    String title;
    String author;
    List<Long> categoryList;
    Boolean isRentable;


    @Builder
    private UpdateBookDto(String title, String author, List<Long> categoryList, Boolean isRentable) {
        this.title = title;
        this.author = author;
        this.categoryList = categoryList == null ? List.of() : List.copyOf(categoryList);
        this.isRentable = isRentable;
    }
}
