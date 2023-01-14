package tds;

import java.util.ArrayList;

public class FunctionEntry extends VarFuncEntry{
    private ArrayList<Parameter> parameters;
    

    public FunctionEntry(String type, String symbol,int size){
        super(type, symbol, size);
        this.parameters=new ArrayList<Parameter>();
    }

    public void addParameter(Parameter parameter){
        this.parameters.add(parameter);
    }

    public boolean isVariable(){
        return false;
    }

    public boolean isFunction(){
        return true;
    }
}
