package tds;

public abstract class Entry {
    private String symbol;
    private int size;

    public Entry(String symbol, int size){
        this.symbol = symbol;
        this.size = size;
    }
    
    public String getSymbol(){
        return this.symbol;
    }

    public int getSize(){
        return this.size;
    }
}
