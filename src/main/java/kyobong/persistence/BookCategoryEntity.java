package kyobong.persistence;


import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "books_categories")
@NoArgsConstructor
public class BookCategoryEntity {

    @EmbeddedId
    private BookCategoryId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("bookId")
    private BookEntity book;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("categoryId")
    private CategoryEntity category;


    public BookCategoryEntity(BookEntity bookEntity, CategoryEntity categoryEntity) {
        this.book = bookEntity;
        this.category = categoryEntity;
        this.id = new BookCategoryId(bookEntity.getId(), categoryEntity.getId());
    }
}
