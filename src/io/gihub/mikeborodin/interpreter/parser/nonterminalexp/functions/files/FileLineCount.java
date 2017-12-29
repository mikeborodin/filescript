package io.gihub.mikeborodin.interpreter.parser.nonterminalexp.functions.files;

import io.gihub.mikeborodin.interpreter.Types;
import io.gihub.mikeborodin.interpreter.Value;
import io.gihub.mikeborodin.interpreter.context.IContext;
import io.gihub.mikeborodin.interpreter.parser.IExpression;
import jdk.nashorn.internal.runtime.regexp.joni.exception.SyntaxException;

import java.io.*;
import java.util.List;

public class FileLineCount implements IExpression {
    private final int ArgNumber = 1;
    private IExpression _expression;
    public FileLineCount(List<IExpression> expr)
    {
        if (expr.size() != ArgNumber)
            throw new SyntaxException("FileExist accepts "+ArgNumber+" arguments.");
        _expression = expr.get(0);
    }
    @Override
    public Value interpret(IContext context)
    {
        Value result = _expression.interpret(context);
        String path = (String) result.data;

        if (result.type == Types.Path)
        {
            // This will reference one lines at a time
            long lineCount = lineCount(new File(path));
            return new Value(Types.Number,(int)lineCount);
        }
        throw new SyntaxException("FileExist is not defined for "+result.type);
    }

    public static long lineCount(File path) {
        String lines = null;
        int lineCount = 0;

        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader =
                    new FileReader(path);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader =
                    new BufferedReader(fileReader);

            while((lines = bufferedReader.readLine()) != null) {
                lineCount++;
            }

            // Always close files.
            bufferedReader.close();
            return lineCount;
        }
        catch(FileNotFoundException ex) {
            System.out.println(
                    "Unable to open file '" + path + "'");
        }
        catch(IOException ex) {
            System.out.println(
                    "Error reading file '" + path + "'");
            // Or we could just do this:
            ex.printStackTrace();
        }
        return 0;
    }
}
