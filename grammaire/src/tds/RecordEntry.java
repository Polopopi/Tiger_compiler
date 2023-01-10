package tds;

import java.util.ArrayList;

public class RecordEntry extends TypeEntry{
    private ArrayList<Field> fields;


    public RecordEntry(String symbol, int size){
        super(symbol, size);
    }

    public RecordEntry(String symbol, int size, ArrayList<String> previousAlias, String alias){
        super(symbol, size, previousAlias, alias);
    }

    public void addField(Field field){
        this.fields.add(field);
    }

}
