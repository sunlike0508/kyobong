package kyobong.persistence;


import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.core.io.ClassPathResource;

@DataJpaTest
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

        Assertions.assertThat(categoryEntityList).hasSize(5);
    }
}