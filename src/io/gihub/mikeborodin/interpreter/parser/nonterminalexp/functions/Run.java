package io.gihub.mikeborodin.interpreter.parser.nonterminalexp.functions;

import io.gihub.mikeborodin.interpreter.Converter;
import io.gihub.mikeborodin.interpreter.Types;
import io.gihub.mikeborodin.interpreter.Value;
import io.gihub.mikeborodin.interpreter.context.IContext;
import io.gihub.mikeborodin.interpreter.parser.IExpression;
import jdk.nashorn.internal.runtime.regexp.joni.exception.SyntaxException;

import java.io.IOException;
import java.util.List;

public class Run implements IExpression {
    private final int argNumber = 1;
    private IExpression expression;

    public Run(List<IExpression> expr)
    {
        if (expr.size() != argNumber)
            throw new SyntaxException("Echo accepts {argNumber} arguments.");
        expression = expr.get(0);
    }

    @Override
    public Value interpret(IContext context)
    {
        Value result = expression.interpret(context);
        if (result.type == Types.String)
        {
            try {
                return new Value(Types.String,execCmd((String) result.data));
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        else
            return null;
    }

    private String execCmd(String cmd) throws java.io.IOException {
        java.util.Scanner s = new java.util.Scanner(Runtime.getRuntime().exec(cmd).getInputStream()).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }
}
