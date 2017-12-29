package io.gihub.mikeborodin.interpreter.parser.nonterminalexp.functions.arrays;

import io.gihub.mikeborodin.interpreter.Converter;
import io.gihub.mikeborodin.interpreter.Types;
import io.gihub.mikeborodin.interpreter.Value;
import io.gihub.mikeborodin.interpreter.context.IContext;
import io.gihub.mikeborodin.interpreter.parser.IExpression;
import jdk.nashorn.internal.runtime.regexp.joni.exception.SyntaxException;

import java.util.List;

public class ArraySize implements IExpression {
    private final int ArgNumber = 1;
    private IExpression _array;
    public ArraySize(List<IExpression> expr)
    {
        if (expr.size() != ArgNumber)
        throw new SyntaxException(getClass().getSimpleName()+" accepts "+ArgNumber+" arguments.");
        _array = expr.get(0);
    }

    @Override
    public Value interpret(IContext context)
    {
        Value result1 = _array.interpret(context);

        if (result1.type == Types.Array)
        {
            return new Value(Types.Number, Converter.<Value>asList(result1).size());
        }
        else
        {
            throw new SyntaxException(getClass().getSimpleName()+" does not support "+result1.type+".");
        }
    }
}
