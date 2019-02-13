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
public class InvertedIndex {

    private ArrayList<Term> termList;

    public InvertedIndex() {
        this.termList = new ArrayList<>();
    }

    public InvertedIndex(ArrayList<Term> termList) {
        this.termList = termList;
    }

}
