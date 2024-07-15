package kyobong.service;

import java.util.List;
import kyobong.config.InitDataProcess;
import kyobong.controller.dto.EnrollBookDto;
import kyobong.persistence.BookEntity;
import kyobong.persistence.BookEntityRepository;
import kyobong.service.dto.BookDto;
import kyobong.service.dto.CategoryDto;
import org.assertj.core.api.Assertions;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.BDDAssertions.catchThrowable;
import org.junit.jupiter.api.BeforeEach;
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

    @Spy
    private KyobongBookService.KyobongBookServiceMapper kyobongBookServiceMapper =
            Mappers.getMapper(KyobongBookService.KyobongBookServiceMapper.class);

    @InjectMocks
    private KyobongBookService kyobongBookService;


    @BeforeEach
    void setUp() {
        InitDataProcess.putCategoryMap(1, "문학");
        InitDataProcess.putCategoryMap(2, "과학");
        InitDataProcess.putCategoryMap(3, "소설");
    }


    @Test
    @DisplayName("책 등록 테스트")
    void enrollBook() {

        EnrollBookDto enrollBookDto =
                EnrollBookDto.builder().title("제목").author("지은이").categoryList(List.of(1L)).build();

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

        EnrollBookDto enrollBookDto = EnrollBookDto.builder().categoryList(List.of(4L)).build();

        Throwable thrown = catchThrowable(() -> kyobongBookService.enrollBook(enrollBookDto));

        assertThat(thrown).as("등록되지 않은 카테고리(4) 입니다. 카테고리를 먼저 등록해주세요.").isInstanceOf(IllegalArgumentException.class);
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