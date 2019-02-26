/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author admin
 */
public class InvertedIndex {

    private ArrayList<Document> documents;
    private ArrayList<Term> dictionary;

    public InvertedIndex() {
        this.dictionary = new ArrayList<>();
        this.documents = new ArrayList<>();
    }

    public InvertedIndex(ArrayList<Term> termList) {
        this.dictionary = termList;
    }

    /**
     * @return the dictionary
     */
    public ArrayList<Term> getDictionary() {
        return dictionary;
    }

    /**
     * @param dictionary the dictionary to set
     */
    public void setDictionary(ArrayList<Term> dictionary) {
        this.dictionary = dictionary;
    }

    public void addNewDocument(Document document) {
        this.documents.add(document);
    }

    public int getDocumentSize() {
        return this.documents.size();
    }

    @Override
    public String toString() {
        String string = "";

        for (int i = 0; i < this.dictionary.size(); i++) {
            System.out.print(this.dictionary.get(i).getTerm() + " -> ");
            for (int j = 0; j < this.dictionary.get(i).getTermList().getPostings().size(); j++) {
                System.out.print(this.dictionary.get(i).getTermList().getPostings().get(j).getDocument().getId() + ", ");
            }
            System.out.println("");
        }

        return string;
    }

    public ArrayList<Posting> getUnsortedIndex() {
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

    public ArrayList<Posting> getSortedIndex() {
        ArrayList<Posting> postingList = this.getUnsortedIndex();

        Collections.sort(postingList);

        return postingList;
    }

    public void makeDictionary() {
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
                    getDictionary().add(term);
                    term = new Term();
                }
            } else {
                getDictionary().add(term);
            }

        }
    }
    
    public ArrayList<Posting> search(String query){
        makeDictionary();
        
        String tempQuery[] = query.split(" ");
        ArrayList<Posting> result = new ArrayList<>();
        
        for (int i = 0; i < tempQuery.length; i++) {
            String string = tempQuery[i];
            if (i == 0) {
                result = searchOneWord(string);
            } else {
                ArrayList<Posting> result1 = searchOneWord(tempQuery[i]);
                result = intersection(result, result1);
            }
        }
        
        return result;
    }

    public ArrayList<Posting> intersection(ArrayList<Posting> p1, ArrayList<Posting> p2) {
        if (p1 == null || p2 == null) {
            return new ArrayList<>();
        }
        
        ArrayList<Posting> postings = new ArrayList<>();
        int p1Index = 0;
        int p2Index = 0;

        Posting post1 = p1.get(p1Index);
        Posting post2 = p2.get(p2Index);

        while (true) {
            if (post1.getDocument().getId() == post2.getDocument().getId()) {
                try {
                    postings.add(post1);
                    p1Index++;
                    p2Index++;
                    post1 = p1.get(p1Index);
                    post2 = p2.get(p2Index);
                } catch (Exception e) {
                    break;
                }

            } else if (post1.getDocument().getId() < post2.getDocument().getId()) {
                try {
                    p1Index++;
                    post1 = p1.get(p1Index);
                } catch (Exception e) {
                    break;
                }

            } else {
                try {
                    p2Index++;
                    post2 = p2.get(p2Index);
                } catch (Exception e) {
                    break;
                }
            }
        }
        return postings;
    }

    public ArrayList<Posting> searchOneWord(String word) {
        Term tempTerm = new Term(word);
        if (getDictionary().isEmpty()) {
            // dictionary kosong
            return null;
        } else {
            int position = Collections.binarySearch(getDictionary(), tempTerm);
            if (position < 0) {
                //tidak ditemukan
                return null;
            } else {
                return getDictionary().get(position).getTermList().getPostings();
            }
        }
    }

    public void makeDictionaryV2() {
        ArrayList<Posting> list = this.getSortedIndex();

        for (int i = 0; i < list.size(); i++) {
            if (dictionary.isEmpty()) {
                Term term = new Term(list.get(i).getTerm());
                term.getTermList().getPostings().add(list.get(i));
                getDictionary().add(term);
            } else {
                Term tempTerm = new Term(list.get(i).getTerm());

                int position = Collections.binarySearch(getDictionary(), tempTerm);
                if (position < 0) {
                    tempTerm.getTermList().getPostings().add(list.get(i));
                    getDictionary().add(tempTerm);
                } else {

                    getDictionary().get(position).getTermList().getPostings().add(list.get(i));
                    Collections.sort(getDictionary());
                }

                Collections.sort(getDictionary());
            }
        }

    }

}
