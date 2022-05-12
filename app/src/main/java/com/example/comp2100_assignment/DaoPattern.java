package com.example.comp2100_assignment;

import java.util.List;
import java.util.Optional;

public interface DaoPattern<T, V> {

        Optional<T> get(V id);

        List<T> getAll();

        void save(T t, boolean filled);

        void delete(T t);
}
