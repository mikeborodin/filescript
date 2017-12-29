package io.gihub.mikeborodin.interpreter;

import java.util.List;

public class Converter {
    /*public static <T> T convert(Value variable,Class<T> tClass)
    {
        if (variable.type == Types.Number &&  variable.getClass().getName() == int.class.getName())
        {
            return (T)variable.data;
        }

        if ((variable.type == Types.String || variable.getClass().getName() == String.class.getName()))
        {
            return (T)variable.data;
        }

        if (variable.type == Types.Bool && variable.getClass().getName() == boolean.class.getName())
        {
            return (T)variable.data;
        }

        if (variable.type == Types.Array && variable.getClass().getName() == List.class.getName())
        {
            return (T)variable.data;
        }
        throw new ClassCastException("Cannot cast "+variable.data.toString()+" to "+
                tClass.getName());
    }*/


    public static boolean asBoolean(Value v){
        return (boolean)  v.data;
    }
    public static int asInt(Value v){
        return (int)  v.data;
    }
    public static float asFloat(Value v){
        return (float)  v.data;
    }


    public static String asString(Value v){

        if(v.type == Types.String || v.type == Types.Path){
            return (String) v.data;
        } else return String.valueOf(v.data);

    }
    public static <T> List<T> asList(Value v){
        return (List<T>)  v.data;
    }


}
//todo check this