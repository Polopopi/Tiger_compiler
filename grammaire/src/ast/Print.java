package ast;

public class Print extends Ast {

    // Utile pour la dernière partie
    public <T> T accept(AstVisitor<T> visitor){
        return visitor.visit(this);
    }

    public Ast value;

    public Print(int lineNumber, Ast value){
        super(lineNumber);
        this.value = value;
    }

}
