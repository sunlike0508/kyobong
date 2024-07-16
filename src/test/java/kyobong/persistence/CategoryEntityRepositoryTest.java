package kyobong.persistence;


import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import com.fasterxml.jackson.databind.ObjectMapper;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.annotation.DirtiesContext;

@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class CategoryEntityRepositoryTest {


    @Autowired
    private CategoryEntityRepository categoryEntityRepository;


    @BeforeEach
    void setUp() throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();

        File jsonFile = new ClassPathResource("category.json").getFile();

        List<CategoryEntity> categoryEntityList =
                Arrays.asList(objectMapper.readValue(jsonFile, CategoryEntity[].class));

        categoryEntityRepository.saveAll(categoryEntityList);
    }


    @Test
    void findAll() {
        List<CategoryEntity> categoryEntityList = categoryEntityRepository.findAll();

        assertThat(categoryEntityList).hasSize(5);
    }


    @Test
    void findAllByIdIn() {

        List<CategoryEntity> test = categoryEntityRepository.findAll();

        List<CategoryEntity> categoryEntityList = categoryEntityRepository.findAllByIdIn(List.of(1L, 2L));

        assertThat(categoryEntityList).hasSize(2);
    }


    @Test
    void findCategoryEntityByName() {

        Optional<CategoryEntity> optionalCategoryEntity = categoryEntityRepository.findCategoryEntityByName("문학");

        assertThat(optionalCategoryEntity).isPresent();
        assertThat(optionalCategoryEntity.get().getName()).isEqualTo("문학");
    }
}