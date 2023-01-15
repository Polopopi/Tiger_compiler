package tds;
import ast.Ast;

public class LaterVerifRecord extends LaterVerifType{
    private RecordEntry recordEntry;
    private String fieldId;

    public LaterVerifRecord(RecordEntry recordEntry, String fieldId, Ast typeAst, Tds tds){
        super(typeAst, tds);
        this.recordEntry = recordEntry;
        this.fieldId = fieldId;
    }

    public RecordEntry getTypeEntry(){
        return recordEntry;
    }

    @Override
    public void check(TdsCreator creator){
        creator.setNameIdf(true);
        String type = this.getTypeAst().accept(creator);
        creator.setNameIdf(false);
        if (this.getTds().existType(type)){
            String typeAlias = this.getTds().getTypeEntry(type).getSymbol();
            recordEntry.getField(fieldId).setType(typeAlias);
        }
        else{
            recordEntry.removeField(fieldId);
        }
    }
}
