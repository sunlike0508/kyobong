package kyobong.controller;

import java.util.List;
import com.google.gson.Gson;
import kyobong.application.EnrollBookUseCase;
import kyobong.application.GetBookUseCase;
import kyobong.application.ManageCategoryUseCase;
import kyobong.application.UpdateBookUseCase;
import kyobong.application.dto.BookDto;
import kyobong.application.dto.CategoryDto;
import kyobong.config.RestDocsConfiguration;
import kyobong.controller.document.KyobongBookControllerDocument;
import kyobong.controller.dto.EnrollBookDto;
import kyobong.controller.dto.EnrollCategoryDto;
import kyobong.controller.dto.UpdateBookDto;
import kyobong.persistence.RentalStatus;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureRestDocs
@ImportAutoConfiguration(RestDocsConfiguration.class)
@WebMvcTest(controllers = KyobongBookController.class)
class KyobongBookControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @MockBean
    private GetBookUseCase getBookUseCase;
    @MockBean
    private EnrollBookUseCase enrollBookUseCase;
    @MockBean
    private UpdateBookUseCase updateBookUseCase;
    @MockBean
    private ManageCategoryUseCase manageCategoryUseCase;


    @Test
    void getBookList() throws Exception {

        CategoryDto categoryDto = CategoryDto.builder().id(1L).name("카테고리").build();

        List<BookDto> bookDtoList = List.of(BookDto.builder().id(1L).author("지은이").title("제목").isRentable(true)
                .rentalStatus(RentalStatus.AVAILABLE).categoryList(List.of(categoryDto)).build());

        given(getBookUseCase.getBookList("author", "title")).willReturn(bookDtoList);

        this.mockMvc.perform(
                        get("/books").param("author", "author").param("title", "title").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andDo(KyobongBookControllerDocument.getBookList());
    }


    @Test
    void enrollBook() throws Exception {

        CategoryDto categoryDto = CategoryDto.builder().id(1L).name("카테고리").build();

        BookDto bookDto =
                BookDto.builder().id(1L).author("지은이").title("제목").isRentable(true).categoryList(List.of(categoryDto))
                        .rentalStatus(RentalStatus.AVAILABLE).build();

        EnrollBookDto enrollBookDto =
                EnrollBookDto.builder().author("지은이").title("제목").categoryList(List.of(1L)).build();

        given(enrollBookUseCase.enrollBook(enrollBookDto)).willReturn(bookDto);

        this.mockMvc.perform(
                        post("/books").contentType(MediaType.APPLICATION_JSON).content(new Gson().toJson(enrollBookDto)))
                .andExpect(status().isOk()).andDo(KyobongBookControllerDocument.enrollBook());
    }


    @Test
    void updateBook() throws Exception {

        CategoryDto categoryDto = CategoryDto.builder().id(1L).name("카테고리").build();

        BookDto bookDto =
                BookDto.builder().id(1L).author("지은이").title("제목").isRentable(true).categoryList(List.of(categoryDto))
                        .rentalStatus(RentalStatus.AVAILABLE).build();

        UpdateBookDto updateBookDto =
                UpdateBookDto.builder().author("지은이").title("제목").categoryList(List.of(1L)).isRentable(false)
                        .rentalStatus(RentalStatus.DAMAGED).build();

        given(updateBookUseCase.updateBook(1L, updateBookDto)).willReturn(bookDto);

        this.mockMvc.perform(patch("/books/{id}", 1).contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(updateBookDto))).andExpect(status().isOk())
                .andDo(KyobongBookControllerDocument.updateBook());
    }


    @Test
    void enrollCategory() throws Exception {

        EnrollCategoryDto enrollCategoryDto = EnrollCategoryDto.builder().name("과학").build();

        given(manageCategoryUseCase.enrollCategory(any(EnrollCategoryDto.class))).willReturn(
                CategoryDto.builder().id(1L).name("과학").build());

        this.mockMvc.perform(post("/categories").contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(enrollCategoryDto))).andExpect(status().isOk())
                .andDo(KyobongBookControllerDocument.enrollCategory());
    }


    @Test
    void getBookListByCategory() throws Exception {

        CategoryDto categoryDto = CategoryDto.builder().id(1L).name("카테고리").build();

        List<BookDto> bookDtoList = List.of(BookDto.builder().id(1L).author("지은이").title("제목").isRentable(true)
                .rentalStatus(RentalStatus.AVAILABLE).categoryList(List.of(categoryDto)).build());

        given(getBookUseCase.getBookListByCategory(1L)).willReturn(bookDtoList);

        this.mockMvc.perform(get("/books/categories/{id}", 1L).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andDo(KyobongBookControllerDocument.getBookListByCategory());
    }
}