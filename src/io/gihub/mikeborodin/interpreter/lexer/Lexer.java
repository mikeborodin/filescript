package io.gihub.mikeborodin.interpreter.lexer;

import jdk.nashorn.internal.runtime.regexp.joni.exception.SyntaxException;

import java.util.Map;
import java.util.Objects;

public class Lexer implements ILexer{

    private int currentIndex = 0;
    private String code;

    public Lexer(String _code)
    {
        code = _code;
    }

    private void parseWhiteSpace()
    {
        while (currentIndex < code.length() && Character.isWhitespace(code.charAt(currentIndex)))
        {
            currentIndex++;
        }
    }


    @Override
    public Token getNextToken() {
        parseWhiteSpace();
        if (currentIndex >= code.length()) {
            return new Token(TokenTypes.EOF);
        }
        Character current = code.charAt(currentIndex);
        if (current == LanguageSyntax.Terminator.charAt(0)) {
            currentIndex++;
            return new Token(TokenTypes.Terminator);
        }
        if (current == LanguageSyntax.StringDelimiter.charAt(0))
        {
            return parseStringLiteral();
        }
        if (current == LanguageSyntax.PathDelimiter.charAt(0))
        {
            return ParseStringLiteral(true);
        }
        if (Character.isLetter(current))
        {
            return ParseIdentifier();
        }
        if (Character.isDigit(current) ||
                (current == '-' && Character.isDigit(code.charAt(currentIndex + 1))))
        {

            return ParseNumber();
        }
        if (LanguageSyntax.OperatorSymbols.contains(current.toString()))
        {
            return parseSymbols();
        }
        throw new SyntaxException("Wrong symbol");
    }
    private Token parseStringLiteral(){
       return ParseStringLiteral(false);
    }
    private Token ParseStringLiteral(boolean isPath)
    {
        currentIndex++;
        StringBuilder newLiteral = new StringBuilder();
        try
        {
            while (code.charAt(currentIndex) != (isPath ?
                    LanguageSyntax.PathDelimiter.charAt(0) : LanguageSyntax.StringDelimiter.charAt(0)))
            {
                newLiteral.append(code.charAt(currentIndex));
                currentIndex++;
            }
        }
        catch (IndexOutOfBoundsException e)
        {
            throw new SyntaxException("Unenclosed String/path literal");
        }
        currentIndex++;
        return new Token(
                (isPath ? TokenTypes.PathLiteral : TokenTypes.StringLiteral),
                newLiteral.toString()
        );
    }

    private Token ParseIdentifier()
    {
        StringBuilder newLiteral = new StringBuilder();

        while (currentIndex < code.length() && Character.isLetter(code.charAt(currentIndex)))
        {
            newLiteral.append(code.charAt(currentIndex));
            currentIndex++;
        }

        String literalString = newLiteral.toString().toLowerCase();

        if (Objects.equals(literalString, LanguageSyntax.While))
        {
            return new Token(TokenTypes.While);
        }

        /*Added*/
        if (Objects.equals(literalString, LanguageSyntax.For))
        {
            return new Token(TokenTypes.For);
        }

        /*Thread as Statement*/
        if (Objects.equals(literalString, LanguageSyntax.Thread))
        {
            return new Token(TokenTypes.Thread);
        }

        if (Objects.equals(literalString, LanguageSyntax.If))
        {
            return new Token(TokenTypes.If);
        }
        TokenTypes typeId = LanguageSyntax.TypeIdentifiers.get(literalString);
        if (typeId != null)
        {
            return new Token(typeId);
        }


        for (String type : LanguageSyntax.BoolValues)
        {
            if (Objects.equals(literalString, type))
            {
                return new Token(
                        TokenTypes.BoolLiteral,
                        type
                );
            }
        }
        return new Token(
                TokenTypes.Identifier,
                literalString
        );
    }
    
    private Token ParseNumber()
    {
        StringBuilder newLiteral = new StringBuilder();
        if (code.charAt(currentIndex) == '-')
        {
            newLiteral.append('-');
            currentIndex++;
        }
        while (currentIndex < code.length() && Character.isDigit(code.charAt(currentIndex)))
        {
            newLiteral.append(code.charAt(currentIndex));
            currentIndex++;
        }

        return new Token(
                TokenTypes.IntegerLiteral,
                newLiteral.toString()
        );
    }


    private Token parseSymbols()
    {
        String EMPTY = "";
        String oneSymbol = code.charAt(currentIndex) +"";//
        String twoSymbol = EMPTY;
        if (currentIndex < code.length() - 1)
            twoSymbol = code.substring(currentIndex, currentIndex+2);

        if (!Objects.equals(twoSymbol, EMPTY))
            for(Map.Entry<TokenTypes,String> token : LanguageSyntax.Operators.entrySet()) //
        {
            if (Objects.equals(twoSymbol, token.getValue()))
            {
                currentIndex += 2;
                return new Token(token.getKey());
            }
        }
        for(Map.Entry<TokenTypes, String> token : LanguageSyntax.Operators.entrySet())
        {
            if (Objects.equals(oneSymbol, token.getValue()))
            {
                currentIndex++;
                return new Token(token.getKey());
            }
        }
        throw new SyntaxException("Unknown operator");

    }
    

    
}
