package ast;

public class Let implements Ast {
    public <T> T accept(AstVisitor<T> visitor){
        return(visitor.visit(this));
    }
}
