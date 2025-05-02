package fr.eni.tp.projet.dal;

import fr.eni.tp.projet.bo.Categories;

import java.util.List;

public interface CategoriesDAO {

    Categories findCategoriesById(long category_id);
    List<Categories> findAllCategories();

    void createcategory(Categories categories);

    void updateCategory(Categories category);

    void deleteCategory(long id);

}
