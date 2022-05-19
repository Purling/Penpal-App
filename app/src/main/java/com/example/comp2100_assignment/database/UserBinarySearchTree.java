package com.example.comp2100_assignment.database;

import com.example.comp2100_assignment.users.User;

/***
 * Implementation of the Binary Search Tree
 *
 * @author Ziling Ouyang
 */
public class UserBinarySearchTree implements BinarySearchTree<String, User> {

    public static class UserBinaryTreeNode {
        User value;
        String key;
        UserBinaryTreeNode leftChild;
        UserBinaryTreeNode rightChild;
        UserBinaryTreeNode parent;

        public UserBinaryTreeNode getLeftChild() {
            return leftChild;
        }

        public UserBinaryTreeNode getRightChild() {
            return rightChild;
        }

        public UserBinaryTreeNode getParent() {
            return parent;
        }

        public UserBinaryTreeNode(User value) {
            this.value = value;
            this.key = value.getUsername();
        }
    }

    UserBinaryTreeNode head;

    public UserBinarySearchTree() {
        head = null;
    }

    public UserBinaryTreeNode getHead() {
        return head;
    }


    /***
     * Creates a UserBinarySearchTree with the current node set to be the root
     *
     * @param node The node to be set as the root
     * @return A binary search tree containing only one node
     */
    public UserBinarySearchTree setRoot(UserBinaryTreeNode node) {
        node.parent = null;
        this.head = node;
        return this;
    }

    /***
     * Searches through the binary search tree until the right place to input the new tree is found.
     *
     * @param node The node to be added
     */
    public void addSubTree(UserBinaryTreeNode current, UserBinaryTreeNode node) {

        if (node.key.compareToIgnoreCase(current.key) < 0) {
            if (current.leftChild == null) {
                current.leftChild = node;
                node.parent = current;
            } else {
                addSubTree(current.getLeftChild(), node);
            }

        } else {
            if (current.rightChild == null) {
                current.rightChild = node;
                node.parent = current;
            } else {
                addSubTree(current.getRightChild(), node);
            }
        }
    }

    /***
     * Searches through the Binary Search Tree and return the User if found
     * Otherwise, returns null
     *
     * @param username The key used to search through the tree
     * @return The User if found, otherwise null
     */
    private User get(UserBinaryTreeNode current, String username) {
        if (current.key.equals(username)) {
            return current.value;
        } else if (current.leftChild == null && current.rightChild == null) {
            return null;
        }else if(username.compareToIgnoreCase(current.key) < 0) {
            return get(current.leftChild,username);
        } else {
            return get(current.rightChild,username);
        }
    }

    /***
     * Gets the user from the binary search tree
     *
     * @param username The key to search with
     * @return The User
     */
    public User get(String username) {
        if (username == null || username.equals("")) return null;
        return get(head, username);
    }

}
