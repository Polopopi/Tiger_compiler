package tds;

public abstract class Entry {
    private String symbol;
    private int size;

    public Entry(String symbol, int size){
        this.symbol = symbol;
        this.size = size;
    }

    public Entry(String symbol){
        this.symbol = symbol;
    }
    
    public String getSymbol(){
        return this.symbol;
    }

    public int getSize(){
        return this.size;
    }

    public void setSize(int size){
        this.size = size;
    }
}
