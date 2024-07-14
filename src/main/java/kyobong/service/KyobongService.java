package kyobong.service;

import kyobong.persistence.BookEntityRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class KyobongService {

    private final BookEntityRepository bookEntityRepository;
}
