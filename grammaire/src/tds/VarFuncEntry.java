package tds;

public abstract class VarFuncEntry extends Entry {

    private String type;
    private int deplacement;

    public VarFuncEntry(String type, String symbol){
        super(symbol);
        this.type = type;
    }
    
    public String getType(){
        return(this.type);
    }

    public void setDeplacement(int deplacement) {
        this.deplacement = deplacement;
    }

    public void setType(String type){
        this.type = type;
    }

    public int getDeplacement() {
        return deplacement;
    }

    public boolean isFunction(){
        return false;
    }
    public boolean isVariable(){
        return false;
    }
}
