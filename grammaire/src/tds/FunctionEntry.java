package tds;

import java.util.ArrayList;

public class FunctionEntry extends VarFuncEntry{
    private ArrayList<Parameter> parameters;
    

    public FunctionEntry(String type, String symbol,int size){
        super(type, symbol, size);
    }

    public void addParameter(Parameter parameter){
        this.parameters.add(parameter);
    }
}
