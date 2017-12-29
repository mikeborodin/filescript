package io.gihub.mikeborodin.interpreter.parser.nonterminalexp.functions;

import io.gihub.mikeborodin.interpreter.Converter;
import io.gihub.mikeborodin.interpreter.Types;
import io.gihub.mikeborodin.interpreter.Value;
import io.gihub.mikeborodin.interpreter.context.IContext;
import io.gihub.mikeborodin.interpreter.parser.IExpression;
import jdk.nashorn.internal.runtime.regexp.joni.exception.SyntaxException;

import java.util.List;

public class Input implements IExpression {
    private final int argNumber = 0;
    private IExpression expression;

    public Input(List<IExpression> expr)
    {
        if (expr.size() != argNumber)
            throw new SyntaxException("Echo accepts {argNumber} arguments.");
    }

    @Override
    public Value interpret(IContext context)
    {
        return new Value(Types.String,context.getInput());

    }
}
