package io.gihub.mikeborodin.interpreter.parser.nonterminalexp.functions.files;

import io.gihub.mikeborodin.interpreter.Types;
import io.gihub.mikeborodin.interpreter.Value;
import io.gihub.mikeborodin.interpreter.context.IContext;
import io.gihub.mikeborodin.interpreter.parser.IExpression;
import jdk.nashorn.internal.runtime.regexp.joni.exception.SyntaxException;

import java.io.*;
import java.util.List;

public class FileDirLineCount implements IExpression {
    private final int ArgNumber = 1;
    private IExpression _expression;
    public FileDirLineCount(List<IExpression> expr)
    {
        if (expr.size() != ArgNumber)
            throw new SyntaxException("FileExist accepts "+ArgNumber+" arguments.");
        _expression = expr.get(0);
    }
    @Override
    public Value interpret(IContext context)
    {
        Value result = _expression.interpret(context);
        String path = (String) result.data;

        if (result.type == Types.Path)
        {
            long dirLineCount = dirLineCount(new File(path));
            return new Value(Types.Number,(int)dirLineCount);
        }
        throw new SyntaxException(getClass().getSimpleName()+" is not defined for "+result.type);
    }

    private long dirLineCount(File wd) {
        //get current dir list files
        //for if dir - recur else getlines
        if(!wd.isDirectory()) return FileLineCount.lineCount(wd);

        long lines = 0;
        File[] children = wd.listFiles();
        for (File aChildren : children) {
            if (!wd.isDirectory())
                lines += FileLineCount.lineCount(aChildren);
            else
                lines += dirLineCount(aChildren);
        }
        return lines;
    }
}
