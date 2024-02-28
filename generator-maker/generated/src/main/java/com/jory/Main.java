package com.jory;

import com.jory.cli.CommandExecutor;



public class Main{
    public static void main(String[] args) {

        CommandExecutor commandExecutor = new CommandExecutor();
        commandExecutor.doExecute(args);
    }
}