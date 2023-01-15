package ast;

public class IfThen extends Ast {

    // Utile pour la dernière partie
    public <T> T accept(AstVisitor<T> visitor){
        return visitor.visit(this);
    }

    public Ast condition;
    public Ast thenBlock;

    public IfThen(int lineNumber, Ast condition, Ast thenBlock){
        super(lineNumber);
        this.condition = condition;
        this.thenBlock = thenBlock;
    }

}
