package ast;

public class IfThenElse extends Ast{

    // Utile pour la dernière partie
    public <T> T accept(AstVisitor<T> visitor){
        return visitor.visit(this);
    }

    public Ast condition;
    public Ast thenBlock;
    public Ast elseBlock; 

    public IfThenElse(int lineNumber, Ast condition, Ast thenBlock, Ast elseBlock){
        super(lineNumber);
        this.condition = condition;
        this.thenBlock = thenBlock;
        this.elseBlock = elseBlock;
    }
    
}
