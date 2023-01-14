package tds;

public class VariableEntry extends VarFuncEntry {
    
    public VariableEntry(String type, String symbol,int size){
        super(type, symbol, size);
    }

    @Override
    public boolean isVariable(){
        return true;
    }

    public void print(){
        System.out.printf("| Id : %-15s | Type :   %-15s |\n", this.getSymbol(), this.getType());
    }
}
