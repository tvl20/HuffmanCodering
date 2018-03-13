package com.logic;

import java.io.Serializable;
import java.util.Arrays;

public class Node implements Comparable<Node>, Serializable
{
    private char key = '\u0000';

    // Introduced a new variable because (key != '\u0000') was acting weird
    private boolean gotKey = false;

    private Node leftChild = null;
    private Node rightChild = null;
    private int frequency;

    public char getKey()
    {
        return key;
    }

    public boolean hasKey() { return gotKey; }

    public Node getLeftChild()
    {
        return leftChild;
    }

    public Node getRightChild()
    {
        return rightChild;
    }

    public int getFrequency()
    {
        return frequency;
    }

    public Node(char key, int frequency)
    {
        this.key = key;
        this.gotKey = true;
        this.frequency = frequency;
    }

    public Node(Node leftChild, Node rightChild, int frequency)
    {
        this.leftChild = leftChild;
        this.rightChild = rightChild;
        this.frequency = frequency;
    }

    @Override
    public int compareTo(Node o)
    {
        return Integer.compare(this.getFrequency(), o.getFrequency());
    }
}