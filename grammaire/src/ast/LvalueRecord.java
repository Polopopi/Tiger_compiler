package ast;

public class LvalueRecord extends Ast {
    public <T> T accept(AstVisitor<T> visitor){
        return visitor.visit(this);
    }

    public Ast id;
    public Ast fieldList;
    
    public LvalueRecord(int lineNumber, Ast id,Ast fieldList){
        super(lineNumber);
        this.id = id;
        this.fieldList = fieldList;
    }
}
