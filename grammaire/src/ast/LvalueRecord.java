package ast;

public class LvalueRecord extends Ast {
    public <T> T accept(AstVisitor<T> visitor){
        return visitor.visit(this);
    }

    public Ast id;
    public Ast fieldList;
    
    public LvalueRecord(Ast id,Ast fieldList){
        this.id=id;
        this.fieldList=fieldList;
    }
}
