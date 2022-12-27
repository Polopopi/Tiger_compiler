package ast;

public class TypeArray extends Ast{
    public <T> T accept(AstVisitor<T> visitor){
        return visitor.visit(this);
    }
    
    public Ast typeArray;

    public TypeArray(Ast typeArray){
        this.typeArray = typeArray;
    }
}
