package ast;

public abstract class Ast {

    public int lineNumber;

    public Ast(int lineNumber){
        this.lineNumber = lineNumber;
    }

    public abstract <T> T accept(AstVisitor<T> visitor);

    public boolean isAffectable(){
        return(false);
    }
    
}