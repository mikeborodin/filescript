package io.gihub.mikeborodin.interpreter.parser.terminalexp;

import io.gihub.mikeborodin.interpreter.Converter;
import io.gihub.mikeborodin.interpreter.Types;
import io.gihub.mikeborodin.interpreter.Value;
import io.gihub.mikeborodin.interpreter.context.IContext;
import io.gihub.mikeborodin.interpreter.parser.IExpression;
import jdk.nashorn.internal.runtime.regexp.joni.exception.SyntaxException;

public class ArrayElementAccess implements IExpression {

    private IExpression index;
    private IExpression array;

    public ArrayElementAccess(IExpression _index, IExpression _array)
    {
        index = _index;
        array = _array;
    }
    @Override
    public Value interpret(IContext context)
    {
        Value arr = array.interpret(context);
        Value ind = index.interpret(context);
        if (arr.type == Types.Array && ind.type == Types.Number)
        {
            //TODO:
            return Converter.<Value>asList(arr)
                    .get(Converter.asInt(ind));
        }
        throw new SyntaxException("ARRAYACCESS does not support "+arr.type+"["+ind.type+"]");
    }
}
