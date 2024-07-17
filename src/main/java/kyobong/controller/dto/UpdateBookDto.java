package kyobong.controller.dto;


import java.util.List;
import kyobong.controller.validator.RentalStatusValid;
import kyobong.persistence.RentalStatus;
import lombok.Builder;
import lombok.Value;

@Value
@RentalStatusValid(message = "대여 불가능일 때는 불가능 사유를 입력해주세요")
public class UpdateBookDto {

    String title;
    String author;
    List<Long> categoryList;
    Boolean isRentable;
    RentalStatus rentalStatus;


    @Builder
    private UpdateBookDto(String title, String author, List<Long> categoryList, Boolean isRentable,
            RentalStatus rentalStatus) {
        this.title = title;
        this.author = author;
        this.categoryList = categoryList == null ? List.of() : List.copyOf(categoryList);
        this.isRentable = isRentable;
        this.rentalStatus = rentalStatus;
    }
}
