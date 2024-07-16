package kyobong.application;

import kyobong.application.dto.BookDto;
import kyobong.controller.dto.UpdateBookDto;

public interface UpdateBookUseCase {

    BookDto updateBook(long bookID, UpdateBookDto updateBookDto);
}
