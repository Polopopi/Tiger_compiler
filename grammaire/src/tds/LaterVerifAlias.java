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
    public boolean isAliasVerif(){
        return true;
    }
        

    @Override
    public void check(TdsCreator creator){
        creator.setNameIdf(true);
        String type = this.getTypeAst().accept(creator);
        creator.setNameIdf(false);
        if (this.getTds().existType(type)){
            String typeAlias = this.getTds().getTypeEntry(type).getSymbol();
            aliasEntry.setParent(typeAlias);
        }
        else{
            System.out.println("Erreur ligne " + this.getTypeAst().lineNumber + " : le type " + type + " n'est pas défini pour le type " + aliasEntry.getSymbol());
            this.getTds().removeTypeEntry(aliasEntry);
        }
    }
}