package com.example.comp2100_assignment;

public class BTree {

    BTree head;
    BTree leftChild;
    BTree rightChild;
    BTree parent;

    public BTree(BTree head) {
        this.head = head;
        this.parent = null;
    }


}
