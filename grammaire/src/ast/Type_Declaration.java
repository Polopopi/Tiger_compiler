package ast;

public class Type_Declaration implements Ast {
    public <T> T accept(AstVisitor<T> visitor){
        return visitor.visit(this);
    }

    public Ast type_id;
    public Ast type;

    public Type_Declaration(Ast type_id, Ast type){
        this.type_id = type_id;
        this.type = type;
    }
}
