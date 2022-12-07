package ast;

public class IdfType /*implements Ast*/{

    /*public <T> T accept(AstVisitor<T> visitor){
        return visitor.visit(this);
    }*/

    public String name;
    public IdfType(String name){
        this.name=name;
    }
    
}
