package kyobong.service;

import java.util.List;
import kyobong.config.InitDataProcess;
import kyobong.persistence.BookEntity;
import kyobong.persistence.BookEntityRepository;
import kyobong.service.dto.BookDto;
import kyobong.service.dto.CategoryDto;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
class KyobongBookService implements GetBookUseCase {

    private final BookEntityRepository bookEntityRepository;
    private final KyobongBookServiceMapper kyobongBookServiceMapper;


    @Override
    @Transactional(readOnly = true)
    public List<BookDto> getBookList() {

        List<BookEntity> bookEntityList = bookEntityRepository.findAll();

        return bookEntityList.stream().map(bookEntity -> {

            List<CategoryDto> categoryList = bookEntity.getBookCategoryList().stream()
                    .map(bookCategoryEntity -> CategoryDto.builder().id(bookCategoryEntity.getCategory().getId())
                            .name(InitDataProcess.getCategoryMap().get(bookCategoryEntity.getCategory().getId()))
                            .build()).toList();

            return kyobongBookServiceMapper.toBookDto(bookEntity, categoryList);
        }).toList();
    }


    @Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.ERROR)
    interface KyobongBookServiceMapper {

        @Mapping(target = "categoryList", source = "categoryList")
        BookDto toBookDto(BookEntity bookEntity, List<CategoryDto> categoryList);
    }
}
