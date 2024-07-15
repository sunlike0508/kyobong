package kyobong.controller;


import java.util.List;
import kyobong.service.GetBookUseCase;
import kyobong.service.dto.BookDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class KyobongBookController {

    private final GetBookUseCase getBookUseCase;


    @GetMapping("/books")
    public ResponseEntity<List<BookDto>> getProductList() {
        return new ResponseEntity<>(getBookUseCase.getBookList(), HttpStatus.OK);
    }
}
