package io.gihub.mikeborodin.interpreter.parser.nonterminalexp.multiplicative;

import io.gihub.mikeborodin.interpreter.Converter;
import io.gihub.mikeborodin.interpreter.Types;
import io.gihub.mikeborodin.interpreter.Value;
import io.gihub.mikeborodin.interpreter.context.IContext;
import io.gihub.mikeborodin.interpreter.parser.IExpression;
import jdk.nashorn.internal.runtime.regexp.joni.exception.SyntaxException;

public class Multiply implements IExpression{
    private IExpression left;
    private IExpression right;

    public Multiply(IExpression left, IExpression right) {
        this.left = left;
        this.right = right;
    }


    @Override
    public Value interpret(IContext context) {
        Value leftResult = left.interpret(context);
        Value rightResult = right.interpret(context);
        if (leftResult.type == Types.Number && rightResult.type == Types.Number)
        {
            return new Value(Types.Number, Converter.asInt(leftResult)
                    * Converter.asInt(rightResult));
        }
        throw new SyntaxException(leftResult.type+" and "+rightResult.type+" don't have MULIPLY operation.");    }
}
