package com.java.cms.service;

import com.java.cms.dao.CategoryDao;
import com.java.cms.entity.Category;
import com.java.cms.vo.FirstCategory;
import com.java.cms.vo.SecondCategory;
import com.java.commonutils.jpa.base.service.BaseService;
import com.java.commonutils.jpa.dynamic.SimpleSpecificationBuilder;
import org.springframework.beans.BeanUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService  extends BaseService<CategoryDao,Category> {

    /**
     * 获取所有分类数据,封装到 List<FirstCategory> 当中
     * @return
     */
    public List<FirstCategory> getCategoryList(){
        //获取一级分类 parent_id = 0
        SimpleSpecificationBuilder simpleSpecificationBuilder1 = new SimpleSpecificationBuilder();
        simpleSpecificationBuilder1.and("parentId","=","0");
        Specification<Category> specification1 = simpleSpecificationBuilder1.getSpecification();

        List<Category> firstCategorys = dao.findAll(specification1);

        //获取二级分类 parent_id != 0
        SimpleSpecificationBuilder simpleSpecificationBuilder2 = new SimpleSpecificationBuilder();
        simpleSpecificationBuilder2.and("parentId","!=","0");
        Specification<Category> specification2 = simpleSpecificationBuilder2.getSpecification();

        List<Category> secondCategorys = dao.findAll(specification2);

        //创建封装一级分类集合
        List<FirstCategory> firstCategoryList = new ArrayList<>();
        //循环一级分类数据赋值
        for (int i = 0; i < firstCategorys.size(); i++) {
            // 得到一级分类对象
            Category category1 = firstCategorys.get(i);
            //创建封装一级分类对象
            FirstCategory firstCategory = new FirstCategory();
            //赋值以后，还没有当前一级分类下的二级分类
            BeanUtils.copyProperties(category1,firstCategory);

            //创建封装二级分类的集合
            List<SecondCategory> secondCategoryList = new ArrayList<>();
            //循环得到二级分类
            for (int j = 0; j < secondCategorys.size(); j++) {
                //得到当前二级分类的对象
                Category category2 = secondCategorys.get(j);
                // 验证二级分类的 parentId 等于一级分类的 Id
                if (category2.getParentId().equals(category1.getId())){
                    // 创建二级分类的封装对象
                    SecondCategory secondCategory = new SecondCategory();
                    BeanUtils.copyProperties(category2,secondCategory);
                    secondCategoryList.add(secondCategory);
                }
            }
            firstCategory.setSecondCategoryList(secondCategoryList);
            firstCategoryList.add(firstCategory);
        }
        return firstCategoryList;
    }

}
