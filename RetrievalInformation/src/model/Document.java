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
 * @author puspaingtyas
 */
public class Document {

    private int id;
    private String content;

    public Document() {
    }

    public Document(int id, String content) {
        this.id = id;
        this.content = content;
    }

    public Document(String content) {
        this.content = content;
    }

    /**
     * @return the content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content the content to set
     */
    public void setContent(String content) {
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String[] getListTerm() {
        return null;
    }

    public static String[] toTerms(String content) {
        return content.split(" ");
    }

    public String[] getListofTerm() {
        String value = this.getContent().toLowerCase();
        value = value.replaceAll("[.,?!]", "");
        return value.split(" ");
    }
    

    public ArrayList<Posting> getListOfPosting() {
        String[] terms = getListofTerm();
        
        // menampung hasil
        ArrayList<Posting> result = new ArrayList<>();
        
        for (int i = 0; i < terms.length; i++) {
            if (i == 0) {
                
                // untuk kata pertama
                Posting tempPosting = new Posting(terms[i], this);
                result.add(tempPosting);
            } else {
                
                // urutkan result
                Collections.sort(result);
                
                Posting tempPosting = new Posting(terms[i], this);
                
                // cari apakah term sudah ada di dalam arraylist result
                int pos = Collections.binarySearch(result, tempPosting);
                if (pos < 0) { // jika tidak ketemu
                    
                    result.add(tempPosting);
                    
                } else { // ika term sudah ada
                    
                    // tambahkan numberOfTerm
                    int tempNumber = result.get(pos).getNumberOfTerm() + 1;
                    result.get(pos).setNumberOfTerm(tempNumber);
                }
            }
        }

        return result;
    }
}
