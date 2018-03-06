package com.logic;

import java.util.Arrays;

public class Node implements Comparable<Node>
{
    private char key;
    private Node leftChild;
    private Node rightChild;
    private int frequency;

    public char getKey()
    {
        return key;
    }

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
        this.leftChild = null;
        this.rightChild = null;
        this.frequency = frequency;
    }

    public Node(Node leftChild, Node rightChild, int frequency)
    {
        this.key = '\u0000';
        this.leftChild = leftChild;
        this.rightChild = rightChild;
        this.frequency = frequency;
    }

    @Override
    public int compareTo(Node o)
    {
        return Integer.compare(o.getFrequency(), this.getFrequency());
    }
}