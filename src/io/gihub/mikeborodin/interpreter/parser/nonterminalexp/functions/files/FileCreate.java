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
import java.util.List;

public class FileCreate implements IExpression {
    private final int ArgNumber = 1;
    private IExpression _fileExpr;

    public FileCreate(List<IExpression> expr)
    {
        if (expr.size() != ArgNumber)
            throw new SyntaxException("FileWrite accepts "+ArgNumber+" arguments.");
        _fileExpr = expr.get(0);
    }
    @Override
    public Value interpret(IContext context)
    {
        Value result = _fileExpr.interpret(context);
        String fileName = (String) result.data;

        if (result.type == Types.Path)
        {

            try {
               File file = new File(fileName);
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            catch(IOException ex) {
                System.out.println(
                        "Error writing to file '"
                                + fileName + "'");
                // Or we could just do this:
                ex.printStackTrace();
            }

        }
        return null;
    }
}
