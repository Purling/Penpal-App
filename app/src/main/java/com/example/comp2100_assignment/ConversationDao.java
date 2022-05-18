package com.example.comp2100_assignment;

import java.util.List;

public class ConversationDao implements DaoPattern<Conversation, String>, Singleton{

    private static ConversationDao conversationDao = null;

    public static ConversationDao singleton() {

        if (conversationDao == null) {
            conversationDao = new ConversationDao();
        }
        return conversationDao;
    }


    @Override
    public void get(String id, OnGetDataListener<Conversation> listener) {
        DaoPattern.super.get(id, listener);
    }

    @Override
    public String getChildName() {
        return null;
    }

    @Override
    public Class<Conversation> getModelClass() {
        return null;
    }

    @Override
    public void getAll(OnGetDataListener<Conversation> listener) {

    }

    @Override
    public void save(Conversation conversation, boolean filledOrNew) {

    }

    @Override
    public void delete(String id) {

    }
}
