/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TestLucene;

import model.Document;

/**
 *
 * @author johan
 */
public class TestIndoStemming {
    public static void main(String[] args) {
        Document doc = new Document(1, "saya menemukan barang berwarna merah");
        System.out.println("Without Stemming");
        System.out.println(doc);
        System.out.println("With stemming");
        doc.indoStemming();
        System.out.println(doc);
    }
}
