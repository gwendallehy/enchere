package fr.eni.tp.projet.exception;

import java.util.ArrayList;
import java.util.List;

public class BusinessException extends RuntimeException {
    private List<String> keys = new ArrayList<>();

    public BusinessException() {
    }

    public void addKey(String key) {
        keys.add(key);
    }

    public List<String> getKeys() {
        return keys;
    }

    public BusinessException(String key) {
        this.keys.add(key);
    }

}
