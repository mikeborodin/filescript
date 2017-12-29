package io.gihub.mikeborodin.interpreter.parser.nonterminalexp.functions.files;

import io.gihub.mikeborodin.interpreter.Types;
import io.gihub.mikeborodin.interpreter.Value;
import io.gihub.mikeborodin.interpreter.context.IContext;
import io.gihub.mikeborodin.interpreter.parser.IExpression;
import jdk.nashorn.internal.runtime.regexp.joni.exception.SyntaxException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileList implements IExpression {
    private final int ArgNumber = 1;
    private IExpression _fileExpr;
    public FileList(List<IExpression> expr)
    {
        if (expr.size() != ArgNumber)
            throw new SyntaxException(getClass().getName()+" accepts "+ArgNumber+" arguments.");
        _fileExpr = expr.get(0);
    }
    @Override
    public Value interpret(IContext context)
    {
        Value result = _fileExpr.interpret(context);
        String fileName = (String) result.data;

        if (result.type == Types.Path)
        {

                // Assume default encoding.
                File file   = new File(fileName);
                if(file.isDirectory()){
                   File[] ls = file.listFiles();
                    Value arr = new Value(Types.Array,new ArrayList<Value>());

                    assert ls != null;
                    for (File f:ls){
                        ((List<Value>)arr.data).add(new Value(Types.Path,f.getAbsolutePath()));
                    }
                    return arr;

                }
                else{
                    throw new SyntaxException("Argument is not a directory");
                }


        }
        return null;
    }
}
