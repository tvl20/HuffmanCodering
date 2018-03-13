package com.logic;

import java.io.*;
import java.util.*;

public class AppLogic implements ILogic
{
    private Node codeTree = null;
    private HashMap<Character, String> bitCodeLookupTable = null;

    @Override
    public void encode(String input)
    {
        if (codeTree == null)
        {
            codeTree = generateTree(input);
            bitCodeLookupTable = generateBitCodeLookupTable(codeTree);
        }

        BitSet encodedMessage = encodeMessage(bitCodeLookupTable, input);
        System.out.println(encodedMessage.toString());
        writeToFile(encodedMessage);
    }

    @Override
    public String decode()
    {
        BitSet encodedMessage = readFromFile();
        return decodeMessage(encodedMessage);
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

    private BitSet encodeMessage(HashMap<Character, String> lookupTable, String message)
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

    private void writeToFile(BitSet encodedText)
    {
        if (codeTree == null)
        {
            return;
        }

        File huffmanFile = new File("huffman.txt");

        try (FileOutputStream fos = new FileOutputStream(huffmanFile);
             ObjectOutputStream oos = new ObjectOutputStream(fos))
        {
            oos.writeObject(codeTree);
            oos.writeObject(encodedText);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private String decodeMessage(BitSet encodedMessage)
    {
        if (codeTree == null)
        {
            return "";
        }

        StringBuilder output = new StringBuilder();
        for (int i = 0; i < encodedMessage.length() -1;)
        {
            Node node = codeTree;

            while(!node.hasKey())
            {
                if(encodedMessage.get(i))
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

    private BitSet readFromFile()
    {
        File huffmanFile = new File("huffman.txt");
        BitSet output = null;

        // TODO: MAKE USE OF THE SAME STREAM?
        try (FileInputStream fis = new FileInputStream(huffmanFile);
             ObjectInputStream ois = new ObjectInputStream(fis))
        {
            codeTree = (Node) ois.readObject();
            output = (BitSet) ois.readObject();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }

        return output;
    }
}
