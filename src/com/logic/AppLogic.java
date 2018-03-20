package com.logic;

import java.io.*;
import java.util.*;

public class AppLogic implements ILogic
{
    private Node codeTree = null;
    private HashMap<Character, String> bitCodeLookupTable = null;

    @Override
    public void encode(String input, File outputFile)
    {
        try
        {
            if (!outputFile.exists())
            {
                System.out.println("Output file doesn't exist");

                if (!outputFile.createNewFile())
                {
                    System.out.println("Output file could not be created, exit program");
                    return;
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return;
        }

        codeTree = generateTree(input);
        bitCodeLookupTable = generateBitCodeLookupTable(codeTree);

        BitSet encodedMessage = encodeMessage(bitCodeLookupTable, input);
        writeToFile(encodedMessage, outputFile);
    }

    @Override
    public String decode(File inputFile)
    {
        if (!inputFile.exists())
        {
            System.out.println("Input file doesn't exist");
            return "";
        }

        BitSet encodedMessage = readFromFile(inputFile);
        return decodeMessage(encodedMessage);
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

    private void writeToFile(BitSet encodedText, File outputFile)
    {
        if (codeTree == null)
        {
            return;
        }

        try (FileOutputStream fos = new FileOutputStream(outputFile);
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

    private BitSet readFromFile(File inputFile)
    {
        BitSet output = null;

        // TODO: MAKE USE OF THE SAME STREAM?
        try (FileInputStream fis = new FileInputStream(inputFile);
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
