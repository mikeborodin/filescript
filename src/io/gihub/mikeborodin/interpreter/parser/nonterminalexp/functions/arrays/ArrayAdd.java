package io.gihub.mikeborodin.interpreter.parser.nonterminalexp.functions.arrays;

import io.gihub.mikeborodin.interpreter.Converter;
import io.gihub.mikeborodin.interpreter.Types;
import io.gihub.mikeborodin.interpreter.Value;
import io.gihub.mikeborodin.interpreter.context.IContext;
import io.gihub.mikeborodin.interpreter.parser.IExpression;
import jdk.nashorn.internal.runtime.regexp.joni.exception.SyntaxException;

import java.util.List;

public class ArrayAdd implements IExpression {
    private final int ArgNumber = 2;
    private IExpression _array;
    private IExpression _expr;

    public ArrayAdd(List<IExpression> expr)
    {
        if (expr.size() != ArgNumber)
            throw new SyntaxException("ARRAY ADD accepts "+ArgNumber+" arguments.");
        _array = expr.get(0);
        _expr = expr.get(1);
    }

    @Override
    public Value interpret(IContext context)
    {
        Value result1 = _array.interpret(context);
        Value result2 = _expr.interpret(context);

        if (result1.type == Types.Array)
        {
            Converter.<Value>asList(result1).add(new Value(result2.type,result2.data));
        }
        else
        {
            throw new SyntaxException("ARRAYADD does not support "+result1.type+".");
        }
        return null;
    }
}
