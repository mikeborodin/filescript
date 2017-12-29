package io.gihub.mikeborodin.interpreter.parser.statements;

import io.gihub.mikeborodin.interpreter.Converter;
import io.gihub.mikeborodin.interpreter.Types;
import io.gihub.mikeborodin.interpreter.Value;
import io.gihub.mikeborodin.interpreter.context.IContext;
import io.gihub.mikeborodin.interpreter.parser.IExpression;
import io.gihub.mikeborodin.interpreter.parser.IStatement;
import jdk.nashorn.internal.runtime.regexp.joni.exception.SyntaxException;

public class If implements IStatement {
    private IExpression _expression;
    private IStatement _statement;

    public If(IExpression expression, IStatement statement)
    {
        _expression = expression;
        _statement = statement;
    }
    @Override
    public void execute(IContext context)
    {
        Value result = _expression.interpret(context);
        if (result.type == Types.Bool)
        {
            if (Converter.asBoolean(result))
            {
                _statement.execute(context);
            }
        }
        else
        {
            throw new SyntaxException("If expressions are required to be bool, not "+result.type);
        }

    }
}
