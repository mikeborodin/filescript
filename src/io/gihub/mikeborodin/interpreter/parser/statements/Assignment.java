package io.gihub.mikeborodin.interpreter.parser.statements;

import io.gihub.mikeborodin.interpreter.Value;
import io.gihub.mikeborodin.interpreter.context.IContext;
import io.gihub.mikeborodin.interpreter.parser.IExpression;
import io.gihub.mikeborodin.interpreter.parser.IStatement;
import jdk.nashorn.internal.runtime.regexp.joni.exception.SyntaxException;

public class Assignment implements IStatement{
    private IExpression rvalue;
    private String identifier;
    public Assignment(String identifier, IExpression expression)
    {
        this.rvalue = expression;
        this.identifier = identifier;
    }


    @Override
    public void execute(IContext context) {
        Value varib = context.lookUpVariable(identifier);
        Value result = rvalue.interpret(context);
        //if (varib.getType() == result.getType())
        varib.type = result.type;
        varib.data = result.data;
        /*else
        {
            throw new SyntaxException("Cannot assign "+result.getType()+" to "+varib.getType());
        }*/
    }
}
