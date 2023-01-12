package ast;

public class TypeRecord implements Ast{
    public <T> T accept(AstVisitor<T> visitor){
        return visitor.visit(this);
    }
    
    public Ast fields;

    public TypeRecord(Ast fields){
        this.fields =fields;
    }
}
