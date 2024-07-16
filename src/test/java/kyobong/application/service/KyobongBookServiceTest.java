package kyobong.application.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import kyobong.application.dto.BookDto;
import kyobong.application.dto.CategoryDto;
import kyobong.controller.dto.EnrollBookDto;
import kyobong.controller.dto.UpdateBookDto;
import kyobong.persistence.BookCategoryEntity;
import kyobong.persistence.BookEntity;
import kyobong.persistence.BookEntityRepository;
import kyobong.persistence.CategoryEntity;
import kyobong.persistence.CategoryEntityRepository;
import org.assertj.core.api.Assertions;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.BDDAssertions.catchThrowable;
import org.junit.jupiter.api.DisplayName;
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
class KyobongBookServiceTest {

    @Mock
    private BookEntityRepository bookEntityRepository;
    @Mock
    private CategoryEntityRepository categoryEntityRepository;

    @Spy
    private KyobongBookService.KyobongBookServiceMapper kyobongBookServiceMapper =
            Mappers.getMapper(KyobongBookService.KyobongBookServiceMapper.class);

    @InjectMocks
    private KyobongBookService kyobongBookService;


    @Test
    @DisplayName("책 등록 테스트")
    void enrollBook() {

        EnrollBookDto enrollBookDto =
                EnrollBookDto.builder().title("제목").author("지은이").categoryList(List.of(1L)).build();

        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setId(1L);
        categoryEntity.setName("문학");

        given(categoryEntityRepository.findAllByIdIn(List.of(1L))).willReturn(List.of(categoryEntity));

        BookEntity bookEntity = new BookEntity();
        bookEntity.setId(1L);
        bookEntity.setTitle("제목");
        bookEntity.setAuthor("지은이");

        given(bookEntityRepository.save(argThat(new BookEntityTest()))).willReturn(bookEntity);

        BookDto bookDto = kyobongBookService.enrollBook(enrollBookDto);

        Assertions.assertThat(bookDto.getId()).isEqualTo(1L);
        Assertions.assertThat(bookDto.getTitle()).isEqualTo("제목");
        Assertions.assertThat(bookDto.getCategoryList())
                .isEqualTo(List.of(CategoryDto.builder().id(1).name("문학").build()));
    }


    @Test
    @DisplayName("등록되지 않은 카테고리로 책을 등록하려고 할때 오류 발생")
    void enrollBookError() {

        given(categoryEntityRepository.findAllByIdIn(List.of(1L))).willReturn(List.of());

        EnrollBookDto enrollBookDto = EnrollBookDto.builder().categoryList(List.of(1L)).build();

        Throwable thrown = catchThrowable(() -> kyobongBookService.enrollBook(enrollBookDto));

        assertThat(thrown).as("등록되지 않은 카테고리가 있습니다. 카테고리를 먼저 등록해주세요.").isInstanceOf(IllegalArgumentException.class);
    }


    @Test
    @DisplayName("책 정보 수정 서비스 테스트")
    void updateBook() {
        UpdateBookDto updateBookDto =
                UpdateBookDto.builder().title("제목").author("지은이").isRentable(true).categoryList(List.of(2L, 3L))
                        .build();

        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setId(1L);
        categoryEntity.setName("문학");


        BookEntity bookEntity = new BookEntity();
        bookEntity.setId(1L);
        bookEntity.setTitle("title");
        bookEntity.setAuthor("author");
        bookEntity.setRentable(false);

        Set<BookCategoryEntity> bookCategoryEntitySet = new HashSet<>();
        bookCategoryEntitySet.add(new BookCategoryEntity(bookEntity, categoryEntity));
        bookEntity.setBookCategoryList(bookCategoryEntitySet);

        given(bookEntityRepository.findById(1L)).willReturn(Optional.of(bookEntity));

        CategoryEntity categoryEntity2 = new CategoryEntity();
        categoryEntity2.setId(2L);
        categoryEntity2.setName("과학");

        CategoryEntity categoryEntity3 = new CategoryEntity();
        categoryEntity3.setId(3L);
        categoryEntity3.setName("경영");

        given(categoryEntityRepository.findAllByIdIn(List.of(2L, 3L))).willReturn(
                List.of(categoryEntity2, categoryEntity3));

        given(bookEntityRepository.save(argThat(new BookEntityTest()))).willReturn(bookEntity);

        BookDto bookDto = kyobongBookService.updateBook(1, updateBookDto);

        Assertions.assertThat(bookDto.getTitle()).isEqualTo("제목");
        Assertions.assertThat(bookDto.getAuthor()).isEqualTo("지은이");
        Assertions.assertThat(bookDto.isRentable()).isTrue();
    }


    private static class BookEntityTest implements ArgumentMatcher<BookEntity> {

        @Override
        public boolean matches(BookEntity bookEntity) {
            return equals(bookEntity.getTitle(), "제목") && equals(bookEntity.getAuthor(), "지은이");
        }


        private boolean equals(Object actual, Object expected) {
            return expected.equals(actual);
        }
    }
}