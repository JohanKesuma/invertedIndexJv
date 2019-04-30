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
public class TestIndoStopWords {

    public static void main(String[] args) {
        Document doc = new Document(1, "saya pergi ke pasar hari senin");
        System.out.println("With Stop Word");
        System.out.println(doc);
        System.out.println("Without Stop word");
        doc.removeIndoStopWords();
        System.out.println(doc);
    }
}
