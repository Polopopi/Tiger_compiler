package tds;

public class Parameter {
    private String type;
    private int size;

    public Parameter(String type, int size){
        this.type = type;
        this.size = size;
    }

    public int getSize(){
        return this.size;
    }

    public String getType(){
        return this.type;
    }
}
