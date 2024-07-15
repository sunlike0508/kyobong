package kyobong.config;


import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;
import kyobong.persistence.BookCategoryEntity;
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

    /**
     * 보통은 redis를 사용하지만 임의 과제이기 때문에 전역변수 사용
     */
    protected static final Map<Long, String> categoryMap = new HashMap<>();

    private final CategoryEntityRepository categoryEntityRepository;
    private final BookEntityRepository bookEntityRepository;


    public static Map<Long, String> getCategoryMap() {
        return Map.copyOf(categoryMap);
    }


    public static void putCategoryMap(long id, String name) {
        categoryMap.put(id, name);
    }


    @Override
    @Transactional
    public void run(String... args) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        File jsonFile = new ClassPathResource("category.json").getFile();

        List<CategoryEntity> categoryEntityList =
                Arrays.asList(objectMapper.readValue(jsonFile, CategoryEntity[].class));

        categoryEntityList = categoryEntityRepository.saveAll(categoryEntityList);

        categoryEntityList.forEach(categoryEntity -> categoryMap.put(categoryEntity.getId(), categoryEntity.getName()));

        jsonFile = new ClassPathResource("books.json").getFile();

        List<BookVO> bookVOList = Arrays.asList(objectMapper.readValue(jsonFile, BookVO[].class));

        bookVOList.forEach(bookVO -> {
            BookEntity bookEntity = new BookEntity();
            bookEntity.setTitle(bookVO.getTitle());
            bookEntity.setAuthor(bookVO.getAuthor());

            CategoryEntity categoryEntity =
                    categoryEntityRepository.findCategoryEntityByName(bookVO.getCategory()).orElseThrow();

            BookCategoryEntity bookCategoryEntity = new BookCategoryEntity();
            bookCategoryEntity.setBook(bookEntity);
            bookCategoryEntity.setCategory(categoryEntity);

            bookEntity.addBookCategoryEntity(bookCategoryEntity);

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
