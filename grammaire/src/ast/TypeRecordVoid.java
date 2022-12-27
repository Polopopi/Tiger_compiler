package ast;

public class TypeRecordVoid extends Ast{
    public <T> T accept(AstVisitor<T> visitor){
        return visitor.visit(this);
    }
    
}
