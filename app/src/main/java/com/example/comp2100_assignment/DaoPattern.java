package com.example.comp2100_assignment;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public interface DaoPattern<T, V> {

        default void get(V id, OnGetDataListener<T> listener) {
                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

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

        String getChildName();

        Class<T> getModelClass();

        void getAll(OnGetDataListener<List<T>> listener);

        void save(T t, boolean filled);

        void delete(V id);
}
