package com.logic;

import java.util.Arrays;

public class Node implements Comparable<Node>
{
    private char key;
    private Node[] children;
    private int frequency;

    public char getKey()
    {
        return key;
    }

    public Node[] getChildren()
    {
        return children;
    }

    public int getFrequency()
    {
        return frequency;
    }

    public Node(char key, int frequency)
    {
        this.key = key;
        this.children = null;
        this.frequency = frequency;
    }

    public Node(Node[] children, int frequency)
    {
        this.key = '\u0000';
        this.children = children;
        this.frequency = frequency;
    }

    @Override
    public int compareTo(Node o)
    {
        return Integer.compare(o.getFrequency(), this.getFrequency());
    }
}
