package tds;

public class VariableEntry extends VarFuncEntry {
    
    public VariableEntry(String type, String symbol,int size){
        super(type, symbol, size);
    }

    @Override
    public boolean isVariable(){
        return true;
    }
}
