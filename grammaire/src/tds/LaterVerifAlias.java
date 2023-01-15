package tds;
import ast.Ast;

public class LaterVerifAlias extends LaterVerifType{
    private AliasEntry aliasEntry;

    public LaterVerifAlias(AliasEntry aliasEntry, Ast typeAst, Tds tds){
        super(typeAst, tds);
        this.aliasEntry = aliasEntry;
    }

    public AliasEntry getTypeEntry(){
        return aliasEntry;
    }

    @Override
    public void check(TdsCreator creator){
        String type = this.getTypeAst().accept(creator);
        if (type != null){
            String typeAlias = this.getTds().getTypeEntry(type).getSymbol();
            aliasEntry.setParent(typeAlias);
        }
        else{
            this.getTds().removeTypeEntry(aliasEntry);
        }
    }
}