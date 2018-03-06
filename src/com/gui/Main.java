package com.gui;

import com.logic.AppLogic;
import com.logic.ILogic;

import java.util.BitSet;

public class Main
{
    private static final String DEFAULT_TEXT = "Een, twee, drie, vier\n" +
            "Hoedje van, hoedje van\n" +
            "Een, twee, drie, vier\n" +
            "Hoedje van papier\n" +
            "\n" +
            "Heb je dan geen hoedje meer\n" +
            "Maak er één van bordpapier\n" +
            "Eén, twee, drie, vier\n" +
            "Hoedje van papier\n" +
            "\n" +
            "Een, twee, drie, vier\n" +
            "Hoedje van, hoedje van\n" +
            "Een, twee, drie, vier\n" +
            "Hoedje van papier\n" +
            "\n" +
            "En als het hoedje dan niet past\n" +
            "Zetten we 't in de glazenkas\n" +
            "Een, twee, drie, vier\n" +
            "Hoedje van papier";

    private static String LINE_SEPERATER = "\n----------------------\n";

    private ILogic logic;

    public static void main(String[] args)
    {
        Main application = new Main();
        application.logic = new AppLogic();

        /*
        System.out.println("Original text:");
        System.out.println(DEFAULT_TEXT + LINE_SEPERATER);

        System.out.println("Encoded:");
        String encodedText = application.logic.encode(DEFAULT_TEXT);
        System.out.println(encodedText + LINE_SEPERATER);

        System.out.println("Decoded: ");
        String decodedText = application.logic.decode(encodedText);
        System.out.println(decodedText + LINE_SEPERATER);
        */
    }
}
