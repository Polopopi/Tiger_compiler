package ast;

public class TypeRecord implements Ast{
    public <T> T accept(AstVisitor<T> visitor){
        return visitor.visit(this);
    }
    
    public Ast typeRecord;

    public TypeRecord(Ast typeRecord){
        this.typeRecord = typeRecord;
    }
}
