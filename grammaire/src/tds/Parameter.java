package tds;

public class Parameter {
    private String type;
    private int size;
    private String symbole;

    public Parameter(String symbole,String type, int size){
        this.type = type;
        this.size = size;
        this.symbole=symbole;
    }

    public int getSize(){
        return this.size;
    }

    public String getType(){
        return this.type;
    }
    public String getSymbole(){
        return this.symbole;
    }
}
