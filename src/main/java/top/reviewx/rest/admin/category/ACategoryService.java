package top.reviewx.rest.admin.category;

import top.reviewx.rest.admin.category.dto.req.CategoryCreateReq;
import top.reviewx.rest.admin.category.dto.req.UpdateCategoryReq;
import top.reviewx.rest.basic.category.dto.CategoryRes;

public interface ACategoryService {
    CategoryRes createCategory(CategoryCreateReq categoryCreateReq);

    CategoryRes updateCategory(String categoryId, UpdateCategoryReq req);

    String deleteCategory(String categoryId);
}
