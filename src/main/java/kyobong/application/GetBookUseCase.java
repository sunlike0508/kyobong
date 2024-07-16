package kyobong.application;

import java.util.List;
import kyobong.application.dto.BookDto;

public interface GetBookUseCase {

    List<BookDto> getBookList();
}
