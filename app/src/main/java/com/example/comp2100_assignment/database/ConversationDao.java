package com.example.comp2100_assignment.database;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.comp2100_assignment.Singleton;
import com.example.comp2100_assignment.conversations.Conversation;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/***
 * Class which implements the DAO and singleton patterns for a Conversation
 *
 * @author Ziling Ouyang
 */
public class ConversationDao implements DaoPattern<Conversation, String>, Singleton {

    private static ConversationDao conversationDao = null;

    public static ConversationDao singleton() {

        if (conversationDao == null) {
            conversationDao = new ConversationDao();
        }
        return conversationDao;
    }

    private static HashMap<String, Conversation> conversations = new HashMap<>();

    private ConversationDao() {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance(
                "https://comp2100-team-assignment-default-rtdb.asia-southeast1.firebasedatabase.app/"
        ).getReference();

        mDatabase.child(getChildName()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Conversation conversation = snapshot.getValue(Conversation.class);
                conversations.put(snapshot.getKey(), conversation);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Conversation conversation = snapshot.getValue(Conversation.class);
                conversations.put(snapshot.getKey(), conversation);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    @Override
    public void get(String id, OnGetDataListener<Conversation> listener) {
        DaoPattern.super.get(id, listener);
    }

    public Conversation getConversation(String id) {
        return conversations.get(id);
    }

    @Override
    public String getChildName() {
        return "conversation";
    }

    @Override
    public Class<Conversation> getModelClass() {
        return Conversation.class;
    }

    @Override
    public void getAll(OnGetDataListener<Conversation> listener) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance(
                "https://comp2100-team-assignment-default-rtdb.asia-southeast1.firebasedatabase.app/"
        ).getReference();

        mDatabase.child(getChildName()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Conversation conversation = snapshot.getValue(Conversation.class);
                listener.onSuccess(conversation);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Conversation conversation = snapshot.getValue(Conversation.class);
                listener.onSuccess(conversation);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public void save(Conversation conversation, boolean filledOrNew) {
        // Create database reference
        DatabaseReference mDatabase = FirebaseDatabase.getInstance(
                "https://comp2100-team-assignment-default-rtdb.asia-southeast1.firebasedatabase.app/"
        ).getReference();

        if (filledOrNew) { // If the user is entirely new or just needs to be entirely updated
            mDatabase.child(getChildName()).child(conversation.getTimestamp()).setValue(conversation);
        } else { // If the user only needs to be partially updated
            if (conversation.getUser1() != null)
                mDatabase.child(getChildName()).child(String.valueOf(conversation.getTimestamp()))
                        .child("username").setValue(conversation.getUser1());
            if (conversation.getUser2() != null)
                mDatabase.child(getChildName()).child(String.valueOf(conversation.getTimestamp()))
                        .child("password").setValue(conversation.getUser2());
            if (conversation.getLanguage1() != null)
                mDatabase.child(getChildName()).child(String.valueOf(conversation.getTimestamp()))
                        .child("blockedUsers").setValue(conversation.getLanguage1());
            if (conversation.getLanguage2() != null)
                mDatabase.child(getChildName()).child(String.valueOf(conversation.getTimestamp()))
                        .child("displayName").setValue(conversation.getLanguage2());
            if (conversation.getTimestamp() != null)
                mDatabase.child(getChildName()).child(String.valueOf(conversation.getTimestamp()))
                        .child("profilePicture").setValue(conversation.getTimestamp());
            if (conversation.getMessages() != null)
                mDatabase.child(getChildName()).child(String.valueOf(conversation.getTimestamp()))
                        .child("familiarity").setValue(conversation.getMessages());
        }
    }

    @Override
    public void delete(String id) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child(getChildName()).child(id).removeValue();
    }
}
