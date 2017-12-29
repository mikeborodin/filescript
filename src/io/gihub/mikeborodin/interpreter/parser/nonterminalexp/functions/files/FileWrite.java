package io.gihub.mikeborodin.interpreter.parser.nonterminalexp.functions.files;

import io.gihub.mikeborodin.interpreter.Types;
import io.gihub.mikeborodin.interpreter.Value;
import io.gihub.mikeborodin.interpreter.context.IContext;
import io.gihub.mikeborodin.interpreter.parser.IExpression;
import jdk.nashorn.internal.runtime.regexp.joni.exception.SyntaxException;

import java.io.*;
import java.util.List;

public class FileWrite implements IExpression {
    private final int ArgNumber = 2;
    private IExpression _fileExpr;
    private IExpression _contentExpr;

    public FileWrite(List<IExpression> expr)
    {
        if (expr.size() != ArgNumber)
            throw new SyntaxException("FileWrite accepts "+ArgNumber+" arguments.");
        _fileExpr = expr.get(0);
        _contentExpr = expr.get(1);
    }
    @Override
    public Value interpret(IContext context)
    {
        Value result = _fileExpr.interpret(context);
        String fileName = (String) result.data;
        String content = (String) _contentExpr.interpret(context).data;

        if (result.type == Types.Path)
        {

            try {
                // Assume default encoding.
                FileWriter fileWriter =
                        new FileWriter(fileName);

                // Always wrap FileWriter in BufferedWriter.
                BufferedWriter bufferedWriter =
                        new BufferedWriter(fileWriter);

                // Note that write() does not automatically
                // append a newline character.
                bufferedWriter.write(content);

                // Always close files.
                bufferedWriter.close();
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
