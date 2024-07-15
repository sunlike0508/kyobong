package kyobong.service;

import java.util.List;
import kyobong.controller.dto.EnrollBookDto;
import kyobong.service.dto.BookDto;

public interface GetBookUseCase {

    List<BookDto> getBookList();

    BookDto enrollBook(EnrollBookDto enrollBookDto);
}
