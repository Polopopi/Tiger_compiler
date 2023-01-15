package ast;
import java.util.ArrayList;

public class DeclarationList extends Ast {
    public <T> T accept(AstVisitor<T> visitor){
        return visitor.visit(this);
    }

    public ArrayList<Ast> listAst;

    public DeclarationList(int lineNumber){
        super(lineNumber);
        this.listAst = new ArrayList<>();
    }

    public void addDecla(Ast declaration){
        this.listAst.add(declaration);
    }
}