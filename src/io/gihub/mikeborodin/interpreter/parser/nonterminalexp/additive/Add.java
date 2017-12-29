package io.gihub.mikeborodin.interpreter.parser.nonterminalexp.additive;

import io.gihub.mikeborodin.interpreter.Converter;
import io.gihub.mikeborodin.interpreter.Types;
import io.gihub.mikeborodin.interpreter.Value;
import io.gihub.mikeborodin.interpreter.context.IContext;
import io.gihub.mikeborodin.interpreter.parser.IExpression;
import jdk.nashorn.internal.runtime.regexp.joni.exception.SyntaxException;

public class Add implements IExpression {
    private IExpression left;
    private IExpression right;

    public Add(IExpression left, IExpression right) {
        this.left = left;
        this.right = right;
    }

    private Value AddStrings(Value left, Value right)
    {
        return new Value(Types.String, Converter.asString(left)
                + Converter.asString(right));
    }

    private Value AddInts(Value left, Value right)
    {
        return new Value(Types.Number, Converter.asInt(left)
                + Converter.asInt(right));
    }
    @Override
    public Value interpret(IContext context) {

        Value leftResult = left.interpret(context);
        Value rightResult = right.interpret(context);
        if (leftResult.type == Types.String && rightResult.type == Types.String)
        {
            return AddStrings(leftResult, rightResult);
        }
        if (leftResult.type == Types.Number && rightResult.type == Types.Number)
        {
            return AddInts(leftResult, rightResult);
        }

        throw new SyntaxException(leftResult.type+" and "+rightResult.type+" don't have add operation.");
    }
}
