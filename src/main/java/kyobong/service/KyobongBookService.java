package kyobong.service;

import java.util.List;
import java.util.Map;
import kyobong.config.InitDataProcess;
import kyobong.controller.dto.EnrollBookDto;
import kyobong.persistence.BookCategoryEntity;
import kyobong.persistence.BookEntity;
import kyobong.persistence.BookEntityRepository;
import kyobong.persistence.CategoryEntity;
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
class KyobongBookService implements GetBookUseCase, EnrollBookUseCase {

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


    @Override
    @Transactional
    public BookDto enrollBook(EnrollBookDto enrollBookDto) {

        Map<Long, String> categoryMap = InitDataProcess.getCategoryMap();

        List<CategoryEntity> categoryEntityList = enrollBookDto.getCategoryList().stream().map(categoryID -> {
            if(categoryMap.containsKey(categoryID)) {
                CategoryEntity categoryEntity = new CategoryEntity();
                categoryEntity.setId(categoryID);
                categoryEntity.setName(categoryMap.get(categoryID));
                return categoryEntity;
            }

            throw new IllegalArgumentException("등록되지 않은 카테고리(" + categoryID + ") 입니다. 카테고리를 먼저 등록해주세요.");
        }).toList();

        final BookEntity bookEntity = new BookEntity();
        bookEntity.setTitle(enrollBookDto.getTitle());
        bookEntity.setAuthor(enrollBookDto.getAuthor());

        categoryEntityList.forEach(categoryEntity -> {
            BookCategoryEntity bookCategoryEntity = new BookCategoryEntity();
            bookCategoryEntity.setCategory(categoryEntity);
            bookEntity.addBookCategoryEntity(bookCategoryEntity);
        });

        BookEntity savedBookEntity = bookEntityRepository.save(bookEntity);

        List<CategoryDto> categoryDtoList = categoryEntityList.stream()
                .map(categoryEntity -> CategoryDto.builder().id(categoryEntity.getId()).name(categoryEntity.getName())
                        .build()).toList();

        return BookDto.builder().id(savedBookEntity.getId()).title(savedBookEntity.getTitle())
                .author(savedBookEntity.getAuthor()).categoryList(categoryDtoList).build();
    }


    @Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.ERROR)
    interface KyobongBookServiceMapper {

        @Mapping(target = "categoryList", source = "categoryList")
        BookDto toBookDto(BookEntity bookEntity, List<CategoryDto> categoryList);
    }
}
