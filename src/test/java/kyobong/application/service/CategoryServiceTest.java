package kyobong.application.service;

import kyobong.application.dto.CategoryDto;
import kyobong.controller.dto.EnrollCategoryDto;
import kyobong.persistence.CategoryEntity;
import kyobong.persistence.CategoryEntityRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.ArgumentMatcher;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.BDDMockito.given;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Spy
    private CategoryService.CategoryServiceMapper categoryServiceMapper =
            Mappers.getMapper(CategoryService.CategoryServiceMapper.class);
    @Mock
    private CategoryEntityRepository categoryEntityRepository;

    @InjectMocks
    private CategoryService categoryService;


    @Test
    void enrollCategory() {

        EnrollCategoryDto enrollCategoryDto = EnrollCategoryDto.builder().name("과학").build();

        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setId(1L);
        categoryEntity.setName("과학");

        given(categoryEntityRepository.save(argThat(new CategoryEntityTest()))).willReturn(categoryEntity);

        CategoryDto categoryDto = categoryService.enrollCategory(enrollCategoryDto);

        Assertions.assertThat(categoryDto.getId()).isEqualTo(1L);
        Assertions.assertThat(categoryDto.getName()).isEqualTo("과학");
    }


    private static class CategoryEntityTest implements ArgumentMatcher<CategoryEntity> {

        @Override
        public boolean matches(CategoryEntity categoryEntity) {
            return equals(categoryEntity.getName(), "과학");
        }


        private boolean equals(Object actual, Object expected) {
            return expected.equals(actual);
        }
    }
}