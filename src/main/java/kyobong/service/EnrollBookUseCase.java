package kyobong.service;

import kyobong.controller.dto.EnrollBookDto;
import kyobong.service.dto.BookDto;

public interface EnrollBookUseCase {

    BookDto enrollBook(EnrollBookDto enrollBookDto);
}
