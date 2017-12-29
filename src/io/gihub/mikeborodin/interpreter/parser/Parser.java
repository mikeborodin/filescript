package io.gihub.mikeborodin.interpreter.parser;

import io.gihub.mikeborodin.interpreter.Types;
import io.gihub.mikeborodin.interpreter.lexer.ILexer;
import io.gihub.mikeborodin.interpreter.lexer.LanguageSyntax;
import io.gihub.mikeborodin.interpreter.lexer.Token;
import io.gihub.mikeborodin.interpreter.lexer.TokenTypes;
import io.gihub.mikeborodin.interpreter.parser.nonterminalexp.additive.Add;
import io.gihub.mikeborodin.interpreter.parser.nonterminalexp.additive.Subtract;
import io.gihub.mikeborodin.interpreter.parser.nonterminalexp.equality.Equals;
import io.gihub.mikeborodin.interpreter.parser.nonterminalexp.equality.NotEquals;
import io.gihub.mikeborodin.interpreter.parser.nonterminalexp.functions.*;
import io.gihub.mikeborodin.interpreter.parser.nonterminalexp.functions.arrays.ArrayAdd;
import io.gihub.mikeborodin.interpreter.parser.nonterminalexp.functions.arrays.ArrayFind;
import io.gihub.mikeborodin.interpreter.parser.nonterminalexp.functions.arrays.ArrayGet;
import io.gihub.mikeborodin.interpreter.parser.nonterminalexp.functions.arrays.ArraySize;
import io.gihub.mikeborodin.interpreter.parser.nonterminalexp.functions.files.*;
import io.gihub.mikeborodin.interpreter.parser.nonterminalexp.logical.And;
import io.gihub.mikeborodin.interpreter.parser.nonterminalexp.logical.Or;
import io.gihub.mikeborodin.interpreter.parser.nonterminalexp.multiplicative.Divide;
import io.gihub.mikeborodin.interpreter.parser.nonterminalexp.multiplicative.Mod;
import io.gihub.mikeborodin.interpreter.parser.nonterminalexp.multiplicative.Multiply;
import io.gihub.mikeborodin.interpreter.parser.nonterminalexp.relational.GreaterThan;
import io.gihub.mikeborodin.interpreter.parser.nonterminalexp.relational.LessThan;
import io.gihub.mikeborodin.interpreter.parser.nonterminalexp.unary.Not;
import io.gihub.mikeborodin.interpreter.parser.statements.*;
import io.gihub.mikeborodin.interpreter.parser.statements.Thread;
import io.gihub.mikeborodin.interpreter.parser.terminalexp.ArrayElementAccess;
import io.gihub.mikeborodin.interpreter.parser.terminalexp.Literal;
import io.gihub.mikeborodin.interpreter.parser.terminalexp.VariableExpr;
import jdk.nashorn.internal.runtime.regexp.joni.exception.SyntaxException;

import java.util.ArrayList;
import java.util.List;

public class Parser {


    private ILexer lexer;
    private Token currentToken;
    private Token nextToken;

    public Parser(ILexer _lexer)
    {
        lexer = _lexer;
        Next();
    }
    private void Next()
    {
        if (nextToken == null)
        {
            currentToken = lexer.getNextToken();
            nextToken = lexer.getNextToken();
        }
        else if (nextToken.type == TokenTypes.EOF)
        {
            currentToken = nextToken;
        }
        else
        {
            currentToken = nextToken;
            nextToken = lexer.getNextToken();
        }
    }

    private Token Take()
    {
        Token token = currentToken;
        Next();
        return token;
    }
    private Token Take(TokenTypes type)
    {
        if (currentToken.type != type)
        {
            throw new SyntaxException(currentToken+" ("+currentToken.type+") is not of type "+type);
        }
        return Take();
    }
    public IStatement buildStatement()
    {
        if (currentToken.type== TokenTypes.EOF)
        {
            return null;
        }
        if (currentToken.type == TokenTypes.VarIdentifier||
            currentToken.type == TokenTypes.ArrayIdentifier)
        {
            return BuildDeclaration(currentToken.type);
        }
        if (currentToken.type == TokenTypes.Identifier){

            if (nextToken.type == TokenTypes.LeftBracket){
                return BuildInvocation();
            }
            if (nextToken.type == TokenTypes.Assign){
                return BuildAssignment();
            }
        }

        if (currentToken.type == TokenTypes.If)
        {
            return BuildIf();
        }
        if (currentToken.type == TokenTypes.While)
        {
            return BuildWhile();
        }


        /*Added For*/
        if (currentToken.type == TokenTypes.For)
        {
            return BuildFor();
        }

        /*Added Thread*/
        if (currentToken.type == TokenTypes.Thread)
        {
            return BuildThread();
        }



        if (currentToken.type == TokenTypes.LeftSquigglyBracket)
        {
            return BuildScope();
        }


        throw new SyntaxException(currentToken.type+" is not a statement.");
    }


    private IStatement BuildInvocation()
    {
        IExpression expr = ParseFunction();
        Take(TokenTypes.Terminator);
        return new Invocation(expr);
    }
    private IStatement BuildWhile()
    {
        Take(TokenTypes.While);
        Take(TokenTypes.LeftBracket);
        IExpression expr = ParseExpression();
        Take(TokenTypes.RightBracket);
        IStatement stmt = buildStatement();
        return new While(expr, stmt);
    }

    private IStatement BuildFor()
    {
        Take(TokenTypes.For);
        Take(TokenTypes.LeftBracket);

        IStatement assign = buildStatement();
        //Take(TokenTypes.Terminator);

        IExpression condition = ParseExpression();
        Take(TokenTypes.Terminator);

        IStatement increment= buildStatement();

        Take(TokenTypes.RightBracket);

        IStatement stmt = buildStatement();
        return new For(assign,condition, increment, stmt);
    }

    private IStatement BuildThread()
    {
        Take(TokenTypes.Thread);
        Take(TokenTypes.LeftBracket);
        IStatement body = buildStatement();
        Take(TokenTypes.RightBracket);
        Take(TokenTypes.Terminator);
        return new Thread(body);
    }


    private IStatement BuildScope()
    {
        List<IStatement> statements = new ArrayList<>();
        Take(TokenTypes.LeftSquigglyBracket);
        while (currentToken.type != TokenTypes.RightSquigglyBracket)
        {
            statements.add(buildStatement());
        }
        Take(TokenTypes.RightSquigglyBracket);
        return new Scope(statements);
    }
    private IStatement BuildIf()
    {
        Take(TokenTypes.If);
        Take(TokenTypes.LeftBracket);
        IExpression expr = ParseExpression();
        Take(TokenTypes.RightBracket);
        IStatement stmt = buildStatement();
        return new If(expr, stmt);
    }

    private IStatement BuildDeclaration(TokenTypes type)
    {
        Take(type);
        Token identifier = Take(TokenTypes.Identifier);
        Take(TokenTypes.Terminator);

        switch (type)
        {
            case VarIdentifier: return new Declaration(null,identifier.value);
            case ArrayIdentifier: return new Declaration(Types.Array, identifier.value);
        }
        throw new SyntaxException("InternalError");
    }

    private IStatement BuildAssignment()
    {
        Token variable = Take(TokenTypes.Identifier);
        Take(TokenTypes.Assign);
        IExpression expression = ParseExpression();
        Take(TokenTypes.Terminator);

        return new Assignment(variable.value, expression);
    }

    private IExpression ParseExpression()
    {
        return ParseLogicalExpression();
    }

    private IExpression ParseLogicalExpression()
    {
        IExpression left = ParseEqualityExpression();
        while (IsLogical())
        {
            Token op = Take();
            IExpression right = ParseEqualityExpression();
            left = CreateNewBinaryExpression(op.type, left, right);
        }
        return left;
    }
    private IExpression ParseEqualityExpression()
    {
        IExpression left = ParseRelationalExpression();
        while (IsEquality())
        {
            Token op = Take();
            IExpression right = ParseRelationalExpression();
            left = CreateNewBinaryExpression(op.type, left, right);
        }
        return left;
    }

    private IExpression ParseRelationalExpression()
    {
        IExpression left = ParseAdditiveExpression();
        while (IsRelational())
        {
            Token op = Take();
            IExpression right = ParseAdditiveExpression();
            left = CreateNewBinaryExpression(op.type, left, right);
        }
        return left;
    }
    private IExpression ParseAdditiveExpression()
    {
        IExpression left = ParseMultiplicativeExpression();
        while (IsAdditive())
        {
            Token op = Take();
            IExpression right = ParseMultiplicativeExpression();
            left = CreateNewBinaryExpression(op.type, left, right);
        }
        return left;
    }
    private IExpression ParseMultiplicativeExpression()
    {
        IExpression left = ParseUnaryExpression();
        while (IsMultiplicative())
        {
            Token op = Take();
            IExpression right = ParseUnaryExpression();
            left = CreateNewBinaryExpression(op.type, left, right);
        }
        return left;
    }
    private IExpression ParseUnaryExpression()
    {
        IExpression left;
        if (IsUnary())
        {
            Token op = Take();
            left = ParsePrimaryExpression();
            left = CreateNewUnaryExpression(op.type, left);
        }
        else
        {

            left = ParsePrimaryExpression();
        }
        return left;
    }
    private IExpression ParsePrimaryExpression()
    {
        if (currentToken.type == TokenTypes.LeftBracket)
        {
            Take(TokenTypes.LeftBracket);
            IExpression expr = ParseExpression();
            Take(TokenTypes.RightBracket);
            return expr;
        }
        if (currentToken.type == TokenTypes.Identifier)
        {
            if (nextToken.type == TokenTypes.LeftBracket)
            {
                return ParseFunction();
            }
            if (nextToken.type == TokenTypes.LeftSquareBracket)
            {
                return ParseArrayAccess();
            }
            return ParseVariableExpr();

        }

        if (currentToken.type == TokenTypes.StringLiteral ||
                currentToken.type == TokenTypes.IntegerLiteral ||
                currentToken.type == TokenTypes.PathLiteral ||
                currentToken.type == TokenTypes.BoolLiteral)
        {
            return ParseLiteral();
        }
        throw new SyntaxException("Unknown expression");
    }

    private IExpression CreateNewBinaryExpression(TokenTypes type, IExpression left, IExpression right)
    {
        switch (type)
        {
            case Plus:
                return new Add(left, right);
            case Minus:
                return new Subtract(left, right);
            case Equality:
                return new Equals(left, right);
            case NotEquality:
                return new NotEquals(left, right);
            case And:
                return new And(left, right);
            case Or:
                return new Or(left, right);
            case Divide:
                return new Divide(left, right);
            case Multiply:
                return new Multiply(left, right);
            case Mod:
                return new Mod(left, right);
            case GreaterThan:
                return new GreaterThan(left, right);
            case LessThan:
                return new LessThan(left, right);
            default:
                throw new SyntaxException(type.toString()+" is not binary operator");
        }
    }

    private IExpression CreateNewUnaryExpression(TokenTypes type, IExpression expr)
    {
        switch (type)
        {
            case Not:
                return new Not(expr);
            default:
                throw new SyntaxException(type.toString()+" is not binary operator");
        }
    }

    private IExpression ParseFunction()
    {
        Token identifier = Take(TokenTypes.Identifier);
        Take(TokenTypes.LeftBracket);
        List<IExpression> exprList = new ArrayList<>();
        while (currentToken.type != TokenTypes.RightBracket)
        {
            exprList.add(ParseExpression());
            if (currentToken.type == TokenTypes.Comma)
            {
                Take(TokenTypes.Comma);
            }
        }
        Take(TokenTypes.RightBracket);
        switch (identifier.value)
        {
            case LanguageSyntax.FuncEcho:
                return new Echo(exprList);

            case LanguageSyntax.FuncInput:
                return new Input(exprList);
            case LanguageSyntax.FuncToPath:
                return new ToPath(exprList);
            case LanguageSyntax.FuncToString:
                return new ToString(exprList);

            /*Thread*/
            case LanguageSyntax.FuncAsync:
                return new Async(exprList);
            case LanguageSyntax.FuncRun:
                return new Run(exprList);

            /*Arrays*/
            case LanguageSyntax.FuncArrayAdd:
                return new ArrayAdd(exprList);
            case LanguageSyntax.FuncArrayFind:
                return new ArrayFind(exprList);
            case LanguageSyntax.FuncArrayLength:
                return new ArraySize(exprList);
            case LanguageSyntax.FuncArrayGet:
                return new ArrayGet(exprList);

            /*Files*/
            case LanguageSyntax.FuncFileExists:
                return new FileExists(exprList);

            case LanguageSyntax.FuncFileAppend:
                return new FileAppend(exprList);


            case LanguageSyntax.FuncFileRead:
                return new FileRead(exprList);

            case LanguageSyntax.FuncFileWrite:
                return new FileWrite(exprList);

            case LanguageSyntax.FuncFileRemove:
                return new FileRemove(exprList);
            case LanguageSyntax.FuncLineCount:
                return new FileLineCount(exprList);
            case LanguageSyntax.FuncDirLineCount:
                return new FileDirLineCount(exprList);

            case LanguageSyntax.FuncFileCreate:
                return new FileCreate(exprList);

            case LanguageSyntax.FuncFileMkdir:
                return new FileMkdir(exprList);
            case LanguageSyntax.FileList:
                return new FileList(exprList);



            default:
                throw new SyntaxException("Function "+identifier.value+" does not exist.");
        }
    }
    private IExpression ParseVariableExpr()
    {
        Token tok = Take(TokenTypes.Identifier);
        return new VariableExpr(tok.value);
    }
    private IExpression ParseLiteral()
    {
        Token tok = Take();
        return new Literal(tok.type, tok.value);
    }
    private IExpression ParseArrayAccess()
    {
        IExpression array = ParseVariableExpr();
        Take(TokenTypes.LeftSquareBracket);
        IExpression expr = ParseExpression();
        Take(TokenTypes.RightSquareBracket);
        return new ArrayElementAccess(expr, array);
    }


    /* Check expression type */
    private boolean IsLogical()
    {
        switch (currentToken.type)
        {
            case And:
            case Or:
                return true;
            default:
                return false;
        }
    }
    private boolean IsEquality()
    {
        switch (currentToken.type)
        {
            case Equality:
            case NotEquality:
                return true;
            default:
                return false;
        }
    }
    private boolean IsRelational()
    {
        switch (currentToken.type)
        {
            case GreaterThan:
            case LessThan:
                return true;
            default:
                return false;
        }
    }
    private boolean IsUnary()
    {
        switch (currentToken.type)
        {
            case Not:
                return true;
            default:
                return false;
        }
    }
    private boolean IsAdditive()
    {
        switch (currentToken.type)
        {
            case Plus:
            case Minus:
                return true;
            default:
                return false;
        }
    }
    private boolean IsMultiplicative()
    {
        switch (currentToken.type)
        {
            case Divide:
            case Multiply:
            case Mod:
                return true;
            default:
                return false;
        }
    }

}
