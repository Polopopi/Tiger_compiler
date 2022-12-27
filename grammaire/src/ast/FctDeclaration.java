package ast;

public class FctDeclaration extends Ast {
    public <T> T accept(AstVisitor<T> visitor){
        return visitor.visit(this);
    }

    public Ast fonctionID;
    public Ast typeField;
    public Ast fct2Declaration;

    public FctDeclaration(Ast fonctionID, Ast typeField, Ast fct2Declaration){
        this.fonctionID=fonctionID;
        this.typeField=typeField;
        this.fct2Declaration=fct2Declaration;   
    }
}
