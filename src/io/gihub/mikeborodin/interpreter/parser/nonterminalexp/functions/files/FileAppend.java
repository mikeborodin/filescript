package io.gihub.mikeborodin.interpreter.parser.nonterminalexp.functions.files;

import io.gihub.mikeborodin.interpreter.Types;
import io.gihub.mikeborodin.interpreter.Value;
import io.gihub.mikeborodin.interpreter.context.IContext;
import io.gihub.mikeborodin.interpreter.parser.IExpression;
import jdk.nashorn.internal.runtime.regexp.joni.exception.SyntaxException;

import java.io.*;
import java.util.List;

public class FileAppend implements IExpression {
    private final int ArgNumber = 2;
    private IExpression _file1Expr;
    private IExpression _file2Expr;

    public FileAppend(List<IExpression> expr)
    {
        if (expr.size() != ArgNumber)
            throw new SyntaxException("FileWrite accepts "+ArgNumber+" arguments.");
        _file1Expr = expr.get(0);
        _file2Expr = expr.get(1);
    }
    @Override
    public Value interpret(IContext context)
    {
        Value result1 = _file1Expr.interpret(context);
        Value result2 = _file2Expr.interpret(context);

        String file1Name = (String) result1.data;
        String file2Name = (String) result2.data;

        if (result1.type == Types.Path  && result2.type == Types.Path)
        {
            // This will reference one lines at a time
            String lines = null;
            StringBuilder builder = new StringBuilder();

            try {
                // FileReader reads text files in the default encoding.
                FileReader fileReader =
                        new FileReader(file1Name);

                // Always wrap FileReader in BufferedReader.
                BufferedReader bufferedReader =
                        new BufferedReader(fileReader);

                while((lines = bufferedReader.readLine()) != null) {
                    builder.append(lines);
                }

                // Always close files.
                bufferedReader.close();
            }
            catch(FileNotFoundException ex) {
                System.out.println(
                        "Unable to open file '" + file1Name + "'");
            }
            catch(IOException ex) {
                System.out.println(
                        "Error reading file '" + file1Name + "'");
                // Or we could just do this:
                ex.printStackTrace();
            }


            String file1Content = builder.toString();


            try {
                // Assume default encoding.
                FileWriter fileWriter =
                        new FileWriter(file2Name,true);

                // Always wrap FileWriter in BufferedWriter.
                BufferedWriter bufferedWriter =
                        new BufferedWriter(fileWriter);

                bufferedWriter.write(file1Content);

                // Always close files.
                bufferedWriter.close();
            }
            catch(IOException ex) {
                System.out.println(
                        "Error writing to file '"
                                + file2Name + "'");
                // Or we could just do this:
                ex.printStackTrace();
            }

        }
        return null;
    }
}
