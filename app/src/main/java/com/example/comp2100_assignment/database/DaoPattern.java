package com.example.comp2100_assignment.database;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/***
 * The interface for a DAO pattern. This can be extended to many different class types
 *
 * @param <T> The type of the object
 * @param <V> The type of the unique key which identifies the object in the database
 * @author Ziling Ouyang
 */
public interface DaoPattern<T, V> {

    /***
     * A function which returns the wanted object from the Firebase database via a listener. Use the snapshot passed to the listener to access the data.
     *
     * @param id The unique key which identifies the object in the table which is wanted
     * @param listener The listener which activates when the operation to return data has succeeded
     */
    default void get(V id, OnGetDataListener<T> listener) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance(
                "https://comp2100-team-assignment-default-rtdb.asia-southeast1.firebasedatabase.app/"
        ).getReference();

        mDatabase.child(getChildName()).child(id.toString()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listener.onSuccess(snapshot.getValue(getModelClass()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    /***
     * Returns the String which is the child name in the database. e.g., "userList"
     *
     * @return The child name which is a table label above the object wanted
     */
    String getChildName();

    /***
     * Returns the class which the object belongs to in order to pass the right value to the snapshot.
     *
     * @return The class which the object belongs to.
     */
    Class<T> getModelClass();

    /***
     * Returns all the objects of the specified class type under a certain table label/child. The listener holds all of the objects within the data snapshot contained within the listener.
     *
     * @param listener The listener which contains the objects wanted.
     */
    void getAll(OnGetDataListener<T> listener);

    /***
     * Saves an object to the database. This object can either be a completely new object not seen in the database or one which already exists in the database.
     *
     * @param t The object to be saved to the database
     * @param filledOrNew True if the object is brand new or every variable of the object needs to be replaced. False if only a few variables of the object needs to be replaced and it already exists in the database.
     */
    void save(T t, boolean filledOrNew);

    /***
     * Deletes an object from the database depending on the object key which is given.
     *
     * @param id The unique key which represents the object to be deleted in the database
     */
    void delete(V id);
}
