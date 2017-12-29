package io.gihub.mikeborodin.interpreter.parser.nonterminalexp.logical;

import io.gihub.mikeborodin.interpreter.Converter;
import io.gihub.mikeborodin.interpreter.Types;
import io.gihub.mikeborodin.interpreter.Value;
import io.gihub.mikeborodin.interpreter.context.IContext;
import io.gihub.mikeborodin.interpreter.parser.IExpression;
import jdk.nashorn.internal.runtime.regexp.joni.exception.SyntaxException;

public class Or implements IExpression {
    private IExpression left;
    private IExpression right;

    public Or(IExpression left, IExpression right) {
        this.left = left;
        this.right = right;
    }


    @Override
    public Value interpret(IContext context) {
        Value leftResult = left.interpret(context);
        Value rightResult = right.interpret(context);

        if (leftResult.type == Types.Bool && rightResult.type == Types.Bool)
        {
            return new Value(Types.Bool, Converter.asBoolean(leftResult)
                    ||
                    Converter.asBoolean(rightResult));
        }

        throw new SyntaxException(leftResult.type+" and "+rightResult.type+" don't have OR operation.");

    }
}
