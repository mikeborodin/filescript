package io.gihub.mikeborodin.interpreter.parser.nonterminalexp.unary;

import io.gihub.mikeborodin.interpreter.Converter;
import io.gihub.mikeborodin.interpreter.Types;
import io.gihub.mikeborodin.interpreter.Value;
import io.gihub.mikeborodin.interpreter.context.IContext;
import io.gihub.mikeborodin.interpreter.parser.IExpression;
import jdk.nashorn.internal.runtime.regexp.joni.exception.SyntaxException;

public class Not implements IExpression {
    private IExpression expr;

    public Not(IExpression expr)
    {
        this.expr = expr;
    }

    @Override
    public Value interpret(IContext context)
    {
        Value result = expr.interpret(context);
        if (result.type == Types.Bool)
        {
            return new Value(Types.Bool, !Converter.asBoolean(result));
        }
        throw new SyntaxException(result.type+"don't have NOT operation.");
    }
}
