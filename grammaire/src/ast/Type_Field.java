package ast;

public class Type_Field implements Ast {
    public <T> T accept(AstVisitor<T> visitor){
        return visitor.visit(this);
    }
    
    public Ast type_id;
    public Idf id;

    public Type_Field(Ast type_id, Idf id){
        this.type_id = type_id;
        this.id = id;
    }
}
