package tds;

public class FieldEntry {
    private String fieldName;
    private String type;
    //private int size;

    public FieldEntry(String fieldName, String type){
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
    public int getDeplacement(){//to do
        return 4;
    }

}
