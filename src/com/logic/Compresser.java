package com.logic;

import java.util.*;

public class Compresser
{
    private Node codeTree = null;
    private HashMap<Character, String> bitCodeLookupTable = null;

    public BitSet encodeMessage(String input)
    {
        codeTree = generateTree(input);
        bitCodeLookupTable = generateBitCodeLookupTable(codeTree);

        return encode(bitCodeLookupTable, input);
    }

    public String decodeMessage(Node codeTree, BitSet encodedMessage)
    {
        this.codeTree = codeTree;
        return decode(encodedMessage);
    }

    public Node getCodeTree()
    {
        return codeTree;
    }

    private BitSet encode(HashMap<Character, String> lookupTable, String message)
    {
        int counter = 0;
        BitSet output = new BitSet();

        for (char character : message.toCharArray())
        {
            String bitSequence = lookupTable.get(character);
            for (char bit : bitSequence.toCharArray())
            {
                if (bit == '1')
                {
                    output.set(counter, true);
                }

                counter++;
            }
        }

        // Add one last 1 bit, this is to ensure that it is always read correctly.
        // Else you would get errors if the bits ended on 00
        output.set(counter, true);

        return output;
    }

    private String decode(BitSet encodedMessage)
    {
        if (codeTree == null)
        {
            return "";
        }

        StringBuilder output = new StringBuilder();
        for (int i = 0; i < encodedMessage.length() - 1; )
        {
            Node node = codeTree;

            while (!node.hasKey())
            {
                if (encodedMessage.get(i))
                {
                    node = node.getRightChild();
                }
                else
                {
                    node = node.getLeftChild();
                }
                i++;
            }
            output.append(node.getKey());
        }

        return output.toString();
    }

    private HashMap<Character, Integer> getCharacterFrequency(String input)
    {
        HashMap<Character, Integer> frequencyMap = new HashMap<>();
        for (int i = 0; i < input.length(); i++)
        {
            char currentCharacter = input.charAt(i);

            if (frequencyMap.containsKey(currentCharacter))
            {
                int frequency = frequencyMap.get(currentCharacter) + 1;
                frequencyMap.replace(currentCharacter, frequency);
            }
            else
            {
                frequencyMap.put(currentCharacter, 1);
            }
        }
        return frequencyMap;
    }

    private PriorityQueue<Node> getPriorityQueue(HashMap<Character, Integer> characterFrequency)
    {
        Set<Map.Entry<Character, Integer>> allCharacters = characterFrequency.entrySet();
        PriorityQueue<Node> sortedFrequencyQueue = new PriorityQueue<>();

        for (Map.Entry<Character, Integer> character : allCharacters)
        {
            sortedFrequencyQueue.add(new Node(character.getKey(), character.getValue()));
        }

        return sortedFrequencyQueue;
    }

    private Node buildTree(PriorityQueue<Node> sortedFrequencyQueue)
    {
        while (sortedFrequencyQueue.size() != 1)
        {
            // Remove 2 nodes with the lowest frequency
            Node child1 = sortedFrequencyQueue.poll();
            Node child2 = sortedFrequencyQueue.poll();
            Node parent = new Node(child1, child2, child1.getFrequency() + child2.getFrequency());

            sortedFrequencyQueue.add(parent);
        }

        return sortedFrequencyQueue.poll();
    }

    private Node generateTree(String source)
    {
        HashMap<Character, Integer> characterFrequency = getCharacterFrequency(source);
        PriorityQueue<Node> sortedFrequencyQueue = getPriorityQueue(characterFrequency);
        Node treeBase = buildTree(sortedFrequencyQueue);
        return treeBase;
    }

    private HashMap<Character, String> generateBitCodeLookupTable(Node root)
    {
        HashMap<Character, String> bitSetHashMap = new HashMap<>();
        recursiveTableFiller(bitSetHashMap, root, "");
        return bitSetHashMap;
    }

    private void recursiveTableFiller(HashMap<Character, String> bitSetHashMap, Node currentNode, String code)
    {
        if (!currentNode.hasKey())
        {
            recursiveTableFiller(bitSetHashMap, currentNode.getLeftChild(), code + "0");
            recursiveTableFiller(bitSetHashMap, currentNode.getRightChild(), code + "1");
        }
        else
        {
            bitSetHashMap.put(currentNode.getKey(), code);
        }
    }
}
