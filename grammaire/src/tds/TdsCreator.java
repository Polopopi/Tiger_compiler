package tds;

import java.lang.reflect.Type;
import java.util.ArrayList;

import javax.swing.CellEditor;

import ast.*;

public class TdsCreator implements AstVisitor<String> {

    private int idCurrentTds;
    private boolean whileForNode;
    private ArrayList<Tds> listeTds;
    private Entry entry;

    TdsCreator(){
        this.listeTds=new ArrayList<Tds>(5);
        this.idCurrentTds=0;
    }
    
    public String visit(Idf affect){

    };


    public String visit(Print affect){


    };


    public String visit(Program affect){
        //AJOUTER int et string à la TDS DU PROG

    };

    // Partie 1 :
    public String visit(Affect affect){


    };


    public String visit(Or or){
        String leftType = or.left.accept(this);
        String rightType = or.right.accept(this);

        if (!(leftType.equals("int") && rightType.equals("int"))){
            //ERREUR TYPE
        }

        return "int";
    };


    public String visit(And and){
        String leftType = and.left.accept(this);
        String rightType = and.right.accept(this);

        if (!(leftType.equals("int") && rightType.equals("int"))){
            //ERREUR TYPE
        }

        return "int";
    };


    public String visit(Equal equal){
        String leftType = equal.left.accept(this);
        String rightType = equal.right.accept(this);

        if (!leftType.equals(rightType)){
            //ERREUR TYPE
        }

        return "int";
    };


    public String visit(Diff diff){
        String leftType = diff.left.accept(this);
        String rightType = diff.right.accept(this);

        if (!leftType.equals(rightType)){
            //ERREUR TYPE
        }

        return "int";
    };


    public String visit(Inf inf){
        String leftType = inf.left.accept(this);
        String rightType = inf.right.accept(this);

        if (!leftType.equals(rightType)){
            //ERREUR TYPE
        }

        return "int";
    };


    public String visit(Sup sup){
        String leftType = sup.left.accept(this);
        String rightType = sup.right.accept(this);

        if (!leftType.equals(rightType)){
            //ERREUR TYPE
        }

        return "int";
    };


    public String visit(InfEqual infEqual){
        String leftType = infEqual.left.accept(this);
        String rightType = infEqual.right.accept(this);

        if (!leftType.equals(rightType)){
            //ERREUR TYPE
        }

        return "int";
    };


    public String visit(SupEqual supEqual){
        String leftType = supEqual.left.accept(this);
        String rightType = supEqual.right.accept(this);

        if (!leftType.equals(rightType)){
            //ERREUR TYPE
        }

        return "int";
    };


    public String visit(Plus plus){
        String leftType = plus.left.accept(this);
        String rightType = plus.right.accept(this);

        if (!(leftType.equals("int") && rightType.equals("int"))){
            //ERREUR TYPE
        }

        return "int";
    };


    public String visit(Minus minus){
        String leftType = minus.left.accept(this);
        String rightType = minus.right.accept(this);

        if (!(leftType.equals("int") && rightType.equals("int"))){
            //ERREUR TYPE
        }

        return "int";
    };


    public String visit(Mult mult){
        String leftType = mult.left.accept(this);
        String rightType = mult.right.accept(this);

        if (!(leftType.equals("int") && rightType.equals("int"))){
            //ERREUR TYPE
        }

        return "int";
    };


    public String visit(Divide divide){
        String leftType = divide.left.accept(this);
        String rightType = divide.right.accept(this);

        if (!(leftType.equals("int") && rightType.equals("int"))){
            //ERREUR TYPE
        }

        return "int";
    };



    // Partie 2 :
    public String visit(MinusExpr minusExpr){
        String type = minusExpr.expr.accept(this);

        if (!type.equals("int")){
            //ERREUR TYPE
        }

        return "int";
    };


    public String visit(IfThen ifThen){
        String condType = ifThen.condition.accept(this);
        String blocType = ifThen.thenBlock.accept(this);

        if (!condType.equals("int")){
            //ERREUR TYPE
        }
        if (!blocType.equals("")){
            //ERREUR TYPE
        }

        return "";
    };


    public String visit(IfThenElse ifThenElse){
        String condType = ifThenElse.condition.accept(this);
        String thenBlocType = ifThenElse.thenBlock.accept(this);
        String elseBlocType = ifThenElse.elseBlock.accept(this);

        if (!condType.equals("int")){
            //ERREUR TYPE
        }
        if (!thenBlocType.equals(elseBlocType)){
            //ERREUR TYPE
        }

        return "";
    };


    public String visit(Let let){
        int imbrication = listeTds.get(idCurrentTds).getImbrication();
        Tds tds = new Tds(imbrication + 1, idCurrentTds);
        listeTds.add(tds);
        idCurrentTds = tds.getId();
        
        let.declarationList.accept(this);
        
        String seqExprType = let.seqExpr.accept(this);

        idCurrentTds = tds.getIdParent();

        return seqExprType;
    };


    public String visit(For forNode){
        whileForNode = true;
        String id = forNode.id.accept(this);
        String debutType = forNode.debut.accept(this);
        String finType = forNode.fin.accept(this);

        int imbrication = listeTds.get(idCurrentTds).getImbrication();
        Tds tds = new Tds(imbrication + 1, idCurrentTds);
        listeTds.add(tds);
        idCurrentTds = tds.getId();
        tds.addVarFunc(new VariableEntry("int",id,4));

        String blocType = forNode.bloc.accept(this);

        idCurrentTds = tds.getIdParent();

        if (!(debutType.equals("int")) && finType.equals("int")){
            //ERREUR TYPE
        }
        if(! blocType.equals("")){
            //ERREUR TYPE
        }
        whileForNode = false;
        return "";

    };


    public String visit(While whileNode){
        whileForNode = true;
        String condType = whileNode.condition.accept(this);
        String blocType = whileNode.bloc.accept(this);
        whileForNode = false;

        if (!condType.equals("int")){
            //ERREUR TYPE
        }
        if (!blocType.equals("")){
            //ERREUR TYPE
        }

        return "";
    };


    //public String visit(LvalueExpr affect);
    //public String visit(LvalueExprTypeID affect);


    public String visit(BreakExpr breakExpr){
        if (!whileForNode){
            //ERREUR ADRIRUIEN;
        }
        return "";
    };


    public String visit(NilExpr affect){
        return "nil";
    };


    public String visit(IntExpr affect){
        return "int";
    };


    public String visit(StrExpr affect){
        return "string";
    };


    public String visit(SeqExpr seqExpr){
        String lastType = "";

        for (Ast expr:seqExpr.listExpr){
            lastType = expr.accept(this);
        }
    
        return lastType;
    };


    public String visit (DeclarationList declarationList){
        for (Ast dec : declarationList.listAst){
            dec.accept(this);
        }

        //VERIF RECURSIF

        return "";
    };


    public String visit (ListExpr listExpr){
        String res = "";
        for (Ast expr : listExpr.listExpr){
            if (!res.equals("")){
                res += ",";
            }
            res += expr.accept(this);
        }
        return(res);
    };

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////// Attention danger : Code WTF
    
    // Partie 3 :
    public String visit(Type_Declaration type_Declaration){
        String type_id = type_Declaration.type_id.accept(this);
        String type = type_Declaration.type.accept(this);

        if (type.startsWith("ArrayOf")){
            String composite = type.substring(7);
            TypeEntry typeEntry = new ArrayEntry(type_id,4,composite);
            this.listeTds.get(idCurrentTds).addType(typeEntry);         
        }
        if (type.startsWith("Record")){
            // VERIF ID FIELD UNIQUE

            String composites = type.substring(7);
            String[] list =composites.split(",");

            RecordEntry typeEntry = new RecordEntry(type_id,4);
            for (String typeName: list){
                String[] fieldArray = typeName.split(":");
                Field field = new Field(fieldArray[0], fieldArray[1], 4);
                // Je sais pas comment retrouver tous les fields deja créer ... ou si je dois créer de nouveaux ...
                typeEntry.addField(field);
            }
            this.listeTds.get(idCurrentTds).addType(typeEntry);         
        }
        else {
            //Verif le type de type copie et le copier
            TypeEntry typeEntry = new TypeEntry(type_id,4);
            this.listeTds.get(idCurrentTds).addType(typeEntry);
        }
        return "";
    };


    public String visit(Type_Fields type_Fields){
        String res = "";
        for (Ast expr : type_Fields.listAst){
            if (!res.equals("")){
                res += ",";
            }
            res += expr.accept(this);
        }
        return(res);
    };


    public String visit(Type_Field type_Field){
        String type_id = type_Field.type_id.accept(this);
        String id = type_Field.id.accept(this);
        if ( !this.listeTds.get(idCurrentTds).existType(type_id) ){
            System.out.println("Type pas trouvé");
        }
        /* je sais pas si on doit verifier si id existe deja ...  
        if ( !this.listeTds.get(idCurrentTds).existVarFunc(id) ){ 
            System.out.println("Id pas trouvé");
        }
        */
        return id + ":" + type_id;
    };


    public String visit(TypeType typeType){                        
        String typeID = typeType.typeCopie.accept(this);

        if ( !this.listeTds.get(idCurrentTds).existType(typeID) ){
            System.out.println("Type pas trouvé");
        }
        return typeID;
    };


    public String visit(TypeRecord typeRecord){
        String types = typeRecord.typeRecord.accept(this);
        String res="Record:"+types;

        return res;
    };


    public String visit(TypeRecordVoid typeRecordVoid){            
        return "Record:";
    };


    public String visit(TypeArray typeArrayy){
    // verifier les types dans Array : soit string, int ou autre déjà existants
    String type = typeArrayy.typeArray.accept(this);
    if ( !this.listeTds.get(idCurrentTds).existType(type) ){
        System.out.println("Type pas trouvé");
    }
    return "ArrayOf"+type;
    };


    public String visit(ast.Field fieldd){              
        String id_f = fieldd.id.accept(this); //verres
        String expr_f = fieldd.expr.accept(this); //2 (int)

        String type_id = this.listeTds.get(idCurrentTds).typeOfVarFunc(id_f);

        /*
        type steven := lunette{adiruin : int};
        type cloée := vélo{adiruin : string};

        var wenjia := steven{adiruin := 2};
        */

        if ( !type_id.equals(expr_f) ){
            System.out.println("Id et expr pas du meme type");
        }
        return "";
    };


    public String visit(FieldList fieldList){
        String res = "";
        for (Ast expr : fieldList.listAst){
            if (!res.equals("")){
                res += ",";
            }
            res += expr.accept(this);
        }
        return(res);
    };

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////// Fin du danger le code est mieux

    // Partie 4 :
    public String visit(VarDeclaration affect){
        String id=affect.idf.accept(this);
        String exprType=affect.expr.accept(this);
        
        VarFuncEntry varFuncEntryr=new VariableEntry(exprType,id,4);
        this.listeTds.get(idCurrentTds).addVarFunc(varFuncEntryr);
        return "";
    };

    public String visit(VarDeclarationType affect){


    };


    public String visit(FctDeclaration affect){
        String id=affect.fonctionID.accept(this);
        String typeRetor=affect.fct2Declaration.accept(this);
        String typeParametre=affect.typeField.accept(this);

        // typeParametre = "id:type,id:type....""
        // itérer
        Parameter parameter=new Parameter(typeParametre, 4);
        FunctionEntry functionEntry=new FunctionEntry(typeRetor, id, 4);
        functionEntry.addParameter(parameter);
        
        this.listeTds.get(idCurrentTds).addVarFunc(functionEntry);
        return "";
    };


    public String visit(ProcDeclaration affect){
        String id=affect.fonctionID.accept(this);
        String typeRetor=affect.fct2Declaration.accept(this);
        FunctionEntry procEntry=new FunctionEntry(typeRetor, id, 4);
        this.listeTds.get(idCurrentTds).addVarFunc(procEntry);
        return "";
    };


    public String visit(Fct2Declaration affect){
        String type = affect.exprAffect.accept(this);
        if (!type.equals("")){
            //ERREUR
        }
        return "";
    };


    public String visit(Fct2DeclarationType affect){
        String typeRetour=affect.typeID.accept(this);
        String lastType=affect.exprAffect.accept(this);
        if (!typeRetour.equals(lastType)){
            //ERREUR
        }
        
        return typeRetour;
    };


    public String visit(LvalueField affect){
        String id = affect.id.accept(this);
        String type = affect.left.accept(this);
        //VERIF ID DANS RECORD

        /*
        if ( !this.listeTds.get(idCurrentTds).existType(type) ){
            System.out.println("Erreur type dans LvalueField");
        } lunettes.verres.marque --> lunettes.verres existe car verres a été vérif avec getrecordfieldTDS(id)
        */

        String typeId = getrecordfieldTDS(id)

        return typeId;
    };
    public boolean estUnEntier(String chaine) {
		try {
			Integer.parseInt(chaine);
		} catch (NumberFormatException e){
			return false;
		}
		return true;
	}


    public String visit(LvalueIndex affect){
        String index=affect.exprOr.accept(this);

        if (!index.equals("int")) {
            System.out.println("Erreur type dans LvalueIndex");
        }
        return "";
    };


    public String visit(Array affect){//array of type à vérifier le type 
        String typeArray=affect.exprOr2.accept(this);
        String lengthArray=affect.exprOr1.accept(this);
        if (!lengthArray.equals("int")) {
            System.out.println("longueur d\'une liste erreur Array [longueur] of type");
        }
        
        return typeArray;
    };


    public String visit(LvalueRecord affect){
        String id=affect.id.accept(this);
        String type=affect.fieldList.accept(this);
        RecordEntry newRecord=new RecordEntry(id,4);
        this.listeTds.get(idCurrentTds).addType(newRecord);
        return "";
    };


    public String visit(Call affect){
        String id=affect.id.accept(this);
        String type=affect.listExpr.accept(this);
        RecordEntry newCall=new RecordEntry(id, 4);
        this.listeTds.get(idCurrentTds).addType(newCall);
        return "";

    };


    //public String visit(RecordList affect);




}
