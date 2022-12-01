package ast;

import java.util.ArrayList;

public class RecordList implements Ast {
    public <T> T accept(AstVisitor<T> visitor){
        return visitor.visit(this);
    }
    
    public ArrayList<Ast> recordList;
    public RecordList(){
        this.recordList=new ArrayList<>();
    }
    public void addRecord(Ast record){
        this.recordList.add(record);
    }
}
