package io.gihub.mikeborodin.interpreter.lexer;

import java.util.*;

public class LanguageSyntax {
    public static final String Terminator = ".";

    public static final Map<String, TokenTypes> TypeIdentifiers = new HashMap<String, TokenTypes>(){{
        /*put("int", TokenTypes.IntIdentifier);
        put("bool", TokenTypes.BoolIdentifier);
        put("String", TokenTypes.StringIdentifier);
        put("path", TokenTypes.PathIdentifier);*/
        put("var", TokenTypes.VarIdentifier);
        put("arr", TokenTypes.ArrayIdentifier);
    }};

    public static final String StringDelimiter = "\"";
    public static final String PathDelimiter = "\'";
    public static final List<String> BoolValues = new ArrayList<String>(){{
        add("true");
        add("false");
    }};

    public static final String OperatorSymbols = "(){}+-*/%^&|[]<>,!=";

    //Operators: (MAXIMUM LENGTH FOR OPERATORS IS 2)
    public static final Map<TokenTypes, String> Operators =
            new HashMap<TokenTypes, String>()
    {{
        put( TokenTypes.LeftBracket, "(");
        put( TokenTypes.RightBracket, ")");

        put( TokenTypes.LeftSquareBracket, "[");
        put( TokenTypes.RightSquareBracket, "]");

        put( TokenTypes.LeftSquigglyBracket, "{");
        put( TokenTypes.RightSquigglyBracket, "}");

        put( TokenTypes.Plus, "+");
        put( TokenTypes.Minus, "-");
        put( TokenTypes.Multiply, "*");
        put( TokenTypes.Divide, "/");
        put( TokenTypes.Mod, "%");
        put( TokenTypes.Assign, "=");
        put( TokenTypes.GreaterThan, ">" );
        put(TokenTypes.LessThan, "<" );
        put( TokenTypes.Comma, ",");

        put( TokenTypes.And, "&&");
        put( TokenTypes.Or, "||");
        put( TokenTypes.Not, "!");
        put( TokenTypes.Equality, "==");
        put( TokenTypes.NotEquality, "!=");
    }};


    public final static  String THISFILE = "thisfile";
    public final static  String THISDIR ="dir";


    public static final String While = "while";
    public static final String For = "for";
    public static final String If = "if";

    public static final String FuncEcho   = "out";
    public static final String FuncInput  = "input";
    public static final String FuncAsync  = "async";
    public static final String FuncToString= "string";
    public static final String FuncToPath = "file";
    public static final String FuncRun    = "run";


    public static final String FuncFileExists = "exists";
    public static final String FuncFileRead   = "read";
    public static final String FuncFileWrite   = "write";
    public static final String FuncFileRemove   = "remove";
    public static final String FuncFileCreate   = "create";
    public static final String FuncFileMkdir   = "mkdir";
    public static final String FuncFileAppend = "append";

    public static final String FileList   = "list";
    public static final String FuncLineCount   = "lines";
    public static final String FuncDirLineCount   = "dlines";


    public static final String FuncArrayFind   = "indexof";
    public static final String FuncArrayLength = "size";

    public static final String FuncArrayAdd    = "push";
    public static final String FuncArrayGet    = "get";


    public static final String Thread = "thread";
}
