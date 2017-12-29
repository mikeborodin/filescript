package io.gihub.mikeborodin.interpreter.parser.statements;

import io.gihub.mikeborodin.interpreter.context.IContext;
import io.gihub.mikeborodin.interpreter.parser.IExpression;
import io.gihub.mikeborodin.interpreter.parser.IStatement;

public class For implements IStatement {
    private IStatement declare;
    private IStatement increment;

    private IExpression condition;
    private IStatement body;

    public For(IStatement declare,
               IExpression condition,
               IStatement increment,
               IStatement body) {

       this.declare = declare;
       this.condition= condition;
       this.increment = increment;

       this.body = body;
    }

    @Override
    public void execute(IContext context)
    {
        declare.execute(context);
        while((boolean)condition.interpret(context).data){
            body.execute(context);
            increment.execute(context);
        }
        /*if (result.type == Types.Bool)
        {
            declare.execute(context);

            while((boolean) condition.interpret(context).data)
            {
                body.execute(context);
                increment.execute(context);
            }
        }
        else
        {
            throw new SyntaxException("If expressions are required to be bool, not "+result.type);
        }*/

    }
}
