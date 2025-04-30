package fr.eni.tp.projet.bll.impl;

import fr.eni.tp.projet.bll.CategoriesService;
import fr.eni.tp.projet.bo.Categories;
import fr.eni.tp.projet.dal.CategoriesDAO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriesServiceImpl implements CategoriesService {
    private CategoriesDAO categoriesDAO;

    public CategoriesServiceImpl(CategoriesDAO categoriesDAO) {
        this.categoriesDAO = categoriesDAO;
    }

    @Override
    public Categories findCategoriesById(long category_id) {
        return categoriesDAO.findCategoriesById(category_id);
    }

    @Override
    public List<Categories> findAllCategories() {
        return categoriesDAO.findAllCategories();
    }
}
