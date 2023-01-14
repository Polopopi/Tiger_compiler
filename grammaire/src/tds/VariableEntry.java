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
        System.out.printf("Id : %-20s | Type : %-20s\n", this.getSymbol(), this.getType());
    }
}
