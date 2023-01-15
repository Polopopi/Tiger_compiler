package ast;

public class TypeRecord extends Ast{
    public <T> T accept(AstVisitor<T> visitor){
        return visitor.visit(this);
    }
    
    public Ast fields;

    public TypeRecord(int lineNumber, Ast fields){
        super(lineNumber);
        this.fields =fields;
    }
}
