/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author admin
 */
public class InvertedIndex {

    private ArrayList<Term> termList;

    public InvertedIndex() {
        this.termList = new ArrayList<>();
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

    public static InvertedIndex toInvertedIndex(Document[] documents) {
        InvertedIndex invertedIndex = new InvertedIndex();

        List<TempTerm> tempTerms = new LinkedList<>();
        for (int i = 0; i < documents.length; i++) {
            String[] terms = Document.toTerms(documents[i].getContent().toLowerCase());
            for (int j = 0; j < terms.length; j++) {
                TempTerm t = new TempTerm(terms[j], documents[i]);
                tempTerms.add(t);
            }
        }

        for (int i = 0; i < tempTerms.size(); i++) {
            System.out.println(tempTerms.get(i).toString());
        }

        System.out.println("");
        System.out.println("Sorted");
        Collections.sort(tempTerms);

        for (int i = 0; i < tempTerms.size(); i++) {
            System.out.println(tempTerms.get(i).toString());
        }

        Term term = new Term();
        for (int i = 0; i < tempTerms.size(); i++) {

            term.setTerm(tempTerms.get(i).getTerm());

            if (i > 0) {
                if (tempTerms.get(i).getTerm()
                        .equalsIgnoreCase(tempTerms.get(i - 1).getTerm())) {
                    if (tempTerms.get(i).getDocument().getId() != tempTerms.get(i - 1).getDocument().getId()) {
                        Posting posting = new Posting(tempTerms.get(i).getDocument());
                        term.getTermList().getPostings().add(posting);
                    }
                } else {
                    Posting posting = new Posting(tempTerms.get(i).getDocument());
                    term.getTermList().getPostings().add(posting);
                }
            } else {
                Posting posting = new Posting(tempTerms.get(i).getDocument());
                term.getTermList().getPostings().add(posting);
            }
            if (i < tempTerms.size() - 1) {
                if (!tempTerms.get(i).getTerm()
                        .equalsIgnoreCase(tempTerms.get(i + 1).getTerm())) {
                    invertedIndex.getTermList().add(term);
                    term = new Term();
                }
            } else {
                invertedIndex.getTermList().add(term);
            }

        }

        return invertedIndex;
    }

}
