package ast;

public interface Ast {

    public abstract <T> T accept(AstVisitor<T> visitor);
    
}