package ast;

public class Type_Declaration extends Ast {
    public <T> T accept(AstVisitor<T> visitor){
        return visitor.visit(this);
    }

    public Ast type_id;
    public Ast type;

    public Type_Declaration(int lineNumber, Ast type_id, Ast type){
        super(lineNumber);
        this.type_id = type_id;
        this.type = type;
    }
}
