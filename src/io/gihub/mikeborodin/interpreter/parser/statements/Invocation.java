package io.gihub.mikeborodin.interpreter.parser.statements;

import io.gihub.mikeborodin.interpreter.context.IContext;
import io.gihub.mikeborodin.interpreter.parser.IExpression;
import io.gihub.mikeborodin.interpreter.parser.IStatement;

public class Invocation implements IStatement{
    private IExpression functionExpression;

    public Invocation(IExpression expr)
    {
        functionExpression = expr;
    }
    @Override
    public void execute(IContext context)
    {
        functionExpression.interpret(context);
    }
}
