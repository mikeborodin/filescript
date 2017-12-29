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
import java.nio.file.Files;
import java.util.List;

public class FileRemove implements IExpression {
    private final int ArgNumber = 1;
    private IExpression _fileExpr;

    public FileRemove(List<IExpression> expr)
    {
        if (expr.size() != ArgNumber)
            throw new SyntaxException("FileWrite accepts "+ArgNumber+" arguments.");
        _fileExpr = expr.get(0);
    }
    @Override
    public Value interpret(IContext context)
    {
        Value result = _fileExpr.interpret(context);
        String filePath = (String) result.data;

        if (result.type == Types.Path)
        {

            File file = new File(filePath);

            try {
                Files.deleteIfExists(file.toPath());
            }
            catch(IOException ex) {
                System.out.println(
                        "Error removing to file '"
                                + filePath + "'");
                ex.printStackTrace();
            }
        }
        return null;
    }
}
