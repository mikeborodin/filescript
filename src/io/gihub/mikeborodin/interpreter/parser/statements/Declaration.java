package io.gihub.mikeborodin.interpreter.parser.statements;

import io.gihub.mikeborodin.interpreter.Types;
import io.gihub.mikeborodin.interpreter.context.IContext;
import io.gihub.mikeborodin.interpreter.parser.IStatement;

public class Declaration implements IStatement{
    private Types type;
    private String identifier;

    public Declaration(Types _type, String _identifier)
    {
        type = _type;
        identifier = _identifier;
    }

    public Declaration(String _identifier)
    {
        identifier = _identifier;
    }


    public @Override  void execute(IContext context)
    {
        if(type==null)// when var
        {
            context.addVariable(identifier);
        }
        else if(type== Types.Array){
            context.addVariable(type,identifier);
        }

    }

    public @Override String toString()
    {
        return "Declaring variable " + identifier + " of type "+type+".";
    }
}
