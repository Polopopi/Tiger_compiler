package assembleur;

import ast.*;
import tds.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class AsrCreator implements AstVisitor<String> {
    private Asr asr;
    private ArrayList<Tds> listTds;
    private Tds currentTds;
    private int oldTdsId;
    private int idGenerator;
    private ArrayList<String> loopLabel;
    private TypeEntry currentTypeEntry;
    private boolean nameIdf = false;



    private Tds getTds(){
        return(listTds.get(oldTdsId++));
    }


    private int generateId(){
        return(idGenerator++);
    }

    private String generateLabel(){
        return("label"+generateId());
    }

    public AsrCreator(ArrayList<Tds> listTds){
        this.listTds = listTds;
        this.asr=new Asr();
        this.idGenerator = 0;
        this.loopLabel = new ArrayList<String>();
        this.oldTdsId = 0;
    }

    public void asrFichier(String asrFileName) throws IOException {
        ArrayList<String> data=asr.getAsr();
        Path fichier= Paths.get(asrFileName);
        Files.write(fichier,data, StandardCharsets.UTF_8);
    }


    @Override
    public String visit(Print affect) {
        asr.comment("START PRINT");

        asr.comment("END PRINT");
        return null;
    }

    @Override
    public String visit(Affect affect) {
        asr.comment("START AFFECTATION");

        affect.idf.accept(this);

        asr.mov("R1", "R10");

        asr.empiler("R1");
        affect.expr.accept(this);
        asr.depiler("R1");

        asr.str("R0", "R1");

        asr.comment("END AFFECTATION");

        return null;
    }

    @Override
    public String visit(Program program) {
        currentTds = getTds();

        String endLabel = generateLabel();

        asr.b(endLabel);

        try {
            Path path = Path.of("./src/assembleur/fonctions.S");
            String div_str = Files.readString(path);
            asr.addFunction(div_str);
        } catch (IOException e) {
            e.printStackTrace();
        }

        asr.label(endLabel);

        asr.mov("R11", "SP");

        String texte = program.affect.accept(this);

        asr.end();

        return null;
    }

    @Override
    public String visit(Or or) {
        asr.comment("START OR");
        String left = or.left.accept(this);

        asr.cmp("R0", "#0");
        asr.setRetour("NE", 1);
        String endLabel = generateLabel();
        asr.b("NE", endLabel);

        asr.empiler("R0");

        //asr.empilerFlags();
        String right = or.right.accept(this);
        //asr.depilerFlags();

        asr.depiler("R1");
        
        asr.or("R0", "R1", "R0");

        asr.label(endLabel);

        asr.comment("END OR");
        return null;
    }

    @Override
    public String visit(And and) {
        asr.comment("START AND");
        String left = and.left.accept(this);

        asr.cmp("R0", "#0");
        asr.setRetour("EQ", 0);
        String endLabel = generateLabel();
        asr.b("EQ", endLabel);

        asr.empiler("R0");

        //asr.empilerFlags();
        String right = and.right.accept(this);
        //asr.depilerFlags();

        asr.depiler("R1");
        
        asr.or("R0", "R1", "R0");

        asr.label(endLabel);

        asr.comment("END AND");
        return null;
    }

    @Override
    public String visit(Equal equal) {
        asr.comment("START EQUAL");
        String left = equal.left.accept(this);

        asr.empiler("R0");

        String right = equal.right.accept(this);

        asr.depiler("R1");

        if (right.equals("string")){

            String loopLabel = generateLabel();
            String endLabel = generateLabel();

            asr.label(loopLabel);

            asr.lireVarReg("R3", "R1");
            asr.lireVarReg("R2", "R0");

            // Si on est à la fin des 2 strings
            asr.and("R5", "R3", "#0x00000003"); //Masque pour récupérer les 2 derniers bits
            asr.cmp("R5", "#0");
            asr.b("EQ", endLabel);
            asr.and("R4", "R2", "#0x00000003");
            asr.cmp("R4", "#0");
            asr.b("EQ", endLabel);

            asr.cmp("R3", "R2");

            asr.charSuivant("R1");
            asr.charSuivant("R0");

            //Tant qu'on peut avancer et que tout les char sont égaux
            asr.b("EQ", loopLabel);

            asr.label(endLabel);
            asr.cmp("R3", "R2");

            // Pour passer à la case vide suivante (replacer le HP)
            asr.charSuivant("R1");
            asr.charSuivant("R0");

            asr.setRetour("EQ", 1);
            asr.setRetour("NE", 0);
        }
        else{
            asr.cmp("R1", "R0");
            asr.setRetour("EQ", 1);
            asr.setRetour("NE", 0);
        }

        asr.comment("END START");

        return null;
    }

    @Override
    public String visit(Diff diff) {
        asr.comment("START DIFF");
        String left = diff.left.accept(this);

        asr.empiler("R0");

        String right = diff.right.accept(this);

        asr.depiler("R1");

        if (right.equals("string")){

            String loopLabel = generateLabel();
            String endLabel = generateLabel();

            asr.label(loopLabel);

            asr.lireVarReg("R3", "R1");
            asr.lireVarReg("R2", "R0");

            // Si on est à la fin des 2 strings
            asr.and("R5", "R3", "#0x00000003");
            asr.cmp("R5", "#0");
            asr.b("EQ", endLabel);
            asr.and("R4", "R2", "#0x00000003");
            asr.cmp("R4", "#0");
            asr.b("EQ", endLabel);

            asr.cmp("R3", "R2");

            asr.charSuivant("R1");
            asr.charSuivant("R0");

            //Tant qu'on peut avancer et que tout les char sont égaux
            asr.b("EQ", loopLabel);

            asr.label(endLabel);
            asr.cmp("R3", "R2");
            asr.charSuivant("R1");
            asr.charSuivant("R0");

            asr.setRetour("NE", 1);
            asr.setRetour("EQ", 0);
        }
        else{
            asr.cmp("R1", "R0");
            asr.setRetour("NE", 1);
            asr.setRetour("EQ", 0);
        }

        asr.comment("END DIFF");

        return null;
    }

    @Override
    public String visit(Inf inf) {
        asr.comment("START INF");
        String left = inf.left.accept(this);

        asr.empiler("R0");

        String right = inf.right.accept(this);

        asr.depiler("R1");

        if (right.equals("string")){
            String loopLabel = generateLabel();
            String endLabel = generateLabel();

            asr.label(loopLabel);

            asr.lireVarReg("R3", "R1");
            asr.lireVarReg("R2", "R0");

            // Si on est à la fin des 2 strings
            asr.and("R5", "R3", "#0x00000003");
            asr.cmp("R5", "#0");
            asr.b("EQ", endLabel);
            asr.and("R4", "R2", "#0x00000003");
            asr.cmp("R4", "#0");
            asr.b("EQ", endLabel);

            asr.cmp("R3", "R2");

            asr.charSuivant("R1");
            asr.charSuivant("R0");

            //Tant qu'on peut avancer et que tout les char sont inférieurs
            asr.b("EQ", loopLabel);

            asr.label(endLabel);
            asr.cmp("R3", "R2");
            asr.charSuivant("R1");
            asr.charSuivant("R0");

            asr.setRetour("LT", 1);
            asr.setRetour("GE", 0);
        }
        else{
            asr.cmp("R1", "R0");
            asr.setRetour("LT", 1);
            asr.setRetour("GE", 0);
        }

        asr.comment("END INF");

        return null;
    }

    @Override
    public String visit(Sup sup) {
        asr.comment("START SUP");
        String left = sup.left.accept(this);

        asr.empiler("R0");

        String right = sup.right.accept(this);

        asr.depiler("R1");

        if (right.equals("string")){
            String loopLabel = generateLabel();
            String endLabel = generateLabel();

            asr.label(loopLabel);

            asr.lireVarReg("R3", "R1");
            asr.lireVarReg("R2", "R0");

            // Si on est à la fin des 2 strings
            asr.and("R5", "R3", "#0x00000003");
            asr.cmp("R5", "#0");
            asr.b("EQ", endLabel);
            asr.and("R4", "R2", "#0x00000003");
            asr.cmp("R4", "#0");
            asr.b("EQ", endLabel);

            asr.cmp("R3", "R2");

            asr.charSuivant("R1");
            asr.charSuivant("R0");

            //Tant qu'on peut avancer et que tout les char sont inférieurs
            asr.b("EQ", loopLabel);

            asr.label(endLabel);
            asr.cmp("R3", "R2");
            asr.charSuivant("R1");
            asr.charSuivant("R0");

            asr.setRetour("GT", 1);
            asr.setRetour("LE", 0);
        }
        else{
            asr.cmp("R1", "R0");
            asr.setRetour("GT", 1);
            asr.setRetour("LE", 0);
        }
        asr.comment("END SUP");
        return null;
    }

    @Override
    public String visit(InfEqual infEqual) {
        asr.comment("START INFEQUAL");
        String left = infEqual.left.accept(this);

        asr.empiler("R0");

        String right = infEqual.right.accept(this);

        asr.depiler("R1");

        if (right.equals("string")){
            String loopLabel = generateLabel();
            String endLabel = generateLabel();

            asr.label(loopLabel);

            asr.lireVarReg("R3", "R1");
            asr.lireVarReg("R2", "R0");

            // Si on est à la fin des 2 strings
            asr.and("R5", "R3", "#0x00000003");
            asr.cmp("R5", "#0");
            asr.b("EQ", endLabel);
            asr.and("R4", "R2", "#0x00000003");
            asr.cmp("R4", "#0");
            asr.b("EQ", endLabel);

            asr.cmp("R3", "R2");

            asr.charSuivant("R1");
            asr.charSuivant("R0");

            //Tant qu'on peut avancer et que tout les char sont inférieurs
            asr.b("EQ", loopLabel);

            asr.label(endLabel);
            asr.cmp("R3", "R2");
            asr.charSuivant("R1");
            asr.charSuivant("R0");

            asr.setRetour("LE", 1);
            asr.setRetour("GT", 0);
        }
        else{
            asr.cmp("R1", "R0");
            asr.setRetour("LE", 1);
            asr.setRetour("GT", 0);
        }
        asr.comment("END INFEQUAL");

        return null;
    }

    @Override
    public String visit(SupEqual supEqual) {
        asr.comment("START SUPEQUAL");
        String left = supEqual.left.accept(this);

        asr.empiler("R0");

        String right = supEqual.right.accept(this);

        asr.depiler("R1");

        if (right.equals("string")){
            String loopLabel = generateLabel();
            String endLabel = generateLabel();

            asr.label(loopLabel);

            asr.lireVarReg("R3", "R1");
            asr.lireVarReg("R2", "R0");

            // Si on est à la fin des 2 strings
            asr.and("R5", "R3", "#0x00000003");
            asr.cmp("R5", "#0");
            asr.b("EQ", endLabel);
            asr.and("R4", "R2", "#0x00000003");
            asr.cmp("R4", "#0");
            asr.b("EQ", endLabel);

            asr.cmp("R3", "R2");

            asr.charSuivant("R1");
            asr.charSuivant("R0");

            //Tant qu'on peut avancer et que tout les char sont inférieurs
            asr.b("EQ", loopLabel);

            asr.label(endLabel);
            asr.cmp("R3", "R2");
            asr.charSuivant("R1");
            asr.charSuivant("R0");

            asr.setRetour("GE", 1);
            asr.setRetour("LT", 0);
        }
        else{
            asr.cmp("R1", "R0");
            asr.setRetour("GE", 1);
            asr.setRetour("LT", 0);
        }
        asr.comment("END SUPEQUAL");
        return null;
    }

    @Override
    public String visit(Plus plus) {
        asr.comment("START PLUS");
        String left= plus.left.accept(this);
        
        asr.empiler("R0");

        String right=plus.right.accept(this);

        asr.depiler("R1");

        asr.plus("R0","R1","R0");
        asr.comment("END PLUS");

        return null;
    }

    @Override
    public String visit(Minus minus) {
        asr.comment("START MINUS");
        String left= minus.left.accept(this);
        
        asr.empiler("R0");

        String right=minus.right.accept(this);

        asr.depiler("R1");

        asr.moins("R0","R1","R0");
        asr.comment("END MINUS");

        return null;
    }

    @Override
    public String visit(Mult mult) {
        asr.comment("START MULT");
        String left = mult.left.accept(this);

        asr.empiler("R0");

        String right = mult.right.accept(this);

        asr.depiler("R1");
        
        asr.link("mult");
        asr.comment("END MULT");
        return null;
    }

    @Override
    public String visit(Divide divide) {
        asr.comment("START DIVIDE");
        String left = divide.left.accept(this);

        asr.empiler("R0");

        String right = divide.right.accept(this);

        asr.depiler("R1");
        
        asr.link("div");
        asr.comment("END DIVIDE");

        return null;
    }


    @Override
    public String visit(MinusExpr minusExpr) {
        asr.comment("START UNARY MINUS");
        minusExpr.expr.accept(this);
        asr.negate("R0");
        asr.comment("END UNARY MINUS");
        return null;
    }

    @Override
    public String visit(IfThen ifThen) {
        asr.comment("START IF THEN");
        String thenLabel = generateLabel();
        String endLabel = generateLabel();

        ifThen.condition.accept(this);
        asr.cmp("r0","#0");
        asr.b("NE", thenLabel);
        asr.b(endLabel);
        asr.label(thenLabel);
        ifThen.thenBlock.accept(this);
        asr.label(endLabel);
        asr.comment("END IF THEN");
        return null;
    }

    @Override
    public String visit(IfThenElse ifThenElse) {
        asr.comment("START IF THEN ELSE");
        String thenLabel = generateLabel();
        String elseLabel = generateLabel();

        ifThenElse.condition.accept(this);
        asr.cmp("r0","#0");
        asr.b("NE", thenLabel);
        asr.b(elseLabel);
        
        asr.label(thenLabel);
        ifThenElse.thenBlock.accept(this);
        asr.label(elseLabel);
        ifThenElse.elseBlock.accept(this);
        asr.comment("END IF THEN ELSE");
        return null;
    }

    @Override
    public String visit(Let let) {
        asr.comment("START LET");
        currentTds = getTds();

        int nbVar = currentTds.getVarFuncEntries().size();
        asr.incrementerSp(nbVar);

        asr.newBlock();
        let.declarationList.accept(this);

        let.seqExpr.accept(this);
        asr.quitBlock();
        asr.comment("END LET");
        return null;
    }

    @Override
    public String visit(DeclarationList declarationList) {
        for (Ast expr:declarationList.listAst){
            expr.accept(this);
        }
        return null;
    }

    @Override
    public String visit(For forNode) {
        asr.comment("START FOR");
        String forLabel = generateLabel();
        String forEndLabel = generateLabel();
        Tds oldTds = currentTds;

        currentTds=getTds();
        forNode.debut.accept(this); 
        asr.empilerSP("r0"); //on empile la variable i
        asr.newBlock(); //on entre dans le bloc (on met le chainage en place)
        forNode.fin.accept(this); 
        asr.empilerSP("r0"); //on stock dans la pile la valeur limite
        asr.label(forLabel);
        asr.lireValBP("r1", -1);         // récupère i
        asr.lireValBP("r2", 1);                  // récupère valeur limite
        asr.cmp( "r2","r1");     //compare i et valeur limite
        asr.b("MI",forEndLabel);

        loopLabel.add(forEndLabel);
        forNode.bloc.accept(this);
        loopLabel.remove(loopLabel.size()-1);

        asr.lireValBP("r1", -1); //on récupère i dans r1
        asr.plus("r1","r1","#1");  //On ajoute 1 dans i
        asr.ecrireVarReg("r1", "r11,#-4*1"); // on remet i a jour dans la pile
        asr.b(forLabel);
        asr.label(forEndLabel);
        asr.moins("r13","r13", "#4"); //depile la valeur limite
        asr.quitBlock();
        asr.moins("r13","r13", "#4"); // depile la variable i
        currentTds=oldTds;

        asr.comment("END FOR");

        return null;
    }

    @Override
    public String visit(While whileNode) {
        asr.comment("START WHILE");
        String whileLabel = generateLabel();
        String whileEndLabel = generateLabel();

        asr.label(whileLabel);
        whileNode.condition.accept(this);
        asr.cmp("r0", "#0");
        asr.b("EQ", whileEndLabel);

        loopLabel.add(whileEndLabel);
        whileNode.bloc.accept(this);
        loopLabel.remove(loopLabel.size()-1);

        asr.b(whileLabel);
        asr.label(whileEndLabel);

        asr.comment("END WHILE");
        return null;
    }

    @Override
    public String visit(BreakExpr affect) {
        asr.comment("BREAK");
        asr.b(loopLabel.get(loopLabel.size()-1));
        return null;
    }

    @Override
    public String visit(NilExpr affect) {
        asr.comment("NIL");
        asr.setRetour(0); // 0 == nil ?
        return null;
    }

    @Override
    public String visit(IntExpr intExpr) {
        asr.comment("INT");
        asr.setRetour(intExpr.value);
        return "int";
    }

    @Override
    public String visit(StrExpr strExpr) {
        asr.comment("START STRING");
        asr.mov("R0", "R11"); //Adresse pour le pointeur de la string dans R0

        String str = strExpr.value;

        int nbLoop = str.length()/4;

        for (int i = 0; i < nbLoop; i++){
            asr.mov("R1", "#0");
            asr.plus("R1", "R1", "#0x"+ ((int)str.charAt(4*i) - 33) +"000000");
            asr.plus("R1", "R1", "#0x"+ ((int)str.charAt(4*i + 1) - 33) +"0000");
            asr.plus("R1", "R1", "#0x"+ ((int)str.charAt(4*i + 2) - 33) +"00");
            asr.plus("R1", "R1", "#0x"+ ((int)str.charAt(4*i + 3) - 33));

            asr.empilerHP("R1");
        }

        int remaining = str.length() % 4;

        asr.mov("R1", "#0");
        for (int i = 0; i < remaining; i++){
            asr.plus("R1", "R1", "#0x"+((int)str.charAt(str.length() - remaining + i) - 33)*(int)Math.pow(10,6-2*i));
        }
        asr.empilerHP("R1");
        asr.comment("END STRING");
        return "string";
    }

    @Override
    public String visit(SeqExpr seqExpr) {
        for (Ast expr:seqExpr.listExpr){
            expr.accept(this);
        }

        return null;
    }

    // Paramètre d'appel de fonction
    @Override
    public String visit(ListExpr listExpr) {
        for (Ast expr : listExpr.listExpr){
            expr.accept(this);
            // On empile le paramètre
            // int passage par valeur, sinon pointeur
            asr.empiler("R0");
        }
        return null;
    }

    @Override
    public String visit(Type_Declaration affect) {
        return null; // rien à faire
    }

    @Override
    public String visit(Type_Fields affect) {
        return null; // rien à faire
    }

    @Override
    public String visit(Type_Field affect) {
        return null; // rien à faire
    }

    @Override
    public String visit(TypeType affect) {
        return null; // rien à faire
    }

    @Override
    public String visit(TypeRecord affect) {
        return null; // rien à faire
    }

    @Override
    public String visit(TypeRecordVoid affect) {
        return null; // rien à faire
    }

    @Override
    public String visit(TypeArray affect) {
        return null; // rien à faire
    }

    @Override
    public String visit(VarDeclaration varDeclaration) {
        asr.comment("START VAR DECLARATION");
        String id = ((Idf)varDeclaration.idf).name; //c'est pour trouver la valeur de déplacement.
        varDeclaration.expr.accept(this);
        int deplacement = this.currentTds.getVarFuncEntry(id).getDeplacement();
        asr.stockerValeurBP("R0", deplacement);
        asr.comment("END DECLARATION");

        //La variable il faut juste l'empiler dans la pile
        return null;
    }

    @Override
    public String visit(VarDeclarationType affect) {//rien à faire
        return null;
    }

    // Appel d'un record avec initialisation des fields pour declaration variable
    @Override
    public String visit(LvalueRecord lvalueRecord) {
        asr.comment("START LVALUE RECORD");
        asr.empiler("R11"); // On empile l'adresse de la 1ère case du record

        currentTypeEntry = this.currentTds.getTypeEntry(((Idf)lvalueRecord.id).name);
        if (currentTypeEntry == null){
            System.out.println("Type non déclaré");
            System.out.println(((Idf)lvalueRecord.id).name);
            System.exit(1);
        }
        lvalueRecord.fieldList.accept(this);

        asr.depiler("R0");
        asr.comment("END LVALUER RECORD");
        return null;
    }

    // Fields d'appel de record pour déclaration d'une variable
    @Override
    public String visit(FieldList fieldList) {
        asr.comment("START FIELD LIST");
        int recordSize = fieldList.listAst.size();
        asr.decrementerHP(recordSize);
        // Ajout dans le tas de chaque field (pointeur ou int)
        for (Ast field : fieldList.listAst){
            field.accept(this);
        }
        asr.comment("END FIELD LIST");
        return null; 
    }

    @Override
    public String visit(Field field) {
        asr.comment("START FIELD");
        // Ajouter le field dans le tas à l'endroit correspondant à son déplacement
        field.expr.accept(this); // R0 contient la valeur du field

        int depl = ((RecordEntry)currentTypeEntry).getFieldDeplacement(((Idf)field.id).name);
        
        asr.lireVarReg("R1", "SP"); // R1 contient l'adresse de la 1ère case du record
        asr.str("R0", "R1",  -depl);
        asr.comment("END FIELD");
        return null; 
    }

    @Override
    public String visit(Array array) {
        asr.comment("START ARRAY");
        asr.empiler("R11");

        currentTypeEntry = this.currentTds.getTypeEntry(((Idf)array.id).name);

        array.exprOr1.accept(this);
        asr.mov("R1", "R0"); // R1 = taille du tableau
        asr.empilerHP("R1");

        String loopLabel = generateLabel();
        asr.label(loopLabel);

        // ASR va boucler sur l'expr2 qui peut donc générer en boucle des strings ou des array/record
        // Et tout simplement renvoyer le pointeur dans R0
        array.exprOr2.accept(this);
        asr.empilerHP("R0");

        asr.moins("R1", "R1", "#1");
        asr.cmp("R1", "#0");
        asr.b("NE", loopLabel);

        asr.depiler("R0");
        asr.comment("END ARRAY");
        return null;
    }

    @Override
    public String visit(FctDeclaration fctDeclaration) {
        asr.comment("START FCTN DECLARATION");
        currentTds = getTds();

        String beginLabel = generateLabel();
        String endLabel = generateLabel();

        asr.mov("r1","PC");
        asr.plus("r1", "r1", "#8");
        asr.empiler("r1");
        asr.b(endLabel);

        asr.label(beginLabel);
        asr.empiler("CS,r12,LR");
        
        fctDeclaration.exprAffect.accept(this);

        asr.depiler("r12,PC");
        asr.label(endLabel);

        asr.comment("END FCTN DECLARATION");

        return(null);
    }

    @Override
    public String visit(ProcDeclaration procDeclaration) {
        asr.comment("START PROC DECLARATION");
        currentTds = getTds();

        String beginLabel = generateLabel();
        String endLabel = generateLabel();

        asr.mov("r1","PC");
        asr.plus("r1", "r1", "#8");
        asr.empiler("r1");
        asr.b(endLabel);

        asr.label(beginLabel);
        asr.empiler("CS,r12,LR");
        
        procDeclaration.exprAffect.accept(this);

        asr.depiler("r12,PC");
        asr.label(endLabel);

        asr.comment("END PROC DECLARATION");
        return(null);
    }

    @Override
    public String visit(Fct2Declaration affect) {
        return null;
    }

    @Override
    public String visit(Fct2DeclarationType affect) {
        return null;
    }

    @Override
    public String visit(Idf affect) {

        asr.comment("IDF " + affect.name);

        String id = affect.name;
        Tds tdsCourant = currentTds;

        asr.lireAdrBP("R9");// enregistrer l'adresse de BP dans R9

        while (tdsCourant.existLocalVarFunc(id) == false){ // s'il y a pas de var ou fonctions locales, alors on doit
            // se déplacer dans la dernière imbrication
            asr.lireValBP("R10",0);//lire le chaînage statique et l'enregistre dans R0
            asr.positionnerBP("R10");//positionne BP vers l'adresse du chaînage statique

            tdsCourant = tdsCourant.getParent();
        }
        // si var est locale
        int deplacement = currentTds.getVarFuncEntry(id).getDeplacement();
       
        asr.incrementerBP(deplacement); //pcq depl est négatif
        // Puis faire un lireVarReg de R10 pour avoir la valeur de la var dans R0
        asr.mov("R10","R12");// lire l'adresse de var cherché et l'enregistrer dans R0
        asr.lireVarReg("R0","R10");
        asr.positionnerBP("R9");//remettre l'ancien adr

        asr.comment("FIN IDF " + affect.name);

        return currentTds.getVarFuncEntry(id).getType();
    }

    @Override
    public String visit(LvalueField affect) {
        asr.comment("START LVALUE FIELD");
        String type = affect.left.accept(this);// on cherche l'adresse lorsque on visite la partie left et on l'enregistre
                                        //dans R0
        asr.lireAdrHP("R9");
        //on pointe R11 vers le lvalue
        asr.positionneHP("R0");
        
        String idf = ((Idf)affect.id).name;

        RecordEntry recordEntry = (RecordEntry) currentTds.getTypeEntry(type);
        String fieldType = recordEntry.getFieldType(idf);
        int deplacement = recordEntry.getFieldDeplacement(idf);

        asr.decrementerHP(deplacement);

        asr.mov("R10", "R11");

        asr.lireVarReg("R0","R10");// enregistre la valeur dans R0
        asr.positionneHP("R9");
        asr.comment("END LVALUE FIELD");
        return fieldType;
    }

    @Override
    public String visit(LvalueIndex lvalueindex) {

        // Si membre gauche d'une affectation (affected), on doit retourner l'adresse de l'array à l'indice i
        // Donc nameIdf = true pour récupérer le nom de l'array et son déplacement

        String id = lvalueindex.left.accept(this);
        lvalueindex.exprOr.accept(this);

        int depl = this.currentTds.getVarFuncEntry(id).getDeplacement();

        asr.lireVarBP("r1", depl);
        asr.mov("R2", "R0");
        asr.lireVarReg("R3", "R1");
        asr.cmp( "r3",  "r2"); // j'ai un doute la dessus entre r2 et r3
        asr.plus("GT", "R2", "R2", "#1"); // plus 1 car le premier de la liste c'est la taille

        asr.multby4("GT", "R2"); // index *4  en rai je sais pas si c'est *4 ou *4*la taille dusuivant je sais pas tropp

        asr.moins("GT","R3","R3","R2"); // on descend dans le tas
        asr.lireVarReg("R0", "R3");

        return null;
    }

    @Override
    public String visit(Call call) {
        call.listExpr.accept(this); // on empile les parametres
        
        String id = call.id.accept(this); //on cherche ID de la fct
        FunctionEntry fct = (FunctionEntry)this.currentTds.getVarFuncEntry(id);
        int nb_param = fct.getNumberOfParameters();

        asr.lireVarReg("PC","R0");              // code à générer

        asr.depilerSP("R0");        // pour adresse de retour
        asr.depilerSP("R1");
        asr.depilerSP("R1");

        for (int i=0;i<nb_param;i++) {
            asr.depilerSP("R1"); // depiler les parametres
        }

        return null;
    }
}
