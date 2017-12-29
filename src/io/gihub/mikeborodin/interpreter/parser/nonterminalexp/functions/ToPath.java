package io.gihub.mikeborodin.interpreter.parser.nonterminalexp.functions;

import io.gihub.mikeborodin.interpreter.Converter;
import io.gihub.mikeborodin.interpreter.Types;
import io.gihub.mikeborodin.interpreter.Value;
import io.gihub.mikeborodin.interpreter.context.IContext;
import io.gihub.mikeborodin.interpreter.parser.IExpression;
import jdk.nashorn.internal.runtime.regexp.joni.exception.SyntaxException;

import java.util.List;

public class ToPath implements IExpression {
    private final int ArgNumber = 1;
    private IExpression _expression;

    public ToPath(List<IExpression> expr)
    {
        if (expr.size() != ArgNumber)
            throw new SyntaxException("ToPath accepts "+ArgNumber+" arguments.");
        _expression = expr.get(0);
    }
    @Override
    public Value interpret(IContext context)
    {
        Value result = _expression.interpret(context);
        if (result.type == Types.String)
        {
            return new Value(Types.Path, Converter.asString(result));
        }
        throw new SyntaxException("ToPath is not defined for "+result.type);
    }
}
