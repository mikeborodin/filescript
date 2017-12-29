package io.gihub.mikeborodin.console;

import io.gihub.mikeborodin.interpreter.context.IInputManager;

import java.util.Scanner;

public class ConsoleInputManager implements IInputManager {

    Scanner scanner = new Scanner(System.in);

    @Override
    public String getLineFromUser() {
        return scanner.next();
    }

    @Override
    public void printLine(String s) {
        System.out.println(s);
    }
}
