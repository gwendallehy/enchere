package fr.eni.tp.projet.dal;

import fr.eni.tp.projet.bo.Categories;

import java.util.List;

public interface CategoriesDAO {
    Categories findCategoriesById(int id);
    List<Categories> findAllCategories();
    void createCategories(Categories categories);
}
