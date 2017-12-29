package io.gihub.mikeborodin.interpreter.context;

import com.sun.istack.internal.NotNull;
import io.gihub.mikeborodin.interpreter.Types;
import io.gihub.mikeborodin.interpreter.Value;
import io.gihub.mikeborodin.interpreter.lexer.LanguageSyntax;
import jdk.nashorn.internal.runtime.regexp.joni.exception.SyntaxException;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static io.gihub.mikeborodin.interpreter.lexer.LanguageSyntax.THISFILE;


public class Context implements IContext {

    private HashMap<String, Value> variables;
    private IInputManager inputManager;

    public Context(IInputManager im , String path){
        variables = new HashMap<String, Value>();
        inputManager = im;
        File  currentDir = new File(path);
        addVariable(THISFILE, new Value(Types.String,path));
        addVariable(LanguageSyntax.THISDIR,  new Value(Types.String,currentDir.getAbsolutePath()));
    }

    @Override
    public void addVariable(String name, Value value) {
        if (!variables.containsKey(name)) {
            if (value.getType()== Types.Array) {
                variables.put(name,value);
            } else {
                variables.put(name,value);
            }
            return;
        }
        throw new SyntaxException("Variable already exists");
    }

    @Override
    public void addVariable(Types type, String name) {
        if (!variables.containsKey(name))
        {
            if (type == Types.Array)
            {
                variables.put(name, new Value(type, new ArrayList<Value>()));
            }
            else
            {
                variables.put(name, new Value(type));
            }
            return;
        }
        throw new SyntaxException("Variable already exists");
    }

    @Override
    public void addVariable(Types type, String name, Object data) {
        if (!variables.containsKey(name))
        {
            variables.put(name, new Value(type, data));
            return;
        }
        throw new SyntaxException("Variable already exists");
    }

    @Override
    public Value  lookUpVariable(@NotNull String name){
        if (variables.containsKey(name)) {
            return variables.get(name);
        }
        throw new SyntaxException("Variable "+name+" does not exist");
    }

    @Override
    public void addVariable(String name) {
        if (!variables.containsKey(name))
        {
            variables.put(name, new Value(null, null));
            return;
        }
        throw new SyntaxException("Variable already exists");
    }


    @Override
    public String getCurrentFile(){
        return variables.get(THISFILE).toString();
    }
    @Override
    public String getCurrentPath(){
        return variables.get(THISFILE).toString();
    }

    @Override
    public void addToOutput(String s){
         inputManager.printLine(s);
    }
    @Override
    public void addToOutput(int output) {
        inputManager.printLine(String.valueOf(output));
    }
    @Override
    public void addToOutput(boolean output) {
        inputManager.printLine(String.valueOf(output));
    }


    public String getInput(){
        return inputManager.getLineFromUser();
    }
}