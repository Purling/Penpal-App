package com.example.comp2100_assignment;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import com.example.comp2100_assignment.database.UserBinarySearchTree;
import com.example.comp2100_assignment.reports.Interaction;
import com.example.comp2100_assignment.reports.InteractionType;
import com.example.comp2100_assignment.users.RandomUserGenerator;
import com.example.comp2100_assignment.users.User;

import org.junit.Test;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * Tests for UserBinarySearchTree class
 * @author William Loughton
 */
public class UserBinarySearchTreeTest {

    @Test
    public void testValid(){
        //Generate some users to test
        User user1 = RandomUserGenerator.generateUser();
        User user2 = RandomUserGenerator.generateUser();
        User user3 = RandomUserGenerator.generateUser();
        User user4 = RandomUserGenerator.generateUser();
        User user5 = RandomUserGenerator.generateUser();

        //add users to BST
        UserBinarySearchTree tree = new UserBinarySearchTree().setRoot(new UserBinarySearchTree.UserBinaryTreeNode(user1));
        tree.addSubTree(tree.getHead(),(new UserBinarySearchTree.UserBinaryTreeNode(user2)));
        tree.addSubTree(tree.getHead(),(new UserBinarySearchTree.UserBinaryTreeNode(user3)));
        tree.addSubTree(tree.getHead(),(new UserBinarySearchTree.UserBinaryTreeNode(user4)));
        tree.addSubTree(tree.getHead(),(new UserBinarySearchTree.UserBinaryTreeNode(user5)));

        //check users were added
        assertEquals(user1,tree.get(user1.getUsername()));
        assertEquals(user2,tree.get(user2.getUsername()));
        assertEquals(user3,tree.get(user3.getUsername()));
        assertEquals(user4,tree.get(user4.getUsername()));
        assertEquals(user5,tree.get(user5.getUsername()));
    }

    @Test
    public void testUserNotAdded(){
        User user1 = RandomUserGenerator.generateUser();
        User user2 = RandomUserGenerator.generateUser();
        UserBinarySearchTree tree = new UserBinarySearchTree().setRoot(new UserBinarySearchTree.UserBinaryTreeNode(user1));
        //user was added
        assertEquals(user1,tree.get(user1.getUsername()));
        //user not found returns null
        assertNull(tree.get(user2.getUsername()));
    }
}