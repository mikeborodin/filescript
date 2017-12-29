package io.gihub.mikeborodin.interpreter.parser;

import io.gihub.mikeborodin.interpreter.Value;
import io.gihub.mikeborodin.interpreter.context.IContext;

public interface IExpression {
    Value interpret(IContext context);
}
