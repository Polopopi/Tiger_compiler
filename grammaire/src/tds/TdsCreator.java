package tds;

import java.util.ArrayList;


import ast.*;

public class TdsCreator implements AstVisitor<String> {

    private int idCurrentTds;
    private int whileForNode;
    private boolean inFunctionDecBloc;
    private boolean inTypeDecBloc;
    private ArrayList<Tds> listeTds;
    private Entry currentEntry;
    private ArrayList<LaterVerif> verifList;
    private boolean nameIdf = false;
    private boolean inAffectation = false;
    private int isError;

    public TdsCreator(){
        this.listeTds=new ArrayList<Tds>();
        this.idCurrentTds=0;
        this.inFunctionDecBloc = false;
        this.inTypeDecBloc = false;
        this.verifList=new ArrayList<LaterVerif>();
        this.isError = 0;
    }

    public ArrayList<Tds> getTds(){
        return this.listeTds;
    }

    public Tds getCuurentTds(){
        return this.listeTds.get(idCurrentTds);
    }


    public void checkList(){
        for (LaterVerif toCheck : verifList){
            toCheck.check(this);
        }
        verifList.clear();
        inFunctionDecBloc = false;
        inTypeDecBloc = false;
    }

    public void setTds(Tds tds){
        idCurrentTds = tds.getId();
    }

    public void setNameIdf(boolean bool){
        nameIdf = bool;
    }
    
    public String visit(Idf idf){

        if (nameIdf){
            return idf.name;
        }

        // APPEL DE VARIABLE
        else if (!listeTds.get(idCurrentTds).existVarFunc(idf.name)){
            isError ++;
            System.out.println("Erreur ligne " + idf.lineNumber + " : la variable " + idf.name + " n'est pas définie");
            return null;
        }
        else if (!listeTds.get(idCurrentTds).getVarFuncEntry(idf.name).isVariable()){
            System.out.println("Erreur ligne " + idf.lineNumber + " : l'identificateur " + idf.name + " est celui d'une fonction");
            isError ++;
            return null;
        }
        else if(inAffectation && !(((VariableEntry) listeTds.get(idCurrentTds).getVarFuncEntry(idf.name)).isAffectable())){
            System.out.println("Erreur ligne " + idf.lineNumber + " : l'identificateur " + idf.name + " ne doit pas être réaffecté");
            isError ++;
            return null;
        }


        return listeTds.get(idCurrentTds).getVarFuncEntry(idf.name).getType();
    };


    public String visit(Print print){
        String parameterType = print.value.accept(this);
        if (!parameterType.equals("int")){
            System.out.println("Erreur ligne " + print.lineNumber + " : le paramètre de print est incorrect, int attendu");
            isError ++;
        }
        return("");
    };    

    public String visit(Program program){
        //AJOUTER int et string à la TDS DU PROG
        //idPrec de la 1ere tds est null
        Tds tds = new Tds(0, null);
        // Ajouter string et int mais sous quelle classe ? On en crée une nouvelle ?
        // On sait que le type existe donc bon...
        //tds.addType(new );
        listeTds.add(tds);
        idCurrentTds = tds.getId();
        program.affect.accept(this);

        for (Tds tdsBis : listeTds){
            System.out.printf("%d - %d\n", tdsBis.getImbrication(), tdsBis.getId());
            tdsBis.printTds();
            System.out.println("\n");
        }

        return "";
    };

    // Partie 1 :
    public String visit(Affect affect){

        inAffectation = true;
        String idfType = affect.idf.accept(this);
        inAffectation = false;
        String exprType = affect.expr.accept(this);

        if (idfType != null && !idfType.equals(exprType)){
            System.out.println("Erreur ligne " + affect.lineNumber + " : affectation du type " + exprType + ", " + idfType + " était attendu");
            isError ++;
        }

        return "";
    };


    public String visit(Or or){
        String leftType = or.left.accept(this);
        String rightType = or.right.accept(this);

        if (leftType != null && rightType != null && !(leftType.equals("int") && rightType.equals("int"))){
            System.out.println("Erreur ligne " + or.lineNumber + " : les opérandes de | ne sont pas des int");
            isError ++;
        }

        return "int";
    };


    public String visit(And and){
        String leftType = and.left.accept(this);
        String rightType = and.right.accept(this);

        if (leftType != null && rightType != null && !(leftType.equals("int") && rightType.equals("int"))){
            System.out.println("Erreur ligne " + and.lineNumber + " : les opérandes de & ne sont pas des int");
            isError ++;
        }

        return "int";
    };


    public String visit(Equal equal){
        String leftType = equal.left.accept(this);
        String rightType = equal.right.accept(this);

        if (leftType != null && rightType != null && !leftType.equals(rightType)){
            System.out.println("Erreur ligne " + equal.lineNumber + " : les opérandes de = ne sont pas du même type"); 
            isError ++;
        }

        return "int";
    };


    public String visit(Diff diff){
        String leftType = diff.left.accept(this);
        String rightType = diff.right.accept(this);

        if (leftType != null && rightType != null &&!leftType.equals(rightType)){
            System.out.println("Erreur ligne " + diff.lineNumber + " : les opérandes de <> ne sont pas du même type"); 
            isError ++;
        }

        return "int";
    };


    public String visit(Inf inf){
        String leftType = inf.left.accept(this);
        String rightType = inf.right.accept(this);

        if (leftType != null && rightType != null && !leftType.equals(rightType)){
            System.out.println("Erreur ligne " + inf.lineNumber + " : les opérandes de < ne sont pas du même type"); 
            isError ++;
        }

        return "int";
    };


    public String visit(Sup sup){
        String leftType = sup.left.accept(this);
        String rightType = sup.right.accept(this);

        if (leftType != null && rightType != null && !leftType.equals(rightType)){
            System.out.println("Erreur ligne " + sup.lineNumber + " : les opérandes de > ne sont pas du même type"); 
            isError ++;
        }

        return "int";
    };


    public String visit(InfEqual infEqual){
        String leftType = infEqual.left.accept(this);
        String rightType = infEqual.right.accept(this);

        if (leftType != null && rightType != null && !leftType.equals(rightType)){
            System.out.println("Erreur ligne " + infEqual.lineNumber + " : les opérandes de <= ne sont pas du même type"); 
            isError ++;
        }

        return "int";
    };


    public String visit(SupEqual supEqual){
        String leftType = supEqual.left.accept(this);
        String rightType = supEqual.right.accept(this);

        if (leftType != null && rightType != null && !leftType.equals(rightType)){
            System.out.println("Erreur ligne " + supEqual.lineNumber + " : les opérandes de >= ne sont pas du même type"); 
            isError ++;
        }

        return "int";
    };


    public String visit(Plus plus){
        String leftType = plus.left.accept(this);
        String rightType = plus.right.accept(this);

        if (leftType != null && rightType != null && !(leftType.equals("int") && rightType.equals("int"))){
            System.out.println("Erreur ligne " + plus.lineNumber + " : les opérandes de + ne sont pas des int"); 
            isError ++;
        }

        return "int";
    };


    public String visit(Minus minus){
        String leftType = minus.left.accept(this);
        String rightType = minus.right.accept(this);

        if (leftType != null && rightType != null && !(leftType.equals("int") && rightType.equals("int"))){
            System.out.println("Erreur ligne " + minus.lineNumber + " : les opérandes de - ne sont pas des int"); 
            isError ++;
        }

        return "int";
    };


    public String visit(Mult mult){
        String leftType = mult.left.accept(this);
        String rightType = mult.right.accept(this);

        if (leftType != null && rightType != null && !(leftType.equals("int") && rightType.equals("int"))){
            System.out.println("Erreur ligne " + mult.lineNumber + " : les opérandes de * ne sont pas des int"); 
            isError ++;
        }

        return "int";
    };


    public String visit(Divide divide){
        String leftType = divide.left.accept(this);
        String rightType = divide.right.accept(this);

        if (leftType != null && rightType != null && !(leftType.equals("int") && rightType.equals("int"))){
            System.out.println("Erreur ligne " + divide.lineNumber + " : les opérandes de / ne sont pas des int"); 
            isError ++;
        }

        return "int";
    };



    // Partie 2 :
    public String visit(MinusExpr minusExpr){
        String type = minusExpr.expr.accept(this);

        if (type != null && !type.equals("int")){
            System.out.println("Erreur ligne " + minusExpr.lineNumber + " : l'opérateur unaire - attends un int"); 
            isError ++;
        }

        return "int";
    };


    public String visit(IfThen ifThen){
        String condType = ifThen.condition.accept(this);
        String blocType = ifThen.thenBlock.accept(this);

        if (condType != null && !condType.equals("int")){
            System.out.println("Erreur ligne " + ifThen.lineNumber + " : la condition ne renvoie pas de int"); 
            isError ++;
        }
        if (blocType != null && !blocType.equals("")){
            System.out.println("Erreur ligne " + ifThen.lineNumber + " : le bloc then renvoie une valeur"); 
            isError ++;
        }

        return "";
    };


    public String visit(IfThenElse ifThenElse){
        String condType = ifThenElse.condition.accept(this);
        String thenBlocType = ifThenElse.thenBlock.accept(this);
        String elseBlocType = ifThenElse.elseBlock.accept(this);

        if (condType != null && !condType.equals("int")){
            System.out.println("Erreur ligne " + ifThenElse.lineNumber + " : la condition ne renvoie pas de int"); 
            isError ++;
        }
        if (thenBlocType != null && elseBlocType != null && !thenBlocType.equals(elseBlocType)){
            System.out.println("Erreur ligne " + ifThenElse.lineNumber + " : les blocs Then et Else ne renvoient pas le même type"); 
            isError ++;
        }

        return thenBlocType;
    };


    public String visit(Let let){
        int imbrication = listeTds.get(idCurrentTds).getImbrication();
        Tds tds = new Tds(imbrication + 1, listeTds.get(idCurrentTds));
        listeTds.add(tds);
        idCurrentTds = tds.getId();
        
        let.declarationList.accept(this);
        
        String seqExprType = let.seqExpr.accept(this);

        idCurrentTds = tds.getParent().getId();

        return seqExprType;
    };


    public String visit(For forNode){
        whileForNode++;
        nameIdf = true;
        String id = forNode.id.accept(this);
        nameIdf = false;
        String debutType = forNode.debut.accept(this);
        String finType = forNode.fin.accept(this);

        int imbrication = listeTds.get(idCurrentTds).getImbrication();
        Tds tds = new Tds(imbrication + 1, listeTds.get(idCurrentTds));
        listeTds.add(tds);
        idCurrentTds = tds.getId();
        VariableEntry newEntry = new VariableEntry("int",id,4);
        newEntry.setAffectable(false);
        tds.addVarFunc(newEntry);

        String blocType = forNode.bloc.accept(this);

        idCurrentTds = tds.getParent().getId();

        if ( debutType != null && finType != null && !(debutType.equals("int")) && finType.equals("int")){
            System.out.println("Erreur ligne " + forNode.lineNumber + " : les bornes ne sont pas des int"); 
            isError ++;
        }
        if(debutType != null && !blocType.equals("")){
            System.out.println("Erreur ligne " + forNode.lineNumber + " : le bloc renvoie une valeur"); 
            isError ++;
        }
        whileForNode--;
        return "";
    };


    public String visit(While whileNode){
        whileForNode++;
        String condType = whileNode.condition.accept(this);
        String blocType = whileNode.bloc.accept(this);
        whileForNode--;

        if (condType!= null && !condType.equals("int")){
            System.out.println("Erreur ligne " + whileNode.lineNumber + " : la condition ne renvoie pas de int"); 
            isError ++;
        }
        if (blocType != null && !blocType.equals("")){
            System.out.println("Erreur ligne " + whileNode.lineNumber + " : le bloc renvoie une valeur"); 
            isError ++;
        }

        return "";
    };


    //public String visit(LvalueExpr affect);
    //public String visit(LvalueExprTypeID affect);


    public String visit(BreakExpr breakExpr){
        if (whileForNode <= 0){
            System.out.println("Erreur ligne " + breakExpr.lineNumber + " : le break est en dehors d'une boucle For ou While");
            isError ++;
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
        ArrayList<LaterVerif> memory = verifList;
        verifList = new ArrayList<>();

        for (Ast dec : declarationList.listAst){
            dec.accept(this);
        }

        //VERIF RECURSIF
        checkList();

        verifList = memory;
        return "";
    };


    public String visit (ListExpr listExpr){
        ArrayList<Parameter> parameters = ((FunctionEntry) currentEntry).getParameters();
        int i = 0;
        int listExprSize=listExpr.listExpr.size();
        
        
        if (parameters.size()>0) {


            if ( listExprSize < ((FunctionEntry)currentEntry).getParameters().size()){
           
                System.out.println("Erreur ligne " + listExpr.lineNumber + " : la fonction " + currentEntry.getSymbol() + " attend " + parameters.size()+ " paramètres, mais " + listExprSize+ " paramètres sont donnés "+ (parameters.size() - listExprSize) + " paramètres sont manquants).");
                isError++;
            }
            else if(listExprSize > ((FunctionEntry)currentEntry).getParameters().size()){
                System.out.println("Erreur ligne " + listExpr.lineNumber + " : la fonction " + currentEntry.getSymbol() + " attend " + parameters.size()+ " paramètres, mais " + listExprSize+ " paramètres sont donnés.");
                isError++;
            }
            else{
                for (Ast expr : listExpr.listExpr){
                    String exprType = expr.accept(this);
                    
                    if (!parameters.get(i).getType().equals(exprType)){
                        System.out.println("Erreur ligne " + listExpr.lineNumber + " : affectation du type " + exprType + " vers le paramètre " + parameters.get(i).getSymbole() + " de type " + parameters.get(i).getType() + " pour la fonction " + currentEntry.getSymbol());
                        isError ++;
                    }
                    i++;
                }
            }
        }
        else{
            if ( listExprSize>0){
           
                System.out.println("Erreur ligne " + listExpr.lineNumber + " : la fonction " + currentEntry.getSymbol() + " n'attend pas de paramètres, mais " + listExprSize+ " paramètres sont donnés.");
                isError++;
            }
        }
        
        
        
        

        return "";
    };

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////// Attention danger : Code WTF
    
    // Partie 3 :
    public String visit(Type_Declaration type_Declaration){
        if (!inTypeDecBloc){
            inTypeDecBloc = true;
            if (inFunctionDecBloc){
                inFunctionDecBloc = false;
                checkList();
            }
        }

        Entry oldEntry = currentEntry;

        nameIdf = true;
        String type_id = type_Declaration.type_id.accept(this);
        nameIdf = false;
        currentEntry = new TypeEntry(type_id);

        if (listeTds.get(idCurrentTds).existLocalType(type_id)){
            System.out.println("Erreur ligne " + type_Declaration.lineNumber + " : le type " + type_id + " est déjà défini");
            isError ++;
        }
        
        type_Declaration.type.accept(this);

        currentEntry = oldEntry;
        return "";
    }


    public String visit(Type_Fields type_Fields){
        //ArrayList<String> fieldsId = new ArrayList<String>();
        for (Ast field : type_Fields.listAst){
            //this.verifList.add(new LaterVerif(type_id, type_Field.type_id));
            String fieldId = field.accept(this);  
            
            if (fieldId != null){
                //fieldsId.add(fieldId);
            }
        }
        return("");
    };

/*
 * type test1={ok : test2}
 * type test2={ok: test1}
 * 
 * 
 * 
 * 
 */
    public String visit(Type_Field type_Field){
        nameIdf = true;
        String type_id = type_Field.type_id.accept(this); 
        
        String id = type_Field.id.accept(this);
        nameIdf = false;
        
        if (inTypeDecBloc){
            if (((RecordEntry) currentEntry).existField(id)){
                System.out.println("Erreur ligne " + type_Field.lineNumber + " : le field " + id + " a été défini plusieurs fois pour le record " + currentEntry.getSymbol());
                isError++;
                return null;
            }
        
            ((RecordEntry) currentEntry).addField(new tds.Field(id, type_id));
            verifList.add(new LaterVerifRecord((RecordEntry)currentEntry, id, type_Field.type_id, listeTds.get(idCurrentTds)));
        }
        else if (inFunctionDecBloc){
            if (((FunctionEntry) currentEntry).existParam(id)){
                System.out.println("Erreur ligne " + type_Field.lineNumber + " : le paramètre " + id + " a été défini plusieurs fois pour la fonction " + currentEntry.getSymbol());
                isError++;
                return null;
            }

            if (!listeTds.get(idCurrentTds).existType(type_id)){
                System.out.println("Erreur ligne " + type_Field.lineNumber + " : le type " + type_id + " n'est pas défini pour la fonction " + currentEntry.getSymbol());
                isError++;
                return id;
            }

            String typeParamAlias = listeTds.get(idCurrentTds).getTypeEntry(type_id).getSymbol();
            //dans l'ancienne tds
            Parameter parameter=new Parameter(id, typeParamAlias,4);
            ((FunctionEntry) currentEntry).addParameter(parameter);
            //dans la nouvelle tds
            VariableEntry var=new VariableEntry(typeParamAlias, id, 4);
            listeTds.get(idCurrentTds).addVarFunc(var);
        }

        else{
            System.out.println("BIZARRE");
        }
        
        /*if ( !this.listeTds.get(idCurrentTds).existType(type_id) ){ 
            System.out.println("Type pas trouvé");
        }
        */
        
        return id;
    };


    public String visit(TypeType typeType){         
        nameIdf = true;
        String typeID = typeType.typeCopie.accept(this);
        nameIdf = false;
        Tds tds = listeTds.get(idCurrentTds);
        currentEntry = new AliasEntry((TypeEntry) currentEntry);
        tds.addType((AliasEntry) currentEntry);

        verifList.add(new LaterVerifAlias((AliasEntry)currentEntry, typeType.typeCopie, tds));
        return typeID;
    };


    public String visit(TypeRecord typeRecord){
        Tds tds = listeTds.get(idCurrentTds);
        currentEntry = new RecordEntry((TypeEntry) currentEntry);
        tds.addType((RecordEntry) currentEntry);
        typeRecord.fields.accept(this);

        return "";
    };


    public String visit(TypeRecordVoid typeRecordVoid){            
        Tds tds = listeTds.get(idCurrentTds);
        currentEntry = new RecordEntry((TypeEntry) currentEntry);
        tds.addType((RecordEntry) currentEntry);

        return "";
    };


    public String visit(TypeArray typeArrayy){
    // verifier les types dans Array : soit string, int ou autre déjà existants
        Tds tds = listeTds.get(idCurrentTds);
        currentEntry = new ArrayEntry((TypeEntry) currentEntry);
        tds.addType((ArrayEntry) currentEntry);
        
        nameIdf = true;
        String typeComp = typeArrayy.typeArray.accept(this);
        nameIdf = false;
        //((ArrayEntry) currentEntry).setTypeComposite(typeComp);
        verifList.add(new LaterVerifArray((ArrayEntry)currentEntry, typeArrayy.typeArray, tds));
        return ("");
    };


    public String visit(ast.Field fieldd){    
        nameIdf = true;          
        String id_f = fieldd.id.accept(this); //verres
        nameIdf = false;
        String expr_f = fieldd.expr.accept(this); //2 (int)

        if (!((RecordEntry)currentEntry).existField(id_f)){
            System.out.println("Erreur ligne " + fieldd.lineNumber + " : le field " + id_f + " n'est pas défini pour le record " + currentEntry.getSymbol());
            isError++;
            return null;
        }

        String type_id = ((RecordEntry) currentEntry).getFieldType(id_f);
    
        /*
        Il faut avoir l'idf du record actuel pcq quand on est dans le field "adiruien := 2" 
        on sait pas si on doit regarder dans cloée (erreur type) ou dans steven (tout va bien)

        type steven = {adiruin : int}; oui
        type cloée = {adiruin : string}; non

        var wenjia := steven{adiruin = 2};
        */

        if (expr_f != null && !type_id.equals(expr_f) ){
            System.out.println("Erreur ligne " + fieldd.lineNumber + " : affectation du type " + expr_f + " vers le field " + id_f + " de type " + type_id + " pour le record " + currentEntry.getSymbol());
            isError ++;
        }

        return id_f;
    };


    public String visit(FieldList fieldList){
        ArrayList<String> fieldsId = new ArrayList<String>();
        for (Ast expr : fieldList.listAst){
            String fieldId = expr.accept(this);
            if (fieldId != null){
                if (fieldsId.contains(fieldId)){
                    System.out.println("Erreur ligne " + fieldList.lineNumber + " : le field " + fieldId + " a été initialisé plusieurs fois pour le record " + currentEntry.getSymbol());
                    isError++;
                }
                fieldsId.add(fieldId);
            }
        }

        ArrayList<String> absentFields = new ArrayList<String>();
        for (Field field : ((RecordEntry)currentEntry).getFields()){
            if (!fieldsId.contains(field.getFieldName())){
                absentFields.add(field.getFieldName());
            }
        }

        if (absentFields.size() > 0){
            System.out.println("Erreur ligne " + fieldList.lineNumber + " : tous les fields du record " + currentEntry.getSymbol() + " n'ont pas été initialisés (" + absentFields.toString() + " sont manquants)");
            isError++;
        }

        return "";
    };

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////// Fin du danger le code est mieux

    // Partie 4 :
    public String visit(VarDeclaration affect){
        if (inFunctionDecBloc || inTypeDecBloc){
            checkList();
        }

        nameIdf = true;
        String id=affect.idf.accept(this);
        nameIdf = false;
        String exprType=affect.expr.accept(this);


        if(this.listeTds.get(idCurrentTds).existLocalVarFunc(id)){
            System.out.println("Erreur ligne " + affect.lineNumber + " : la variable " + id + " est déjà définie");
            isError++;
        }
        else if (exprType != null){
            VariableEntry varFuncEntryr=new VariableEntry(exprType,id,4);
            this.listeTds.get(idCurrentTds).addVarFunc(varFuncEntryr);
        }
        
        return "";
    };

    public String visit(VarDeclarationType affect){
        if (inFunctionDecBloc || inTypeDecBloc){
            checkList();
        }

        nameIdf = true;
        String id=affect.idf.accept(this);
        String type=affect.type.accept(this);
        nameIdf = false;
        String exprType=affect.expr.accept(this);

        if(this.listeTds.get(idCurrentTds).existLocalVarFunc(id)){
            System.out.println("Erreur ligne " + affect.lineNumber + " : la variable " + id + " est déjà définie");
            isError++;
        }

        if (!this.listeTds.get(idCurrentTds).existType(type)) {
            System.out.println("Erreur ligne " + affect.lineNumber + " : le type " + type + " n'est pas défini");
            isError ++;
        }

        String typeAlias = listeTds.get(idCurrentTds).getTypeEntry(type).getSymbol();

        if (exprType != null && !typeAlias.equals(exprType)){
            System.out.println("Erreur ligne " + affect.lineNumber + " : affectation du type " + exprType + " vers la variable " + id + " de type " + typeAlias);
            isError ++;
        }

        VariableEntry variableEntry=new VariableEntry(typeAlias, id, 4);
        this.listeTds.get(idCurrentTds).addVarFunc(variableEntry);
        return "";

    };


    public String visit(FctDeclaration affect){
        if (!inFunctionDecBloc){
            if (inTypeDecBloc){
                inTypeDecBloc = false;
                checkList();
                System.out.println("check");
            }
            inFunctionDecBloc = true;
        }
/* 'function' id '(' type_fields? ')' (':' type_id)? '=' expr_affect */
        nameIdf = true;
        String id=affect.fonctionID.accept(this);
        String typeId=affect.typeId.accept(this);
        nameIdf = false;

        String typeAlias = listeTds.get(idCurrentTds).getTypeEntry(typeId).getSymbol();
        FunctionEntry functionEntry=new FunctionEntry(typeAlias, id, 4);

        if(!this.listeTds.get(idCurrentTds).existType(typeId)){
            System.out.println("Erreur ligne " + affect.lineNumber + " : le type " + typeId + " n'est pas défini pour la fonction " + functionEntry.getSymbol());
            isError ++;
        }

        if(this.listeTds.get(idCurrentTds).existLocalVarFunc(id)){
            System.out.println("Erreur ligne " + affect.lineNumber + " : la fonction " + id + " est déjà définie");
            isError ++;
        }
        //nouvelle tds
        Tds tdsFonction=new Tds(this.listeTds.get(idCurrentTds).getImbrication()+1,this.listeTds.get(idCurrentTds));
        //ajout de nouvelle tds
        this.listeTds.add(tdsFonction);

        idCurrentTds = tdsFonction.getId();
        Entry oldEntry = currentEntry;
        currentEntry = functionEntry;
        
        affect.typeFields.accept(this);
    

        currentEntry = oldEntry;
        idCurrentTds = tdsFonction.getParent().getId();


        //vérification
        //this.verifList.add(new LaterVerifFunc(typeParametres, affect.typeFields)); //BIZARRE FAUDRIAT METTRE LE BLOC PAS LE  FIELD
        this.verifList.add(new LaterVerifFunc(typeAlias, affect.exprAffect, tdsFonction));
        //ajout de nouvelle fct
        this.listeTds.get(idCurrentTds).addVarFunc(functionEntry);
        return "";
    };


    public String visit(ProcDeclaration affect){
        if (!inFunctionDecBloc){
            if (inTypeDecBloc){
                inTypeDecBloc = false;
                checkList();
            }
            inFunctionDecBloc = true;
        }

        nameIdf = true;
        String id=affect.fonctionID.accept(this);
        nameIdf = false;
        FunctionEntry procEntry=new FunctionEntry("", id, 4);

        if(this.listeTds.get(idCurrentTds).existLocalVarFunc(id)){
            System.out.println("Erreur ligne " + affect.lineNumber + " : la procédure " + id + " est déjà définie pour la procédure " + procEntry.getSymbol());
            isError ++;
        }

        Tds tdsFonction=new Tds(this.listeTds.get(idCurrentTds).getImbrication()+1,this.listeTds.get(idCurrentTds));
        //ajout de nouvelle tds
        this.listeTds.add(tdsFonction);
        Entry oldEntry = currentEntry;
        currentEntry = procEntry;

        idCurrentTds = tdsFonction.getId();
        
        affect.typeFields.accept(this);

        currentEntry = oldEntry;
        idCurrentTds = tdsFonction.getParent().getId();

        verifList.add(new LaterVerifFunc("", affect.exprAffect, tdsFonction));
        this.listeTds.get(idCurrentTds).addVarFunc(procEntry);
        return "";
    };


    public String visit(Fct2Declaration affect){
        //Ajouter la Tds dans LaterVerif
        //verifList.add(new LaterVerifFunc("",affect.exprAffect));
        return ("");
    };


    public String visit(Fct2DeclarationType affect){
        nameIdf = true;
        String typeRetour=affect.typeID.accept(this);
        nameIdf = false;
        ((FunctionEntry) currentEntry).setType(typeRetour);
        //verifList.add(new LaterVerifFunc(typeRetour, affect.exprAffect));
        
        return typeRetour;
    };


    public String visit(LvalueField affect){//à modifier enattandant la fonction dans tds
        nameIdf = true;
        String id = affect.id.accept(this);
        nameIdf = false;
        String recordId = affect.left.accept(this); // type
        //VERIF ID DU FIELD DANS RECORD

        TypeEntry typeEntry = listeTds.get(idCurrentTds).getTypeEntry(recordId);
        if (typeEntry == null){
            return null;
        }
        if (!typeEntry.isRecord()){
            System.out.println("Erreur ligne " + affect.lineNumber + " : le type " + recordId + " n'est pas un Record");
            isError++;
            return null;
        }

        if (!((RecordEntry)typeEntry).existField(id)){
            System.out.println("Erreur ligne " + affect.lineNumber + " : le field " + id + " n'est pas défini pour le record " + recordId);
            isError++;
            return null;
        }

        /*lunettes.verres.marque --> lunettes.verres existe car verres a été vérif avec getrecordfieldTDS(id)
        */

        String fieldType = ((RecordEntry)typeEntry).getFieldType(id);

        return fieldType;
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
        inAffectation = false;
        String arrayId = affect.left.accept(this);
        String index=affect.exprOr.accept(this);

        //listeTds.get(idCurrentTds).printTds();
        //System.out.println(arrayId);

        TypeEntry typeEntry = listeTds.get(idCurrentTds).getTypeEntry(arrayId);

        if (typeEntry == null){
            return null;
        }

        if (!typeEntry.isArray()){
            System.out.println("Erreur ligne " + affect.lineNumber + " : Le type " + arrayId + " n'est pas un Array");
            isError++;
            return null;
        }

        if (index != null && !index.equals("int")) {
            System.out.println("Erreur ligne " + affect.lineNumber + " : type indice dans LvalueIndex");
            isError ++;
        }
            
        return ((ArrayEntry) typeEntry).getTypeComposite();           
    };


    public String visit(Array affect){//array of type à vérifier le type 
        nameIdf = true;
        String idType = affect.id.accept(this);
        nameIdf = false;
        String typeArray=affect.exprOr2.accept(this);
        String lengthArray=affect.exprOr1.accept(this);

        
        TypeEntry entry = listeTds.get(idCurrentTds).getTypeEntry(idType);

        if (entry == null){
            System.out.println("Erreur ligne " + affect.lineNumber + " : l'array "+idType+" n'est pas défini");
            isError++;
            return null;
        }
        if (!entry.isArray()){
            System.out.println("Erreur ligne " + affect.lineNumber + " : le type " + idType +" n'est pas un array");
            isError ++;
        }
        else{
            if (lengthArray != null && !lengthArray.equals("int")) {
                System.out.println("Erreur ligne " + affect.lineNumber + " : le type de l'indice pour l'array " + idType + " n'est pas un int");
                isError++;
            }
            else if (typeArray != null && !typeArray.equals(((ArrayEntry)entry).getTypeComposite())){
                System.out.println("Erreur ligne " + affect.lineNumber + " : le type attendu est " + ((ArrayEntry)entry).getTypeComposite() + ", et non " + typeArray);
                isError++;
            }
        }

        return idType;
    };


    public String visit(LvalueRecord record){
        inAffectation = false;
        nameIdf = true;
        String idType = record.id.accept(this);
        nameIdf = false;

        TypeEntry entry = listeTds.get(idCurrentTds).getTypeEntry(idType);
        if (entry == null){
            System.out.println("Erreur ligne " + record.lineNumber + " : le record "+idType+" n'est pas défini");
            isError++;
            return null;
        }
        else{
            Entry oldEntry = currentEntry;
            currentEntry = entry;
            if (!((TypeEntry) currentEntry).isRecord()){
                System.out.println("Erreur ligne " + record.lineNumber + " : le type " + idType + " n'est pas un record");
                isError++;
            }
            else{
                record.fieldList.accept(this);
            }
            currentEntry = oldEntry;

            return(idType);
        }
    };


    public String visit(Call call){
        nameIdf = true;
        String id=call.id.accept(this);
        //System.out.println(id);
        nameIdf = false;
        VarFuncEntry entry = listeTds.get(idCurrentTds).getVarFuncEntry(id);
        if (entry==null) {
            System.out.println("Erreur ligne " + call.lineNumber + " : la fonction "+id+" n'est pas définie");
            isError ++;
            return null;
        }
        else{
            Entry oldEntry = currentEntry;
            currentEntry = entry;
    
            //System.out.println(id);
            //listeTds.get(idCurrentTds).printTds();
            if (!entry.isFunction()){
                System.out.println("Erreur ligne " + call.lineNumber + " : le symbole " + id + " n'est pas une fonction");
                isError ++;
            }
            else{
                call.listExpr.accept(this);
            }
    
            currentEntry = oldEntry;
            return(entry.getType());
        }        
    };


    //public String visit(RecordList affect);




}
