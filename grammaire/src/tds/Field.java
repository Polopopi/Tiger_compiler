package tds;

public class Field {
    private String fieldName;
    private String type;
    private int size;

    public Field(String fieldName, String type, int size){
        this.fieldName = fieldName;
        this.type = type;
        this.size = size;
    }

    public String getType(){
        return type;
    }

    public String getFieldName(){
        return fieldName;
    }

    public int getSize(){
        return size;
    }
}
