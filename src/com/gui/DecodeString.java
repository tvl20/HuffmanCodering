package com.gui;

import com.logic.AppLogic;
import com.logic.ILogic;

public class DecodeString
{
    private ILogic logic;

    public static void main(String[] args)
    {
        DecodeString application = new DecodeString();
        application.logic = new AppLogic();

        System.out.println("Decoded: ");
        String decoded = application.logic.decode();
        System.out.println(decoded);
    }
}
