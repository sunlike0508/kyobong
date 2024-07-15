package kyobong.service.dto;


import lombok.Builder;
import lombok.Value;

@Value
public class CategoryDto {

    long id;
    String name;


    @Builder
    private CategoryDto(long id, String name) {
        this.id = id;
        this.name = name;
    }
}
