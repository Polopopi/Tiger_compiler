package ast;

public abstract class Ast {

    public Ast parent;
    public abstract <T> T accept(AstVisitor<T> visitor);
    
}