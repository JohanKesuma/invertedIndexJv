/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author admin
 */
public class InvertedIndex {

    private ArrayList<Document> documents;
    private ArrayList<Term> termList;

    public InvertedIndex() {
        this.termList = new ArrayList<>();
        this.documents = new ArrayList<>();
    }

    public InvertedIndex(ArrayList<Term> termList) {
        this.termList = termList;
    }

    /**
     * @return the termList
     */
    public ArrayList<Term> getTermList() {
        return termList;
    }

    /**
     * @param termList the termList to set
     */
    public void setTermList(ArrayList<Term> termList) {
        this.termList = termList;
    }
    
    public void addNewDocument(Document document){
        this.documents.add(document);
    }
    
    public int getDocumentSize(){
        return this.documents.size();
    }

    @Override
    public String toString() {
        String string = "";

        for (int i = 0; i < this.termList.size(); i++) {
            System.out.print(this.termList.get(i).getTerm() + " -> ");
            for (int j = 0; j < this.termList.get(i).getTermList().getPostings().size(); j++) {
                System.out.print(this.termList.get(i).getTermList().getPostings().get(j).getDocument().getId() + ", ");
            }
            System.out.println("");
        }

        return string;
    }
    
    public ArrayList<Posting> getUnsortedIndex(){
        ArrayList<Posting> postingList = new ArrayList<>();
        for (int i = 0; i < getDocumentSize(); i++) {
            String[] terms = Document.toTerms(this.documents.get(i).getContent().toLowerCase());
            for (int j = 0; j < terms.length; j++) {
                Posting t = new Posting(terms[j], this.documents.get(i));
                postingList.add(t);
            }
        }
        
        return postingList;
    }
    
    public ArrayList<Posting> getSortedIndex(){
        ArrayList<Posting> postingList = this.getUnsortedIndex();
        
        Collections.sort(postingList);
        
        return  postingList;
    }
    
    public void makeDictionary(){
        ArrayList<Posting> postingList = this.getSortedIndex();
        
        Term term = new Term();
        for (int i = 0; i < postingList.size(); i++) {

            term.setTerm(postingList.get(i).getTerm());

            if (i > 0) {
                if (postingList.get(i).getTerm()
                        .equalsIgnoreCase(postingList.get(i - 1).getTerm())) {
                    if (postingList.get(i).getDocument().getId() != postingList.get(i - 1).getDocument().getId()) {
                        Posting posting = postingList.get(i);
                        term.getTermList().getPostings().add(posting);
                    }
                } else {
                    Posting posting = postingList.get(i);
                    term.getTermList().getPostings().add(posting);
                }
            } else {
                Posting posting = postingList.get(i);
                term.getTermList().getPostings().add(posting);
            }
            if (i < postingList.size() - 1) {
                if (!postingList.get(i).getTerm()
                        .equalsIgnoreCase(postingList.get(i + 1).getTerm())) {
                    getTermList().add(term);
                    term = new Term();
                }
            } else {
                getTermList().add(term);
            }

        }
    }

}
