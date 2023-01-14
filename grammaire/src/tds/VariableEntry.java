package tds;

public class VariableEntry extends VarFuncEntry {
    
    public VariableEntry(String type, String symbol,int size){
        super(type, symbol, size);
    }

    public boolean isVariable(){
        return true;
    }

    public boolean isFunction(){
        return false;
    }
}
