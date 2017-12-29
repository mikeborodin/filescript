package tests;

import io.gihub.mikeborodin.console.ConsoleInputManager;
import io.gihub.mikeborodin.interpreter.Types;
import io.gihub.mikeborodin.interpreter.Value;
import io.gihub.mikeborodin.interpreter.context.Context;
import io.gihub.mikeborodin.interpreter.lexer.Lexer;
import io.gihub.mikeborodin.interpreter.parser.IStatement;
import io.gihub.mikeborodin.interpreter.parser.Parser;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;

public class MainTest {

    @Test
    public void fileCreate() {
        //Arrange
        String filename = "aaa.txt";

        File file = new File(filename);
        try {
            Files.deleteIfExists(file.toPath());
        } catch (IOException e) {
            System.out.println("File not exist,ok");
            e.printStackTrace();
        }

        String code = "var f. f = file(thisdir+\"/"+filename+"\"). write(f,\"aaaaaaa\").";
        Context context = new Context(new ConsoleInputManager(),
                FileSystems.getDefault().getPath(".").toAbsolutePath().toString()
        );
        Lexer lexer = new Lexer(code);
        Parser parser = new Parser(lexer);

        //Act
        try{
            IStatement cur = parser.buildStatement();
            while (cur!=null){
                cur.execute(context);
                cur = parser.buildStatement();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        //Assert
        Assert.assertTrue("File must be created",file.exists());
    }


    @Test
    public void fileRemove(){
        //Arrange
        String filename = "aaa.txt";

        File file = new File(filename);
        try {
            Files.deleteIfExists(file.toPath());
        } catch (IOException e) {
            System.out.println("File not exist,ok");
            e.printStackTrace();
        }

        String code = "var f.\n" +
                "f = file(thisdir+\"/"+filename+"\").\n" +
                "write(f,\"aaaaaaa\").\n" +
                "if(exists(f)){\n" +
                "    out(\"ok\").\n" +
                "}\n" +
                "\n" +
                "remove(f).";

        Context context = new Context(new ConsoleInputManager(),
                FileSystems.getDefault().getPath(".").toAbsolutePath().toString()
        );
        Lexer lexer = new Lexer(code);
        Parser parser = new Parser(lexer);

        //Act
        try{
            IStatement cur = parser.buildStatement();
            while (cur!=null){
                cur.execute(context);
                cur = parser.buildStatement();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        //Assert
        Assert.assertFalse("File must not exis",file.exists());
    }

    @Test
    public void addOperation(){
        //Arrange
        Value v1 = new Value(Types.Number,2);
        Value v2 = new Value(Types.Number,2);

        //Act

        //Assert


    }

}
