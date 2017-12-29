package io.gihub.mikeborodin.interpreter.parser;

import io.gihub.mikeborodin.interpreter.context.IContext;

public interface IStatement {
    void execute(IContext context);
}
