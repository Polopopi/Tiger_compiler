package tds;

public abstract class VarFuncEntry extends Entry {

    private String type;

    public VarFuncEntry(String type, String symbol, int size){
        super(symbol,size);
        this.type = type;
    }
    
    public String getType(){
        return(this.type);
    }

    public void setType(String type){
        this.type = type;
    }

    public boolean isFunction(){
        return false;
    }
    public boolean isVariable(){
        return false;
    }
}
