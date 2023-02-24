package assembleur;

import java.io.PrintWriter;
import java.util.ArrayList;

//Par default, R12 est BP
//valeur à donner à une variable est par convention enregistrée dans R1
//pour lire une variable dans la pile, sa valeur est lue et enregistrée dans R2
public class Asr {

    private ArrayList<String> asr;
    private String cond;

    public Asr(){
        this.asr=new ArrayList<String>();
        this.cond = "";
    }

    public ArrayList<String> getAsr(){
        return this.asr;
    }
    public void setCond(String val){
        this.cond = val;
    }

    public void resetCond(){
        this.cond = "";
    }


    public void setVar(int valeur) {
        this.asr.add("    LDR" + cond + " R1, ="+valeur);
    }



    public void incrementerSp(int nbr){
        this.asr.add("    ADD"+cond+" SP, SP, #"+nbr*4);
    }
    public void decrementerSp(int nbr) {
        this.asr.add("    SUB" + cond + " SP, SP, #"+nbr*4);
    }
    /**stockerValeurSP est destiné à stocker une valeur dans le stack qui est pointé par SP
     * */
    public void stockerValeurSP(){ // C'est un peu restrictif d'utiliser que R1 pour ça nan ?
        this.asr.add("    STR"+cond+" R1, [SP, #0]");
    }
    public void empilerValeurs(String registres){
        this.asr.add("    STMFA" + cond + " SP!, {"+registres+"}");
    }
    public void lireVarSP(){
        this.asr.add("    LDR" + cond + " R2, [SP, #0]");
    }
    public void depilerValeurs(String registres){
        this.asr.add("    LDMFA" + cond + " SP!, {"+registres+"}");
    }
    public void empilerFlags(){
        this.asr.add("    PUSHFD");
    }
    public void depilerFlags(){
        this.asr.add("    POPFD");
    }


    public void positionnerBP(){
        this.asr.add("    MOV R12,SP");
    }
    public void stockerValeurBP(int ordrePile){
        this.asr.add("    STR R1, [R12, #"+ordrePile*4+"]");
    }
    /**lireVarPile est destiné à lire un stack en référençant BP
     * @param ordrePile :nombre de stack à incrémenter pour atteindre le stack objectif
     * */
    public void lireVarPile(int ordrePile){
        this.asr.add("    LDR R2, [R12, #"+ordrePile*4+"]");
    }
    


    public void  plus(){
        this.asr.add("    ADD R1, R1, R2");
    }
    public void  plus(String param1,String param2,String param3){
        this.asr.add("    ADD "+param1+", "+param2+", "+param3); //R1-R2=>R1
    }
    public void  moins(String param1,String param2,String param3){
        this.asr.add("    SUB "+param1+", "+param2+", "+param3); //R1-R2=>R1
    }
    public void or(String regDest, String reg1, String reg2){
        this.asr.add("    ORR" + cond + " " + regDest + ", " + reg1 + ", " + reg2);
    }
    public void multiplie(String regDest, String reg1, String reg2){
        this.asr.add("    mul " + regDest + ", " + reg1 + ", " + reg2);
    }


    public void enregistreValeur(){
        this.asr.add( "    MOV R1,R2");
    }
    public void cmp(String reg1, String reg2){
        this.asr.add("    CMP "+reg1+", "+reg2);
    }

    public void mov(String op1, String op2){
        this.asr.add("    MOV " + op1 +", " +op2);
    }


    public void b(String flag){
        this.asr.add("    B " + flag);
    }

    public void b(String cond, String flag){
        this.asr.add("    B" + cond + " " + flag);
    }

    public void flag(String name){
        this.asr.add(name);
    }


    public void link(String label){
        this.asr.add("    BL " + label);
    }

    /*

    public void jump(String label){
        this.asr.add("    B" + cond + " " + label);
    }

    */

    public void label(String label){
        this.asr.add(label);
    }

    public void addFunction(String content){
        this.asr.add(content);
    }

    public void end(){
        this.asr.add("    END");
    }

}
