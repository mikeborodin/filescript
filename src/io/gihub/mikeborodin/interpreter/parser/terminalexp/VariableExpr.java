package io.gihub.mikeborodin.interpreter.parser.terminalexp;

import io.gihub.mikeborodin.interpreter.Value;
import io.gihub.mikeborodin.interpreter.context.IContext;
import io.gihub.mikeborodin.interpreter.parser.IExpression;

public class VariableExpr implements IExpression {

    private String name;

    public VariableExpr(String _name) { name = _name;}

    public String getName()
    { return name; }

    @Override
    public Value interpret(IContext context)
    {
        return context.lookUpVariable(name);
    }
}
