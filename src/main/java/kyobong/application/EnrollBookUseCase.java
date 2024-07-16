package kyobong.application;

import kyobong.application.dto.BookDto;
import kyobong.controller.dto.EnrollBookDto;

public interface EnrollBookUseCase {

    BookDto enrollBook(EnrollBookDto enrollBookDto);
}
