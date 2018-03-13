package com.gui;

import com.logic.AppLogic;
import com.logic.ILogic;

import java.io.File;

public class DecodeString
{
    private ILogic logic;

    public static void main(String[] args)
    {
        DecodeString application = new DecodeString();
        application.logic = new AppLogic();

        File inputFile = new File("src/huffmanFile.txt");
        System.out.println("Decoded: ");
        String decoded = application.logic.decode(inputFile);
        System.out.println(decoded);
    }
}
