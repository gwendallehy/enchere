package fr.eni.tp.projet.bll;

import fr.eni.tp.projet.bo.Categories;

import java.util.List;

public interface CategoriesService {
    Categories findCategoriesById(long category_id);
    List<Categories> findAllCategories();
}
