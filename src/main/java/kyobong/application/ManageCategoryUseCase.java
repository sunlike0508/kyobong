package kyobong.application;

import kyobong.application.dto.CategoryDto;
import kyobong.controller.dto.EnrollCategoryDto;

public interface ManageCategoryUseCase {

    CategoryDto enrollCategory(EnrollCategoryDto enrollCategoryDto);
}
