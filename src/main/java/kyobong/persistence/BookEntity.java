package kyobong.persistence;


import java.util.HashSet;
import java.util.Set;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RentalStatus rentalStatus = RentalStatus.AVAILABLE;

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
}
