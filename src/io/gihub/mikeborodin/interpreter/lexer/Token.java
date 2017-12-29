package io.gihub.mikeborodin.interpreter.lexer;

public class Token {
    public TokenTypes type;
    public  String value;

    public Token(TokenTypes type)
    {
        this.type = type;
        value = "";
    }
    public Token(TokenTypes type, String value)
    {
        this.type = type;
        this.value = value;
    }

    public @Override String toString()
    {
        StringBuilder str = new StringBuilder();
        str.append('<');
        str.append(type.toString());
        if (value.equals(""))
        {
            str.append(", ").append(value);
        }
        str.append('>');
        return str.toString();
    }
}
