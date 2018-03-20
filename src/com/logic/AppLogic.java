package com.logic;

import java.io.*;
import java.util.*;

public class AppLogic implements ILogic
{
    private Compresser compresser;

    public AppLogic()
    {
        compresser = new Compresser();
    }

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

        BitSet encodedMessage = compresser.encodeMessage(input);
        writeToFile(encodedMessage, compresser.getCodeTree(), outputFile);
    }

    @Override
    public String decode(File inputFile)
    {
        if (!inputFile.exists())
        {
            System.out.println("Input file doesn't exist");
            return "";
        }

        Object[] readData = readFromFile(inputFile);
        return compresser.decodeMessage((Node) readData[0], (BitSet) readData[1]);
    }

    private void writeToFile(BitSet encodedText, Node codeTree, File outputFile)
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

    // TODO: PRETTY THIS UP, so that it isn't Object[] but 2 different things
    private Object[] readFromFile(File inputFile)
    {
        Object[] output = new Object[2];

        try (FileInputStream fis = new FileInputStream(inputFile);
             ObjectInputStream ois = new ObjectInputStream(fis))
        {
            output[0] = ois.readObject();
            output[1] = ois.readObject();
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
