package fr.eni.tp.projet.bll.impl;

import fr.eni.tp.projet.bll.CategoriesService;
import fr.eni.tp.projet.bo.Categories;
import fr.eni.tp.projet.dal.CategoriesDAO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriesServiceImpl implements CategoriesService {

    private final CategoriesDAO categoriesDAO;

    public CategoriesServiceImpl(CategoriesDAO categoriesDAO) {
        this.categoriesDAO = categoriesDAO;
    }

    @Override
    public List<Categories> getAllCategories() {
        return categoriesDAO.findAllCategories();
    }

    @Override
    public Categories getCategoryById(long id) {
        return categoriesDAO.findCategoriesById(id);
    }
}

