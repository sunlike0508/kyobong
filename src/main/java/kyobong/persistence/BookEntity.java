package kyobong.persistence;


import java.util.HashSet;
import java.util.Set;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Getter
@Setter
@Table(name = "books")
@NoArgsConstructor
@DynamicUpdate
public class BookEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String author;

    @OneToMany(mappedBy = "book", fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
    private Set<BookCategoryEntity> bookCategoryList = new HashSet<>();

    private boolean isRentable = true;


    public void addBookCategoryEntity(CategoryEntity categoryEntity) {
        BookCategoryEntity bookCategoryEntity = new BookCategoryEntity(this, categoryEntity);
        bookCategoryList.add(bookCategoryEntity);
    }


    public void clearCategories() {

        bookCategoryList.forEach(bookCategory -> bookCategory.getCategory().getBookCategoryList().remove(bookCategory));

        bookCategoryList.clear();
    }


    //    public void removeCategory(CategoryEntity categoryEntity) {
    //        BookCategoryEntity bookCategoryEntity = new BookCategoryEntity(this, categoryEntity);
    //
    //        categoryEntity.getBookCategoryList().remove(bookCategoryEntity);
    //
    //        bookCategoryList.remove(bookCategoryEntity);
    //    }
    //
    //
    //    public boolean hasCategoryEntity(CategoryEntity categoryEntity) {
    //        BookCategoryEntity bookCategoryEntity = new BookCategoryEntity(this, categoryEntity);
    //        return bookCategoryList.contains(bookCategoryEntity);
    //    }
}
