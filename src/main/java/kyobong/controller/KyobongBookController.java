package kyobong.controller;


import java.util.List;
import jakarta.validation.Valid;
import kyobong.application.EnrollBookUseCase;
import kyobong.application.GetBookUseCase;
import kyobong.application.ManageCategoryUseCase;
import kyobong.application.UpdateBookUseCase;
import kyobong.application.dto.BookDto;
import kyobong.application.dto.CategoryDto;
import kyobong.controller.dto.EnrollBookDto;
import kyobong.controller.dto.EnrollCategoryDto;
import kyobong.controller.dto.UpdateBookDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class KyobongBookController {

    private final GetBookUseCase getBookUseCase;
    private final EnrollBookUseCase enrollBookUseCase;
    private final UpdateBookUseCase updateBookUseCase;
    private final ManageCategoryUseCase manageCategoryUseCase;


    /**
     * 책 전체 조회 or 작가 or 제목 조회
     */
    @GetMapping("/books")
    public ResponseEntity<List<BookDto>> getBookList(@RequestParam(name = "author", required = false) String author,
            @RequestParam(name = "title", required = false) String title) {
        return new ResponseEntity<>(getBookUseCase.getBookList(author, title), HttpStatus.OK);
    }


    /**
     * 책 등록
     */
    @PostMapping("/books")
    public ResponseEntity<BookDto> enrollBook(@Valid @RequestBody EnrollBookDto enrollBookDto) {
        return new ResponseEntity<>(enrollBookUseCase.enrollBook(enrollBookDto), HttpStatus.OK);
    }


    /**
     * 책 업데이트
     */
    @PatchMapping("/books/{id}")
    public ResponseEntity<BookDto> updateBook(@PathVariable(name = "id") long bookID,
            @Valid @RequestBody UpdateBookDto updateBookDto) {
        return new ResponseEntity<>(updateBookUseCase.updateBook(bookID, updateBookDto), HttpStatus.OK);
    }


    /**
     * 카테고리 등록
     */
    @PostMapping("/categories")
    public ResponseEntity<CategoryDto> enrollCategory(@Valid @RequestBody EnrollCategoryDto enrollCategoryDto) {

        CategoryDto categoryDto = manageCategoryUseCase.enrollCategory(enrollCategoryDto);

        return new ResponseEntity<>(categoryDto, HttpStatus.OK);
    }


    /**
     * 카테고리로 책 검색
     */
    @GetMapping("/books/categories/{id}")
    public ResponseEntity<List<BookDto>> getBookListByCategory(@PathVariable(name = "id") long categoryID) {
        return new ResponseEntity<>(getBookUseCase.getBookListByCategory(categoryID), HttpStatus.OK);
    }
}
