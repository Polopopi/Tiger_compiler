package tds;

import java.util.ArrayList;

import ast.Ast;

public class LinkList {
    private ArrayList<LinkNodeTds> listLink;

    public LinkList(){
        listLink = new ArrayList<>();
    }


    public Tds getTds(Ast ast){
        for (LinkNodeTds link : listLink){
            if (link.getAst() == ast){
                return link.getTds();
            }
        }
        return null;
    }

    public void add(Tds tds, Ast ast){
        this.listLink.add(new LinkNodeTds(ast, tds));
    }



}
