package kyobong.persistence;

import java.io.Serializable;
import jakarta.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
public class BookCategoryId implements Serializable {

    private Long bookId;
    private Long categoryId;


    public BookCategoryId() {}


    public BookCategoryId(Long bookId, Long categoryId) {
        this.bookId = bookId;
        this.categoryId = categoryId;
    }
}
