package tds;

import java.util.ArrayList;

public class RecordEntry extends TypeEntry{
    private ArrayList<Field> fields;


    public RecordEntry(String symbol){
        super(symbol);
    }

    public RecordEntry(TypeEntry typeEntry){
        super(typeEntry);
    }

    public void addField(Field field){
        this.fields.add(field);
    }

    public boolean existField(String fieldId){
        for (Field field : fields){
            if (field.getFieldName().equals(fieldId)){
                return true;
            }
        }
        return false;
    }

    public String getFieldType(String fieldId){
        for (Field field : fields){
            if (field.getFieldName().equals(fieldId)){
                return field.getType();
            }
        }
        return null;
    }

    @Override
    public boolean isRecord(){
        return true;
    }

}
