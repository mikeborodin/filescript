package io.gihub.mikeborodin.interpreter;

public class Value{
    public Types type;
    public Object data;


    public Value(Types _type, Object _data){
        type = _type;
        data = _data;
    }

    public Value(Types _type){
        type = _type;
        data = null;
    }

    void copy(Value other){
    }

    public void setData(Object _data){
        data = _data;
    }
    public Object getData(){
        return data;
    }



    public Types getType() {
        return type;
    }

    public void setType(Types type) {
        this.type = type;
    }
}