package kyobong.persistence;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.core.io.ClassPathResource;


@DataJpaTest
class BookEntityRepositoryTest {

    @Autowired
    private CategoryEntityRepository categoryEntityRepository;

    @Autowired
    private BookEntityRepository bookEntityRepository;


    @BeforeEach
    void setUp() throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();

        File jsonFile = new ClassPathResource("category.json").getFile();

        List<CategoryEntity> categoryEntityList =
                Arrays.asList(objectMapper.readValue(jsonFile, CategoryEntity[].class));

        categoryEntityRepository.saveAll(categoryEntityList);

        jsonFile = new ClassPathResource("books.json").getFile();

        List<BookVO> bookVOList = Arrays.asList(objectMapper.readValue(jsonFile, BookVO[].class));

        bookVOList.forEach(bookVO -> {
            BookEntity bookEntity = new BookEntity();
            bookEntity.setTitle(bookVO.getTitle());
            bookEntity.setAuthor(bookVO.getAuthor());

            CategoryEntity categoryEntity =
                    categoryEntityRepository.findCategoryEntityByName(bookVO.getCategory()).orElseThrow();

            bookEntity.addBookCategoryEntity(categoryEntity);

            bookEntityRepository.save(bookEntity);
        });

    }


    @Test
    void findAll() {
        List<BookEntity> bookEntityList = bookEntityRepository.findAll();

        Assertions.assertThat(bookEntityList).hasSize(15);
        Assertions.assertThat(bookEntityList.get(0).getTitle()).isEqualTo("너에게 해주지 못한 말들");
        Assertions.assertThat(bookEntityList.get(0).getBookCategoryList()).hasSize(1);
        Assertions.assertThat(bookEntityList.get(0).getBookCategoryList().iterator().next().getCategory().getName())
                .isEqualTo("문학");
        Assertions.assertThat(bookEntityList.get(0).getAuthor()).isEqualTo("권태영");
    }


    @Getter
    @Setter
    public static class BookVO {

        private String title;
        private String author;
        private String category;
    }
}