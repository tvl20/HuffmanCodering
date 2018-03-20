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
                performanceTestFile1.createNewFile();
            }

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

    @BeforeEach
    void beforeEach()
    {
        logic = new AppLogic();
    }

    @Test
    void encode()
    {
        testString1 = generateTestString(10000);
        testString2 = generateTestString(1000000);

        System.out.println("10k: ");
        long startTime = System.currentTimeMillis();
        logic.encode(testString1, performanceTestFile1);
        visualise(10000, startTime);
        System.out.println(LINE_SEPERATOR);

        System.out.println("1m: ");
        startTime = System.currentTimeMillis();
        logic.encode(testString2, performanceTestFile2);
        visualise(1000000, startTime);
        System.out.println(LINE_SEPERATOR);
    }

    // I added 'zz' in front of the function name so that the other test will be run before this one.
    // Because tests are run alphabetically.
    @Test
    void zzdecode()
    {
        System.out.println("10k: ");
        long startTime = System.currentTimeMillis();
        String resultString1 = logic.decode(performanceTestFile1);
        visualise(10000, startTime);
        System.out.println(LINE_SEPERATOR);

        Assert.assertTrue("Test string 1 doesn't match the result.", resultString1.equals(testString1));

        System.out.println("1m: ");
        startTime = System.currentTimeMillis();
        String resultString2 = logic.decode(performanceTestFile2);
        visualise(1000000, startTime);
        System.out.println(LINE_SEPERATOR);

        Assert.assertTrue("Test string 2 doesn't match the result.", resultString2.equals(testString2));
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