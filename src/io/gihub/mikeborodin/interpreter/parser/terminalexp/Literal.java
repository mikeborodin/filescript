package io.gihub.mikeborodin.interpreter.parser.terminalexp;

import io.gihub.mikeborodin.interpreter.Types;
import io.gihub.mikeborodin.interpreter.Value;
import io.gihub.mikeborodin.interpreter.context.IContext;
import io.gihub.mikeborodin.interpreter.lexer.TokenTypes;
import io.gihub.mikeborodin.interpreter.parser.FileHelpers;
import io.gihub.mikeborodin.interpreter.parser.IExpression;
import jdk.nashorn.internal.runtime.regexp.joni.exception.SyntaxException;

public class Literal implements IExpression {
    private String value;
    private TokenTypes type;

    public Literal(TokenTypes _type, String _value)
    {
        value = _value;
        type = _type;
    }
    @Override
    public Value interpret(IContext context)
    {
        if (type == TokenTypes.BoolLiteral)
        {
            if (value == "true")
            {
                return new Value(Types.Bool, true);
            }
            if (value == "false")
            {
                return new Value(Types.Bool, false);
            }
            throw new SyntaxException("Unknown boolean literal "+value);
        }
        if (type == TokenTypes.StringLiteral)
        {
            return new Value(Types.String, value);
        }
        if (type == TokenTypes.IntegerLiteral)
        {
            return new Value(Types.Number, Integer.parseInt(value));
        }
        if (type == TokenTypes.PathLiteral)
        {
            if (FileHelpers.IsValidPath(value))
            {
                if (FileHelpers.isAbsolute(value))
                {
                    return new Value(Types.Path, value);
                }
                return new Value(Types.Path, FileHelpers.createAbsolutePath(
                        context.getCurrentPath(), value));
            }
        }
        throw new SyntaxException(type+" is not literal.");
    }
}
