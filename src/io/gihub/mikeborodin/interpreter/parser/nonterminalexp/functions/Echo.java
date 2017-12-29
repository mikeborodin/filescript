package io.gihub.mikeborodin.interpreter.parser.nonterminalexp.functions;

import io.gihub.mikeborodin.interpreter.Converter;
import io.gihub.mikeborodin.interpreter.Types;
import io.gihub.mikeborodin.interpreter.Value;
import io.gihub.mikeborodin.interpreter.context.IContext;
import io.gihub.mikeborodin.interpreter.parser.IExpression;
import jdk.nashorn.internal.runtime.regexp.joni.exception.SyntaxException;

import java.util.List;

public class Echo implements IExpression {
    private final int argNumber = 1;
    private IExpression expression;

    public Echo(List<IExpression> expr)
    {
        if (expr.size() != argNumber)
            throw new SyntaxException("Echo accepts {argNumber} arguments.");
        expression = expr.get(0);
    }

    @Override
    public Value interpret(IContext context)
    {
        Value result = expression.interpret(context);
        if (result.type == Types.Number)
        {
            context.addToOutput(Converter.asInt(result));
        }
        else if (result.type == Types.String)
        {
            context.addToOutput(Converter.asString(result));
        }
        else if (result.type == Types.Bool)
        {
            context.addToOutput(Converter.asBoolean(result));
        }

        else
        {
            context.addToOutput((String) result.data);
        }
        return null;
    }
}
