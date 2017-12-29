package io.gihub.mikeborodin.interpreter.context;

import io.gihub.mikeborodin.interpreter.Types;
import io.gihub.mikeborodin.interpreter.Value;

public interface IContext {

    String getCurrentFile();
    String getCurrentPath();
    Value lookUpVariable(String name);

    void addVariable(String name);
    void addVariable(Types type, String name);
    void addVariable(String name,Value value);
    void addVariable(Types type, String name, Object data);

    void addToOutput(String output);
    void addToOutput(int output);
    void addToOutput(boolean output);
    String getInput();
}
