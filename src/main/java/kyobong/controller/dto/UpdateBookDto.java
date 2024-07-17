package kyobong.controller.dto;


import java.util.List;
import kyobong.persistence.RentalStatus;
import lombok.Builder;
import lombok.Value;

@Value
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
        this.rentalStatus = isRentable == null ? null : getRentalStatus();
    }


    private RentalStatus getRentalStatus() {
        return Boolean.TRUE.equals(isRentable) ?
                RentalStatus.SYSTEM_ERROR :
                RentalStatus.AVAILABLE.equals(rentalStatus) ? RentalStatus.SYSTEM_ERROR : rentalStatus;
    }
}
