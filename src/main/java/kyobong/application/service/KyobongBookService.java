package kyobong.application.service;

import java.util.List;
import java.util.NoSuchElementException;
import kyobong.application.EnrollBookUseCase;
import kyobong.application.GetBookUseCase;
import kyobong.application.UpdateBookUseCase;
import kyobong.application.dto.BookDto;
import kyobong.application.dto.CategoryDto;
import kyobong.controller.dto.EnrollBookDto;
import kyobong.controller.dto.UpdateBookDto;
import kyobong.persistence.BookEntity;
import kyobong.persistence.BookEntityRepository;
import kyobong.persistence.CategoryEntity;
import kyobong.persistence.CategoryEntityRepository;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
class KyobongBookService implements GetBookUseCase, EnrollBookUseCase, UpdateBookUseCase {

    private final BookEntityRepository bookEntityRepository;
    private final CategoryEntityRepository categoryEntityRepository;
    private final KyobongBookServiceMapper kyobongBookServiceMapper;


    @Override
    @Transactional(readOnly = true)
    public List<BookDto> getBookList() {

        List<BookEntity> bookEntityList = bookEntityRepository.findAll();

        return bookEntityList.stream().map(bookEntity -> {

            List<CategoryDto> categoryList = bookEntity.getBookCategoryList().stream()
                    .map(bookCategoryEntity -> CategoryDto.builder().id(bookCategoryEntity.getCategory().getId())
                            .name(bookCategoryEntity.getCategory().getName()).build()).toList();

            return kyobongBookServiceMapper.toBookDto(bookEntity, categoryList);
        }).toList();
    }


    @Override
    @Transactional
    public BookDto enrollBook(EnrollBookDto enrollBookDto) {

        List<CategoryEntity> categoryEntityList =
                categoryEntityRepository.findAllByIdIn(enrollBookDto.getCategoryList());

        if(categoryEntityList.size() != enrollBookDto.getCategoryList().size()) {
            throw new IllegalArgumentException("등록되지 않은 카테고리가 있습니다. 카테고리를 먼저 등록해주세요.");
        }

        final BookEntity bookEntity = new BookEntity();
        bookEntity.setTitle(enrollBookDto.getTitle());
        bookEntity.setAuthor(enrollBookDto.getAuthor());


        categoryEntityList.forEach(bookEntity::addBookCategoryEntity);

        BookEntity savedBookEntity = bookEntityRepository.save(bookEntity);

        List<CategoryDto> categoryDtoList = categoryEntityList.stream()
                .map(categoryEntity -> CategoryDto.builder().id(categoryEntity.getId()).name(categoryEntity.getName())
                        .build()).toList();

        return kyobongBookServiceMapper.toBookDto(savedBookEntity, categoryDtoList);
    }


    @Override
    @Transactional
    public BookDto updateBook(long bookID, UpdateBookDto updateBookDto) {

        BookEntity bookEntity = bookEntityRepository.findById(bookID)
                .orElseThrow(() -> new NoSuchElementException("등록된 책을 찾을 수 없습니다."));

        if(StringUtils.hasText(updateBookDto.getTitle())) {
            bookEntity.setTitle(updateBookDto.getTitle());
        }

        if(!ObjectUtils.isEmpty(updateBookDto.getAuthor())) {
            bookEntity.setAuthor(updateBookDto.getAuthor());
        }

        if(!ObjectUtils.isEmpty(updateBookDto.getIsRentable())) {
            bookEntity.setRentable(updateBookDto.getIsRentable());
        }

        if(!CollectionUtils.isEmpty(updateBookDto.getCategoryList())) {

            List<CategoryEntity> categoryEntityList =
                    categoryEntityRepository.findAllByIdIn(updateBookDto.getCategoryList());

            if(categoryEntityList.size() != updateBookDto.getCategoryList().size()) {
                throw new IllegalArgumentException("등록되지 않은 카테고리가 있습니다. 카테고리를 먼저 등록해주세요.");
            }

            bookEntity.clearCategories();

            categoryEntityList.forEach(bookEntity::addBookCategoryEntity);
        }

        bookEntityRepository.save(bookEntity);

        List<CategoryDto> categoryDtoList = bookEntity.getBookCategoryList().stream()
                .map(bookCategoryEntity -> CategoryDto.builder().id(bookCategoryEntity.getCategory().getId())
                        .name(bookCategoryEntity.getCategory().getName()).build()).toList();

        return kyobongBookServiceMapper.toBookDto(bookEntity, categoryDtoList);
    }


    @Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.ERROR)
    interface KyobongBookServiceMapper {

        @Mapping(target = "categoryList", source = "categoryList")
        @Mapping(target = "isRentable", source = "bookEntity.rentable")
        BookDto toBookDto(BookEntity bookEntity, List<CategoryDto> categoryList);
    }


}
