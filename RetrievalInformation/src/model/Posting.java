/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author admin
 */
public class Posting implements Comparable<Posting>{

    private String term;
    private Document document;

    public Posting() {
    }

    public Posting(Document document) {
        this.document = document;
    }

    public Posting(String term, Document document) {
        this.term = term;
        this.document = document;
    }

    public String getTerm() {
        return term;
    }

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
    public int compareTo(Posting o) {
        return this.term.compareTo(o.term);
    }
    
    @Override
    public String toString(){
        String string = this.term + " -> "+ this.document.getId();
        
        return string;
    }

}
