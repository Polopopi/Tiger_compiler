package tds;

public abstract class Entry {
    private String symbol;

    public Entry(String symbol){
        this.symbol = symbol;
    }
    
    public String getSymbol(){
        return this.symbol;
    }


    public abstract void print();
}
