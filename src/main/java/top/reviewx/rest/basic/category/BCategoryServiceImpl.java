package top.reviewx.rest.basic.category;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import top.reviewx.mapper.CategoryMapper;
import top.reviewx.rest.basic.category.dto.CategoryChildRes;
import top.reviewx.rest.basic.category.dto.CategoryRes;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class BCategoryServiceImpl implements BCategoryService {
    private final BCategoryRepository bCategoryRepository;
    private final CategoryMapper bCategoryMapper;

    @Override
    @Transactional(readOnly = true)
    public List<CategoryRes> getListCategory() {
        return bCategoryRepository.findByIsDeleteFalse()
                .stream()
                .map(o -> {
                    CategoryRes categoryRes = bCategoryMapper.toCategoryRes(o);
                    if (!CollectionUtils.isEmpty(o.getChilds())) {
                        categoryRes.setChilds(o.getChilds().stream()
                                .filter(oo -> !oo.getIsDelete())
                                .map(oo -> {
                                    CategoryChildRes categoryChildRes = bCategoryMapper.toCategoryChildRes(oo);
                                    if (!CollectionUtils.isEmpty(oo.getChilds())) {
                                        categoryChildRes.setChilds(oo.getChilds().stream()
                                                .filter(ooo -> !ooo.getIsDelete())
                                                .map(bCategoryMapper::toCategoryChildRes)
                                                .collect(Collectors.toList()));
                                    }
                                    return categoryChildRes;
                                }).collect(Collectors.toList()));
                    }
                    return categoryRes;
                })
                .collect(Collectors.toList());
    }
}
