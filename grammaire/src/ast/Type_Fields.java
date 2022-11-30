package ast;

public class Type_Fields implements Ast {
    public <T> T accept(AstVisitor<T> visitor){
        return visitor.visit(this);
    }
    public Ast type_field;
    public Ast type_fields2;

    public Type_Fields(Ast type_field, Ast type_fields2){
        this.type_field = type_field;
        this.type_fields2 = type_fields2;
    }
}
