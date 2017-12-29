package io.gihub.mikeborodin.interpreter.parser.nonterminalexp.functions.arrays;

import io.gihub.mikeborodin.interpreter.Converter;
import io.gihub.mikeborodin.interpreter.Types;
import io.gihub.mikeborodin.interpreter.Value;
import io.gihub.mikeborodin.interpreter.context.IContext;
import io.gihub.mikeborodin.interpreter.parser.IExpression;
import jdk.nashorn.internal.runtime.regexp.joni.exception.SyntaxException;

import java.util.List;

public class ArrayGet implements IExpression {
    private final int ArgNumber = 2;
    private IExpression _array;
    private IExpression _expr;

    public ArrayGet(List<IExpression> expr)
    {
        if (expr.size() != ArgNumber)
            throw new SyntaxException("ARRAYGET accepts "+ArgNumber+" arguments.");
        _array = expr.get(0);
        _expr = expr.get(1);
    }

    @Override
    public Value interpret(IContext context)
    {
        Value inputArray = _array.interpret(context);
        Value index = _expr.interpret(context);

        if (index.type == Types.Number && inputArray.type==Types.Array)
        {
            Types type = Converter.<Value>asList(inputArray).get(0).type;
            int indx = Converter.asInt(index);
            switch (type){
                case Number:
                    return new Value(Types.Number,
                            ((Value)((List)inputArray.data).get(indx)).data);
                case String:
                    return new Value(Types.String,
                            ((Value)((List)inputArray.data).get(indx)).data);
                case Path:
                    return new Value(Types.Path,
                            ((Value)((List)inputArray.data).get(indx)).data);
            }


        }else{
            throw new SyntaxException("Array index is not an integer");
        }

        {
            throw new SyntaxException("ARRAYGET does not support "+inputArray.type+".");
        }
    }
}
