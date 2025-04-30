package fr.eni.tp.projet.bll;

import fr.eni.tp.projet.bo.Categories;

import java.util.List;

public interface CategoriesService {
    List<Categories> getAllCategories();
    Categories getCategoryById(long id);
}

