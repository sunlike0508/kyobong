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
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class KyobongBookController {

    private final GetBookUseCase getBookUseCase;
    private final EnrollBookUseCase enrollBookUseCase;
    private final UpdateBookUseCase updateBookUseCase;
    private final ManageCategoryUseCase manageCategoryUseCase;


    @GetMapping("/books")
    public ResponseEntity<List<BookDto>> getProductList() {
        return new ResponseEntity<>(getBookUseCase.getBookList(), HttpStatus.OK);
    }


    @PostMapping("/books")
    public ResponseEntity<BookDto> enrollBook(@Valid @RequestBody EnrollBookDto enrollBookDto) {
        return new ResponseEntity<>(enrollBookUseCase.enrollBook(enrollBookDto), HttpStatus.OK);
    }


    @PatchMapping("/books/{id}")
    public ResponseEntity<BookDto> updateBook(@PathVariable(name = "id") long bookID,
            @Valid @RequestBody UpdateBookDto updateBookDto) {
        return new ResponseEntity<>(updateBookUseCase.updateBook(bookID, updateBookDto), HttpStatus.OK);
    }


    @PostMapping("/categories")
    public ResponseEntity<CategoryDto> enrollCategory(@Valid @RequestBody EnrollCategoryDto enrollCategoryDto) {
        return new ResponseEntity<>(manageCategoryUseCase.enrollCategory(enrollCategoryDto), HttpStatus.OK);
    }
    // 카테고리 별 검색

    // 지은이 or 제목으로 검색
}
