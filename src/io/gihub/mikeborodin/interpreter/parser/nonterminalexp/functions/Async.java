package io.gihub.mikeborodin.interpreter.parser.nonterminalexp.functions;

import io.gihub.mikeborodin.interpreter.Value;
import io.gihub.mikeborodin.interpreter.context.IContext;
import io.gihub.mikeborodin.interpreter.parser.IExpression;
import jdk.nashorn.internal.runtime.regexp.joni.exception.SyntaxException;

import java.util.List;

public class Async implements IExpression {
    Thread t;

    private final int ArgNumber = 1;
    private IExpression expression;
    public Async(List<IExpression> expr)
    {
        if (expr.size() != ArgNumber)
            throw new SyntaxException("FileExist accepts "+ArgNumber+" arguments.");
        expression = expr.get(0);
    }

    @Override
    public Value interpret(IContext context) {
        t = new Thread(() -> expression.interpret(context));
        t.start();
        return null;
    }
}
