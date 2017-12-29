package io.gihub.mikeborodin.interpreter.parser.statements;

import io.gihub.mikeborodin.interpreter.context.IContext;
import io.gihub.mikeborodin.interpreter.parser.IStatement;

import java.util.List;

public class Scope implements IStatement {
    private List<IStatement> _statements;
	public Scope(List<IStatement> statements) {
            _statements = statements;
    }


    @Override
    public void execute(IContext context)
    {
        for(IStatement stmt : _statements)
        {
            stmt.execute(context);
        }
    }
}
