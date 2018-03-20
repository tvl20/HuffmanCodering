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
                testFile1.createNewFile();
            }

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

    @BeforeEach
    void beforeEach()
    {
        logic = new AppLogic();
    }

    @Test
    void encode()
    {
        logic.encode(testString1, testFile1);
        logic.encode(testString2, testFile2);
    }

    // I added 'zz' in front of the function name so that the other test will be run before this one.
    // Because tests are run alphabetically.
    @Test
    void zzdecode()
    {
        String resultString1 = logic.decode(testFile1);
        String resultString2 = logic.decode(testFile2);

        Assert.assertTrue("Test string 1 doesn't match the result.", resultString1.equals(testString1));
        Assert.assertTrue("Test string 2 doesn't match the result.", resultString2.equals(testString2));
    }

}