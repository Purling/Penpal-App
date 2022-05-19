package com.example.comp2100_assignment.database;

/***
 * Class which basically implements callback when the data returns from the database
 *
 * @author Ziling Ouyang
 * @param <T>
 */
public interface OnGetDataListener<T> {
    void onSuccess(T data);
}
