package kyobong.controller;


import java.util.List;
import jakarta.validation.Valid;
import kyobong.controller.dto.EnrollBookDto;
import kyobong.service.GetBookUseCase;
import kyobong.service.dto.BookDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class KyobongBookController {

    private final GetBookUseCase getBookUseCase;


    @GetMapping("/books")
    public ResponseEntity<List<BookDto>> getProductList() {
        return new ResponseEntity<>(getBookUseCase.getBookList(), HttpStatus.OK);
    }


    @PostMapping("/books")
    public ResponseEntity<BookDto> enrollBook(@Valid @RequestBody EnrollBookDto enrollBookDto) {
        return new ResponseEntity<>(getBookUseCase.enrollBook(enrollBookDto), HttpStatus.OK);
    }
}
