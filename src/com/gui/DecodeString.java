package com.gui;

import com.logic.AppLogic;
import com.logic.ILogic;
import oracle.jrockit.jfr.events.Bits;

import java.util.BitSet;

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
