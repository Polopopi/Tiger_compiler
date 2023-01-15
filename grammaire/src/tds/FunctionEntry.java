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

    public ArrayList<Parameter> getParameters(){
        return parameters;
    }

    public boolean existParam(String paramId){
        for (Parameter param : parameters){
            if (param.getSymbole().equals(paramId)){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isFunction(){
        return true;
    }

    public void print(){
        System.out.printf("| Id : %-15s | Retour : %-15s", this.getSymbol(), this.getType());
        for (Parameter param : parameters){
            System.out.printf(" | %-15s : %-15s", param.getSymbole(), param.getType());
        }
        System.out.printf(" |\n");
    }
}