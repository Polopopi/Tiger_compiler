package ast;

public class Type_Field extends Ast {
    public <T> T accept(AstVisitor<T> visitor){
        return visitor.visit(this);
    }
    
    public Ast type_id;
    public Ast id;

    public Type_Field(Ast type_id, Ast id){
        this.type_id = type_id;
        this.id = id;
    }
}
