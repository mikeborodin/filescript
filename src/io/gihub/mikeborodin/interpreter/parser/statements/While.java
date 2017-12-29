package io.gihub.mikeborodin.interpreter.parser.statements;

import io.gihub.mikeborodin.interpreter.Converter;
import io.gihub.mikeborodin.interpreter.Types;
import io.gihub.mikeborodin.interpreter.Value;
import io.gihub.mikeborodin.interpreter.context.IContext;
import io.gihub.mikeborodin.interpreter.parser.IExpression;
import io.gihub.mikeborodin.interpreter.parser.IStatement;
import jdk.nashorn.internal.runtime.regexp.joni.exception.SyntaxException;

public class While implements IStatement {
    private IExpression expression;
    private IStatement statement;

    public While(IExpression expression, IStatement statement) {
        this.expression = expression;
        this.statement = statement;
    }

    @Override
    public void execute(IContext context)
    {
        Value result = expression.interpret(context);
        if (result.type == Types.Bool)
        {
            while (Converter.asBoolean(result))
            {
                statement.execute(context);
                result = expression.interpret(context);
            }
        }
        else
        {
            throw new SyntaxException("If expressions are required to be bool, not "+result.type);
        }

    }
}
