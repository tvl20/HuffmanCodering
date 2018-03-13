package com.logic;

import java.io.File;

public interface ILogic
{
    void encode(String input, File outputFile);
    String decode(File inputFile);
}
