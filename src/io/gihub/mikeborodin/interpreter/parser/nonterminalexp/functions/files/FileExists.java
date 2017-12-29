package io.gihub.mikeborodin.interpreter.parser.nonterminalexp.functions.files;

import io.gihub.mikeborodin.interpreter.Converter;
import io.gihub.mikeborodin.interpreter.Types;
import io.gihub.mikeborodin.interpreter.Value;
import io.gihub.mikeborodin.interpreter.context.IContext;
import io.gihub.mikeborodin.interpreter.parser.IExpression;
import jdk.nashorn.internal.runtime.regexp.joni.exception.SyntaxException;

import java.io.File;
import java.util.List;

public class FileExists implements IExpression {
    private final int ArgNumber = 1;
    private IExpression _expression;
    public FileExists(List<IExpression> expr)
    {
        if (expr.size() != ArgNumber)
            throw new SyntaxException("FileExist accepts "+ArgNumber+" arguments.");
        _expression = expr.get(0);
    }
    @Override
    public Value interpret(IContext context)
    {
        Value result = _expression.interpret(context);
        if (result.type == Types.Path)
        {
            String path = Converter.asString(result);
            return new Value(Types.Bool, new File(path).exists());
        }
        throw new SyntaxException("FileExist is not defined for "+result.type);
    }
}
