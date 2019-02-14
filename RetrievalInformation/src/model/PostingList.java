/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;

/**
 *
 * @author admin
 */
public class PostingList {

    private ArrayList<Posting> postings;

    public PostingList(ArrayList<Posting> postings) {
        this.postings = postings;
    }

    public PostingList() {
        this.postings = new ArrayList<>();
    }

    /**
     * @return the postings
     */
    public ArrayList<Posting> getPostings() {
        return postings;
    }

    /**
     * @param postings the postings to set
     */
    public void setPostings(ArrayList<Posting> postings) {
        this.postings = postings;
    }
    
    

}
