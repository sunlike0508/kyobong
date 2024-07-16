package kyobong.config;


import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;
import kyobong.persistence.BookEntity;
import kyobong.persistence.BookEntityRepository;
import kyobong.persistence.CategoryEntity;
import kyobong.persistence.CategoryEntityRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class InitDataProcess implements CommandLineRunner {

    private final CategoryEntityRepository categoryEntityRepository;
    private final BookEntityRepository bookEntityRepository;


    @Override
    @Transactional
    public void run(String... args) throws IOException {
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


    @Getter
    @Setter
    private static class BookVO {

        private String title;
        private String author;
        private String category;
    }
}
