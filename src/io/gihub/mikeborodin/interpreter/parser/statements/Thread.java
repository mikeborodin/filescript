package io.gihub.mikeborodin.interpreter.parser.statements;

import io.gihub.mikeborodin.interpreter.context.IContext;
import io.gihub.mikeborodin.interpreter.parser.IExpression;
import io.gihub.mikeborodin.interpreter.parser.IStatement;

public class Thread implements IStatement {

    private IStatement body;

    public Thread(IStatement body) {
       this.body = body;
    }

    @Override
    public void execute(IContext context)
    {
        new java.lang.Thread(new Runnable() {
            @Override
            public void run() {
                body.execute(context);
            }
        }).start();
    }
}
