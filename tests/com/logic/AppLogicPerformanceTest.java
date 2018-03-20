package com.logic;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

class AppLogicPerformanceTest
{
    private final static String ALPHABET = "abcdefghijklmnopqrstuvwxyz ";
    private final static String LINE_SEPERATOR = "-------------------";

    private static String testString1;
    private static File performanceTestFile1;

    private static String testString2;
    private static File performanceTestFile2;

    private static ILogic logic;

    @BeforeAll
    static void beforeAll()
    {
        performanceTestFile1 = new File("tests/PerformanceTestFile1.txt");
        performanceTestFile2 = new File("tests/PerformanceTestFile2.txt");

        try
        {
            if (performanceTestFile1.exists())
            {
                performanceTestFile1.delete();
            }
            performanceTestFile1.createNewFile();

            if (performanceTestFile2.exists())
            {
                performanceTestFile2.delete();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @Test
    void performaceTestPreexistingFile()
    {
        encodeDecodeTest(10000, performanceTestFile1);
        encodeDecodeTest(1000000, performanceTestFile1);
    }

    @Test
    void performaceTestNoPreexistingFile()
    {
        encodeDecodeTest(10000, performanceTestFile2);
        encodeDecodeTest(1000000, performanceTestFile2);
    }

    private void encodeDecodeTest(int numberOfCharacters, File testFile)
    {
        String testString = generateTestString(numberOfCharacters);

        logic = new AppLogic();

        System.out.println("Encoding " + numberOfCharacters + ": ");
        long startTime = System.currentTimeMillis();
        logic.encode(testString, testFile);
        visualise(numberOfCharacters, startTime);
        System.out.println(LINE_SEPERATOR);


        logic = new AppLogic();

        System.out.println("Decoding " + numberOfCharacters + ": ");
        startTime = System.currentTimeMillis();
        String resultString = logic.decode(testFile);
        visualise(numberOfCharacters, startTime);
        System.out.println(LINE_SEPERATOR);


        Assert.assertTrue("Test string doesn't match the result.", resultString.equals(testString));
    }

    private void visualise(int grootte, long start)
    {
        long end = System.currentTimeMillis();
        long nb_miliseconds = (end - start);

        System.out.println("n = " + grootte);
        System.out.println("time = " + nb_miliseconds + "ms");

        SimpleDateFormat sdf = new SimpleDateFormat("KK:mm:ss,SSS");
        Date resultdate = new Date(nb_miliseconds);

        System.out.println("Human readable: " + sdf.format(resultdate));
    }

    private String generateTestString(int length)
    {
        StringBuilder output = new StringBuilder();
        Random rnd = new Random();

        for (int i = 0; i < length; i++)
        {
            output.append(ALPHABET.charAt(rnd.nextInt(ALPHABET.length())));
        }

        return output.toString();
    }
}