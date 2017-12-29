package io.gihub.mikeborodin.interpreter.lexer;

public enum TokenTypes {
    EOF,
    Terminator,

    Identifier,
/*    IntIdentifier,
    BoolIdentifier,
    StringIdentifier,*/
    VarIdentifier,
    ArrayIdentifier,

    StringLiteral,
    PathLiteral,
    BoolLiteral,
    IntegerLiteral,

    LeftBracket,
    RightBracket,
    LeftSquareBracket,
    RightSquareBracket,
    LeftSquigglyBracket,
    RightSquigglyBracket,

    Comma,

    // Additive
    Plus,
    Minus,

    // Multiplicative
    Multiply,
    Divide,
    Mod,

    Assign,

    While,
    For,
    Thread,
    If,

    //logical
    And,
    Or,

    //Unary
    Not,

    //Relational
    GreaterThan,
    LessThan,

    //Equality
    Equality,
    NotEquality
}
