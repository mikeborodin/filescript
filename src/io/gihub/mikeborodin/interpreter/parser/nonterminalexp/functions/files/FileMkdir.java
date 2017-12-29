package io.gihub.mikeborodin.interpreter.parser.nonterminalexp.functions.files;

import io.gihub.mikeborodin.interpreter.Types;
import io.gihub.mikeborodin.interpreter.Value;
import io.gihub.mikeborodin.interpreter.context.IContext;
import io.gihub.mikeborodin.interpreter.parser.IExpression;
import jdk.nashorn.internal.runtime.regexp.joni.exception.SyntaxException;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class FileMkdir implements IExpression {
    private final int ArgNumber = 1;
    private IExpression _fileExpr;

    public FileMkdir(List<IExpression> expr)
    {
        if (expr.size() != ArgNumber)
            throw new SyntaxException("FileMkdir accepts "+ArgNumber+" arguments.");
        _fileExpr = expr.get(0);
    }
    @Override
    public Value interpret(IContext context)
    {
        Value result = _fileExpr.interpret(context);
        String fileName = (String) result.data;

        if (result.type == Types.Path)
        {
               File file = new File(fileName);
               file.mkdirs();
        }
        return null;
    }
}
