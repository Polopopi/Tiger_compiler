package tds;

import java.util.ArrayList;

public class RecordEntry extends TypeEntry{
    private ArrayList<FieldEntry> fields;


    public RecordEntry(String symbol){
        super(symbol);
        fields = new ArrayList<FieldEntry>();
    }

    public RecordEntry(TypeEntry typeEntry){
        super(typeEntry);
        fields = new ArrayList<FieldEntry>();
    }

    public void addField(FieldEntry field){
        this.fields.add(field);
    }

    public FieldEntry getField(String fieldId){
        for (FieldEntry field : fields){
            if (field.getFieldName().equals(fieldId)){
                return field;
            }
        }
        return null;
    }

    public ArrayList<FieldEntry> getFields(){
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
        for (FieldEntry field : fields){
            if (field.getFieldName().equals(fieldId)){
                return true;
            }
        }
        return false;
    }

    public String getFieldType(String fieldId){
        for (FieldEntry field : fields){
            if (field.getFieldName().equals(fieldId)){
                return field.getType();
            }
        }
        return null;
    }

    public int getFieldDeplacement(String fieldId){
        int deplacement = 0;
        for (FieldEntry field : fields){
            if (field.getFieldName().equals(fieldId)){
                return deplacement;
            }
            deplacement++;
        }
        return -1;
    }

    @Override
    public boolean isRecord(){
        return true;
    }

    public void print(){
        System.out.printf("| RECORD | Id : %-15s | Fields : %-15s", this.getSymbol(), this.getFields().size());
        for (FieldEntry field : fields){
            System.out.printf(" | %-15s : %-15s", field.getFieldName(), field.getType());
        }
        System.out.printf(" |\n");
    }

}
