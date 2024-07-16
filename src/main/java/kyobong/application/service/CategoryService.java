package kyobong.application.service;


import kyobong.application.ManageCategoryUseCase;
import kyobong.application.dto.CategoryDto;
import kyobong.controller.dto.EnrollCategoryDto;
import kyobong.persistence.CategoryEntity;
import kyobong.persistence.CategoryEntityRepository;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService implements ManageCategoryUseCase {

    private final CategoryEntityRepository categoryEntityRepository;
    private final CategoryServiceMapper categoryServiceMapper;


    @Override
    public CategoryDto enrollCategory(EnrollCategoryDto enrollCategoryDto) {

        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setName(enrollCategoryDto.getName());

        CategoryEntity savedCategoryEntity = categoryEntityRepository.save(categoryEntity);

        return categoryServiceMapper.toCategoryDto(savedCategoryEntity);
    }


    @Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.ERROR)
    interface CategoryServiceMapper {

        CategoryDto toCategoryDto(CategoryEntity savedCategoryEntity);
    }
}
