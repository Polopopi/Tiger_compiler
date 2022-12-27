package ast;
import java.util.ArrayList;

public class DeclarationList implements Ast {
    public <T> T accept(AstVisitor<T> visitor){
        return visitor.visit(this);
    }

    public ArrayList<Ast> listAst;

    public DeclarationList(){
        this.listAst = new ArrayList<>();
    }

    public void addDecla(Ast declaration){
        this.listAst.add(declaration);
    }
}