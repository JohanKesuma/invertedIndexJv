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
public class Term {

    private String term;
    private PostingList termList;

    public Term(String term, PostingList termList) {
        this.term = term;
        this.termList = termList;
    }

}
