package com.logic;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class AppLogicTest
{
    private static String testString1;
    private static File testFile1;

    private static String testString2;
    private static File testFile2;

    private static ILogic logic;

    @BeforeAll
    static void beforeAll()
    {
        testString1 = "This is TestingString 1";
        testString2 = "This is TestingString 2";

        testFile1 = new File("tests/TestFile1.txt");
        testFile2 = new File("tests/TestFile2.txt");

        try
        {
            if (testFile1.exists())
            {
                testFile1.delete();
            }
            testFile1.createNewFile();

            if (testFile2.exists())
            {
                testFile2.delete();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @Test
    void preexistingFileTest()
    {
        System.out.println(testFile1);
        encodeDecodeTest(testString1, testFile1);
    }

    @Test
    void noPreexistingFileTest()
    {
        System.out.println(testFile2);
        encodeDecodeTest(testString2, testFile2);
    }

    private void encodeDecodeTest(String testString, File testFile)
    {
        logic = new AppLogic();
        logic.encode(testString, testFile);

        logic = new AppLogic();
        String resultString = logic.decode(testFile);
        Assert.assertTrue("Test string doesn't match the result.", resultString.equals(testString));
    }
}