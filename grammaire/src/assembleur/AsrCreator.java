package assembleur;

import ast.*;
import tds.Tds;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;

public class AsrCreator implements AstVisitor<String> {
    private Asr asr;
    private int labelId;
    private ArrayList<Tds> listTds;
    private Tds currentTds;
    private int oldTdsId;
    private int idGenerator;
    private String label;

    private Tds getTds(){
        return(listTds.get(oldTdsId++));
    }

    

    private int generateId(){
        return(idGenerator++);
    }

    private String generateLabel(){
        return("label"+generateId());
    }

    public AsrCreator(){
        this.asr=new Asr();
        this.idGenerator = 0;
        this.label = "";
    }

    public void asrFichier(String asrFileName) throws IOException {
        ArrayList<String> data=asr.getAsr();
        Path fichier= Paths.get(asrFileName);
        Files.write(fichier,data, StandardCharsets.UTF_8);
    }

    @Override
    public String visit(Idf affect) {
        return null;
    }

    @Override
    public String visit(Print affect) {
        return null;
    }

    @Override
    public String visit(Program program) {
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

        String texte = program.affect.accept(this);

        asr.end();
        return null;
    }

    @Override
    public String visit(Affect affect) {
        return null;
    }

    @Override
    public String visit(Or or) {
        String left = or.left.accept(this);

        asr.cmp("R11", "#0");
        asr.setVar("NE", 1);
        String endLabel = generateLabel();
        asr.b("NE", endLabel);

        asr.empiler("R11");

        //asr.empilerFlags();
        String right = or.right.accept(this);
        //asr.depilerFlags();

        asr.depiler("R0");
        
        asr.or("R11", "R0", "R11");

        asr.label(endLabel);

        return null;
    }

    @Override
    public String visit(And and) {
        String left = and.left.accept(this);

        asr.cmp("R11", "#0");
        asr.setVar("EQ", 1);
        String endLabel = generateLabel();
        asr.b("EQ", endLabel);

        asr.empiler("R11");

        //asr.empilerFlags();
        String right = and.right.accept(this);
        //asr.depilerFlags();

        asr.depiler("R0");
        
        asr.or("R11", "R0", "R11");

        asr.label(endLabel);

        return null;
    }

    @Override
    public String visit(Equal equal) {
        String left = equal.left.accept(this);

        asr.empiler("R11");

        String right = equal.right.accept(this);

        asr.depiler("R0");

        asr.cmp("R0", "R11");
        asr.setVar("EQ", 1);
        asr.setVar("NE", 0);

        return null;
    }

    @Override
    public String visit(Diff diff) {
        String left = diff.left.accept(this);

        asr.empiler("R11");

        String right = diff.right.accept(this);

        asr.depiler("R0");

        asr.cmp("R0", "R11");
        asr.setVar("NE", 1);
        asr.setVar("QE", 0);

        return null;
    }

    @Override
    public String visit(Inf inf) {
        String left = inf.left.accept(this);

        asr.empiler("R11");

        String right = inf.right.accept(this);

        asr.depiler("R0");

        asr.cmp("R0", "R11");
        asr.setVar("LT", 1);
        asr.setVar("GE", 0);

        return null;
    }

    @Override
    public String visit(Sup sup) {
        String left = sup.left.accept(this);

        asr.empiler("R11");

        String right = sup.right.accept(this);

        asr.depiler("R0");

        asr.cmp("R0", "R11");
        asr.setVar("GT", 1);
        asr.setVar("LE", 0);

        return null;
    }

    @Override
    public String visit(InfEqual infEqual) {
        String left = infEqual.left.accept(this);

        asr.empiler("R11");

        String right = infEqual.right.accept(this);

        asr.depiler("R0");

        asr.cmp("R0", "R11");
        asr.setVar("LE", 1);
        asr.setVar("GT", 0);

        return null;
    }

    @Override
    public String visit(SupEqual supEqual) {
        String left = supEqual.left.accept(this);

        asr.empiler("R11");

        String right = supEqual.right.accept(this);

        asr.depiler("R0");

        asr.cmp("R0", "R11");
        asr.setVar("GE", 1);
        asr.setVar("LT", 0);

        return null;
    }

    @Override
    public String visit(Plus plus) {
        String left= plus.left.accept(this);
        
        asr.empiler("R11");

        String right=plus.right.accept(this);

        asr.depiler("R0");

        asr.moins("R11","R0","R11");

        return null;
    }

    @Override
    public String visit(Minus minus) {
        String left= minus.left.accept(this);
        
        asr.empiler("R11");

        String right=minus.right.accept(this);

        asr.depiler("R0");

        asr.moins("R11","R0","R11");

        return null;
    }

    @Override
    public String visit(Mult mult) {
        String left = mult.left.accept(this);

        asr.empiler ("R11");

        String right = mult.right.accept(this);

        asr.depiler("R0");
        
        asr.link("mult");

        return null;
    }

    @Override
    public String visit(Divide divide) {
        String left = divide.left.accept(this);

        asr.empiler("R11");

        String right = divide.right.accept(this);

        asr.depiler("R0");
        
        asr.link("div");

        return null;
    }


    @Override
    public String visit(MinusExpr minusExpr) {
        minusExpr.expr.accept(this);
        asr.negate("R11");
        return null;
    }

    @Override
    public String visit(IfThen ifThen) {
        String thenLabel = generateLabel();
        String elseLabel = generateLabel();

        ifThen.condition.accept(this);
        asr.cmp("r1","#0");
        asr.b("NE", thenLabel);
        asr.b(elseLabel);
        asr.label(thenLabel);
        ifThen.thenBlock.accept(this);
        return null;
    }

    @Override
    public String visit(IfThenElse ifThenElse) {
        String thenLabel = generateLabel();
        String elseLabel = generateLabel();

        ifThenElse.condition.accept(this);
        asr.cmp("r1","#0");
        asr.b("NE", thenLabel);
        asr.b(elseLabel);
        asr.label(thenLabel);
        ifThenElse.thenBlock.accept(this);
        return null;
    }

    @Override
    public String visit(Let affect) {
        return null;
    }

    @Override
    public String visit(For affect) {
        String forLabel = generateLabel();
        String forEndLabel = generateLabel();

        asr.label(forLabel);
        asr.empilerValeurs("R11");


        return null;
    }

    @Override
    public String visit(While whileNode) {
        String whileLabel = generateLabel();
        String whileEndLabel = generateLabel();

        asr.label(whileLabel);
        whileNode.condition.accept(this);
        asr.cmp("r1", "#0");
        asr.b("EQ", whileEndLabel);

        label = whileEndLabel;
        whileNode.bloc.accept(this);

        asr.b(whileLabel);
        asr.label(whileEndLabel);

        return null;
    }

    @Override
    public String visit(BreakExpr affect) {
        asr.b(label);
        return null;
    }

    @Override
    public String visit(NilExpr affect) {
        asr.setVar(0);
        return null;
    }

    @Override
    public String visit(IntExpr intExpr) {
        asr.setVar(intExpr.value);
        return null;
    }

    @Override
    public String visit(StrExpr affect) {

        return null;
    }

    @Override
    public String visit(SeqExpr seqExpr) {
        for (Ast expr:seqExpr.listExpr){
            expr.accept(this);
        }

        return null;
    }

    @Override
    public String visit(DeclarationList affect) {
        return null;
    }

    @Override
    public String visit(ListExpr affect) {
        return null;
    }

    @Override
    public String visit(Type_Declaration affect) {
        return null;
    }

    @Override
    public String visit(Type_Fields affect) {
        return null;
    }

    @Override
    public String visit(Type_Field affect) {
        return null;
    }

    @Override
    public String visit(TypeType affect) {
        return null;
    }

    @Override
    public String visit(TypeRecord affect) {
        return null;
    }

    @Override
    public String visit(TypeRecordVoid affect) {
        return null;
    }

    @Override
    public String visit(TypeArray affect) {
        return null;
    }

    @Override
    public String visit(Field affect) {
        return null;
    }

    @Override
    public String visit(FieldList affect) {
        return null;
    }

    @Override
    public String visit(VarDeclaration affect) {
        return null;
    }

    @Override
    public String visit(FctDeclaration affect) {
        return null;
    }

    @Override
    public String visit(ProcDeclaration affect) {
        return null;
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
    public String visit(LvalueField affect) {
        return null;
    }

    @Override
    public String visit(LvalueIndex affect) {
        return null;
    }

    @Override
    public String visit(Array affect) {
        return null;
    }

    @Override
    public String visit(LvalueRecord affect) {
        return null;
    }

    @Override
    public String visit(Call affect) {
        return null;
    }

    @Override
    public String visit(VarDeclarationType affect) {
        return null;
    }
}
