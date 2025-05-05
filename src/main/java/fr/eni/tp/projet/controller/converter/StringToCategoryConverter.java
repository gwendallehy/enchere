package fr.eni.tp.projet.controller.converter;

import fr.eni.tp.projet.bll.CategoriesService;
import fr.eni.tp.projet.bo.Categories;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToCategoryConverter implements Converter<String, Categories> {

    private final CategoriesService categoriesService;

    public StringToCategoryConverter(CategoriesService categoriesService) {
        this.categoriesService = categoriesService;
    }

    @Override
    public Categories convert(String id) {
        try {
            return categoriesService.getCategoryById(Integer.parseInt(id));
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
