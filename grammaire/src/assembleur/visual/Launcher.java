package assembleur.visual;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;
import java.util.Collections;


import assembleur.Asr;
import visual.EmulatorLogFile;
import visual.HeadlessController;
public class Launcher {
    private static final int instMemSize = 0x10000;
    /* we make sure that the output buffer is always the first symbol in memory */
    private static final int outputBufferAddress = instMemSize;
    /* VisUAL offsets line numbers by one for some reason */
    private static final List<Integer> breakpoints = List.of(40-1);//12-1,17-1,23-1,29-1
    private Launcher() {}
    /* array of all word addresses in the output buffer */
    private static String[] getOutputRange() {
        return Stream.iterate(outputBufferAddress, n -> n + 4)
                .limit(Asr.outputBufferLength / 4)
                .map(n -> String.format("0x%X", n))
                .toArray(String[]::new);
    }
    public static List<String> executeAndParseOutput(String assemblyFile) {
        EmulatorLogFile.configureLogging("", true, false, false, false, false, false,
                true, false, getOutputRange());
        HeadlessController.setLogMode(EmulatorLogFile.LogMode.BREAKPOINT);
        HeadlessController.setBreakpoints(breakpoints);
        HeadlessController.setInstMemSize(instMemSize);
        String logFile = String.format("%s_log.xml", assemblyFile);
        ExitTrapper.forbidSystemExitCall();
        int code = -1;
        try {

            HeadlessController.runFile(assemblyFile, logFile);
        } catch (ExitTrappedException e) {
            code = e.getCode();
        } finally {

            ExitTrapper.enableSystemExitCall();
            if (code != 0) {
                System.err.println("VisUAL emulator exited with error code " + code);
                System.exit(code);
            }
        }



        return OutputParser.parseOutput(logFile);
    }
    public static void run(String assemblyFile) {
        List<String> output = executeAndParseOutput(assemblyFile);
        //Collections.reverse(output);

        System.out.println("---- PROGRAM OUTPUT ----");
        //output.forEach(System.out::println);
        //System.out.println(output);
        for (int i=0;i<output.size();i++){
            String chaine = reverse(output.get(i));

            if (chaine.contains("-")){
                String resultat = chaine.substring(0,chaine.length()-1);
                System.out.println("-"+resultat);
            }
            else {
                System.out.println(chaine);
            }
        }

        //output.forEach(System.out::println);
        System.out.println("---- END PROGRAM OUTPUT ----");
    }
    public static void lireF(String name){
        try
        {
            // Le fichier d'entrée
            FileInputStream file = new FileInputStream(name);
            Scanner scanner = new Scanner(file);

            //renvoie true s'il y a une autre ligne à lire
            while(scanner.hasNextLine())
            {
                System.out.println(scanner.nextLine());
            }
            scanner.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
    public static String reverse(String str) {
        return new StringBuilder(str).reverse().toString();
    }


}
