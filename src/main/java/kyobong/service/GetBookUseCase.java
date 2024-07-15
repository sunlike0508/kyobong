package kyobong.service;

import java.util.List;
import kyobong.service.dto.BookDto;

public interface GetBookUseCase {

    List<BookDto> getBookList();
}
