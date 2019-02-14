/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author johan
 */
public class TempTerm implements Comparable<TempTerm>{
    private String term;
    private Document document;

    public TempTerm() {
    }

    public TempTerm(String temp, Document document) {
        this.term = temp;
        this.document = document;
    }

    /**
     * @return the term
     */
    public String getTerm() {
        return term;
    }

    /**
     * @param term the term to set
     */
    public void setTerm(String term) {
        this.term = term;
    }

    /**
     * @return the document
     */
    public Document getDocument() {
        return document;
    }

    /**
     * @param document the document to set
     */
    public void setDocument(Document document) {
        this.document = document;
    }

    @Override
    public int compareTo(TempTerm o) {
        return this.term.compareTo(o.term);
    }
    
    @Override
    public String toString(){
        String string = this.term + " -> "+ this.document.getId();
        
        return string;
    }
}
