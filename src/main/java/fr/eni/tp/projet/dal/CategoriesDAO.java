package fr.eni.tp.projet.dal;

import fr.eni.tp.projet.bo.Categories;

public interface CategoriesDAO {
    Categories findCategoriesById(int id);
    Categories findAllCategories();
    Categories createCategories(Categories categories);
}
