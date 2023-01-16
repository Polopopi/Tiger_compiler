package tds;

import java.util.ArrayList;

public class RecordEntry extends TypeEntry{
    private ArrayList<Field> fields;


    public RecordEntry(String symbol){
        super(symbol);
        fields = new ArrayList<Field>();
    }

    public RecordEntry(TypeEntry typeEntry){
        super(typeEntry);
        fields = new ArrayList<Field>();
    }

    public void addField(Field field){
        this.fields.add(field);
    }

    public Field getField(String fieldId){
        for (Field field : fields){
            if (field.getFieldName().equals(fieldId)){
                return field;
            }
        }
        return null;
    }

    public ArrayList<Field> getFields(){
        return fields;
    }

    public void removeField(String fieldId){
        for (int i = fields.size()-1; i >= 0; i--){
            if (fields.get(i).getFieldName().equals(fieldId)){
                fields.remove(i);
            }
        }
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
        System.out.printf("| RECORD | Id : %-15s | Fields : %-15s", this.getSymbol(), this.getFields().size());
        for (Field field : fields){
            System.out.printf(" | %-15s : %-15s", field.getFieldName(), field.getType());
        }
        System.out.printf(" |\n");
    }
}
