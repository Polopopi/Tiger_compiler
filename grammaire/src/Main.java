import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;

import javax.print.PrintException;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.io.IOException;

import assembleur.AsrCreator;
import assembleur.visual.Launcher;
import org.antlr.v4.gui.TreeViewer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RecognitionException;

import parser.*;
import parser.tigerParser.ProgramContext;
import tds.TdsCreator;
import ast.*;
import graphViz.GraphVizVisitor;
import tds.TdsCreator;

public class Main {

    public static void main(String[] args){

        if (args.length < 1){
            System.out.println("Error : Expected 1 argument filepath, found 0");
            return;
        }

        String testFile = args[0];

        try {

            //chargement du fichier et construction du parser
            CharStream input = CharStreams.fromFileName(testFile);
            tigerLexer lexer = new tigerLexer(input); 
            CommonTokenStream stream = new CommonTokenStream(lexer);
            tigerParser parser = new tigerParser(stream);

            ProgramContext program = parser.program();

            // code d'affichage de l'arbre syntaxique
            JFrame frame = new JFrame("Antlr AST");
            JPanel panel = new JPanel();
            TreeViewer viewer = new TreeViewer(Arrays.asList(
                    parser.getRuleNames()),program);
            viewer.setScale(1); // Scale a little
            try{
                String fileName = "./out/syntaxical_tree.png";
                viewer.save(fileName);
            }
            catch (PrintException e) {
                e.printStackTrace();
            }
            panel.add(viewer);
            frame.add(panel);
            //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            //frame.pack();
            //frame.setVisible(true);

            // Visiteur de création de l'AST + création de l'AST
            AstCreator creator = new AstCreator();
            Ast ast = program.accept(creator);

            // Visiteur de représentation graphique + appel
            GraphVizVisitor graphViz = new GraphVizVisitor();
            ast.accept(graphViz);
        
            graphViz.dumpGraph("./out/AST.dot");

            // Visiteur de création de la TDS + création de la TDS
            TdsCreator tdsCreator = new TdsCreator();
            ast.accept(tdsCreator);
            String asrFileName = "./out/asr.S";
            AsrCreator asrCreator=new AsrCreator(tdsCreator.getTds(),tdsCreator.getLinkList());
            ast.accept(asrCreator);
            asrCreator.asrFichier(asrFileName);
            Launcher.run("./out/asr.S");


        } 
        catch (IOException | RecognitionException e) {
            e.printStackTrace();
        }


    }
    
}