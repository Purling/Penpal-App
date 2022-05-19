package com.example.comp2100_assignment.database;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.HashMap;

/***
 * @author Zane Gates
 */
public class DatabaseDictionaryWatcher {

    public HashMap<String, String> map = new HashMap<>();

    public DatabaseDictionaryWatcher(DatabaseReference root) {
        root.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                map.put(snapshot.getKey(), snapshot.getValue(String.class));
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                map.remove(previousChildName);
                map.put(snapshot.getKey(), snapshot.getValue(String.class));
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                map.remove(snapshot.getKey());
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
