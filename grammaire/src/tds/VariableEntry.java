package tds;

public class VariableEntry extends VarFuncEntry {

    private boolean affectable;
    
    public VariableEntry(String type, String symbol,int size){
        super(type, symbol, size);
        affectable = true;
    }

    @Override
    public boolean isVariable(){
        return true;
    }

    public void print(){
        System.out.printf("| VAR    | Id : %-15s | Type :   %-15s |\n", this.getSymbol(), this.getType());
    }

    public boolean isAffectable(){
        return affectable;
    }

    public void setAffectable(boolean affectable){
        this.affectable = affectable;
    }
}
