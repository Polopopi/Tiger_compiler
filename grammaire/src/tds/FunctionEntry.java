package tds;

import java.util.ArrayList;

public class FunctionEntry extends VarFuncEntry{
    private ArrayList<Parameter> parameters;
    private Tds tds;
    

    public FunctionEntry(String type, String symbol){
        super(type, symbol);
        this.parameters=new ArrayList<Parameter>();
    }

    public void setTds(Tds tds){
        this.tds = tds;
    }

    public Tds getTds(){
        return(tds);
    }

    public void addParameter(Parameter parameter){
        this.parameters.add(parameter);
    }

    public ArrayList<Parameter> getParameters(){
        return parameters;
    }

    public int getNumberOfParameters(){
        return this.parameters.size();
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
        if (this.getType().equals("")){
            System.out.printf("| PROC   | Id : %-15s | Param :  %-15s", this.getSymbol(), this.getParameters().size());
        }
        else{
            System.out.printf("| FUNC   | Id : %-15s | Retour : %-15s | Param :  %-15s", this.getSymbol(), this.getType(), this.getParameters().size());
        }
        for (Parameter param : parameters){
            System.out.printf(" | %-15s : %-15s", param.getSymbole(), param.getType());
        }
        System.out.printf(" |\n");
    }
}