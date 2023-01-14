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

    public void print(){
        System.out.printf("Id : %-20s", this.getSymbol());
        for (Field field : fields){
            System.out.printf(" | %-20s : %-20s", field.getFieldName(), field.getType());
        }
        System.out.printf("\n");
    }
}
