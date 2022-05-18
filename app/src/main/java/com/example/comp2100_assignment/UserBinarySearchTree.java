package com.example.comp2100_assignment;

/***
 * Implementation of the Binary Search Tree
 *
 */
public class UserBinarySearchTree implements BinarySearchTree<String, User> {

    UserBinarySearchTree head;
    UserBinarySearchTree leftChild;
    UserBinarySearchTree rightChild;
    UserBinarySearchTree parent;
    User value;
    String key;

    public UserBinarySearchTree(User value) {
        this.value = value;
        this.key = value.getUsername();
    }

    public UserBinarySearchTree(User value, UserBinarySearchTree head) {
        this.value = value;
        this.key = value.getUsername();
        this.head = head;
    }

    /***
     * Creates a UserBinarySearchTree with the current node set to be the root
     *
     * @param value The value contained in the root
     * @return A binary search tree containing only one node
     */
    public UserBinarySearchTree createRoot(User value) {
        this.key = value.getUsername();
        this.value = value;
        this.parent = null;
        this.head = this;
        return this;
    }

    /***
     * Searches through the binary search tree until the right place to input the new tree is found.
     *
     * @param subTree The tree to be added
     * @return A binary search tree with the new tree added
     */
    public UserBinarySearchTree addSubTree(UserBinarySearchTree subTree) {
        if (subTree.key.compareToIgnoreCase(this.key) < 0) {
            if (leftChild == null) {
                leftChild = subTree;
                subTree.parent = this;
                return subTree.head;
            } else {
                return leftChild.addSubTree(subTree);
            }

        } else {
            if (rightChild == null) {
                rightChild = subTree;
                subTree.parent = this;
                return subTree.head;
            } else {
                return rightChild.addSubTree(subTree);
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
    public User search(String username) {
        if (key.equals(username)) {
            return value;
        } else if (leftChild == null && rightChild == null) {
            return null;
        }else if(username.compareToIgnoreCase(key) < 0) {
            return leftChild.search(username);
        } else {
            return rightChild.search(username);
        }
    }

}
