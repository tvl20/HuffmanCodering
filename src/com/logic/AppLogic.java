package com.logic;

import java.util.*;

public class AppLogic implements ILogic
{
    private TreeMap codeTree = null;

    @Override
    public String encode(String input)
    {
        if (codeTree == null)
        {
            generateTree(input);
        }

        return null;
    }

    @Override
    public String decode(String input)
    {
        return null;
    }

    private Node generateTree(String source)
    {
        HashMap<Character, Integer> characterFrequency = new HashMap<>();
        for (int i = 0; i < source.length(); i++)
        {
            char currentCharacter = source.charAt(i);

            if (characterFrequency.containsKey(currentCharacter))
            {
                int frequency = characterFrequency.get(currentCharacter) + 1;
                characterFrequency.replace(currentCharacter, frequency);
            }
            else
            {
                characterFrequency.put(currentCharacter, 1);
            }
        }


        Set<Map.Entry<Character, Integer>> allCharacters = characterFrequency.entrySet();
        PriorityQueue<Node> sortedFrequencyQueue = new PriorityQueue<>();

        for (Map.Entry<Character, Integer> character : allCharacters)
        {
            sortedFrequencyQueue.add(new Node(character.getKey(), character.getValue()));
        }


        while(sortedFrequencyQueue.size() != 1)
        {
            // Remove 2 nodes with the lowest frequency
            Node child1 = sortedFrequencyQueue.poll();
            Node child2 = sortedFrequencyQueue.poll();
            Node Paranet = new Node(child1, child2, child1.getFrequency() + child2.getFrequency());

            sortedFrequencyQueue.add(Paranet);
        }

        return sortedFrequencyQueue.poll();
    }
}
