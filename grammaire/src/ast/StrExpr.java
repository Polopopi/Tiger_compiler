package ast;

public class StrExpr implements Ast {
    public <T> T accept(AstVisitor<T> visitor){
        return visitor.visit(this);
    }

    public String value;

    public StrExpr(String value){
        this.value = value;
    }
}
