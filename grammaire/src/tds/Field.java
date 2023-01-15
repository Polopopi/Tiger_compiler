package tds;

public class Field {
    private String fieldName;
    private String type;
    //private int size;

    public Field(String fieldName, String type){
        this.fieldName = fieldName;
        this.type = type;
    }

    public String getType(){
        return type;
    }

    public void setType(String type){
        this.type = type;
    }

    public String getFieldName(){
        return fieldName;
    }

}
