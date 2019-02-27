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
public class Posting implements Comparable<Posting> {

    private String term;
    private Document document;
    private int numberOfTerm;

    public Posting() {
        numberOfTerm = 1;
    }

    public Posting(Document document) {
        this.document = document;
        numberOfTerm = 1;
    }

    public Posting(String term, Document document) {
        this.term = term;
        this.document = document;
        numberOfTerm = 1;
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
    public String toString() {
        String string = this.term + " -> " + this.document.getId();

        return string;
    }

    /**
     * @return the numberOfTerm
     */
    public int getNumberOfTerm() {
        return numberOfTerm;
    }

    /**
     * @param numberOfTerm the numberOfTerm to set
     */
    public void setNumberOfTerm(int numberOfTerm) {
        this.numberOfTerm = numberOfTerm;
    }

}
