/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package retrievalinformation;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import model.Document;
import model.InvertedIndex;
import model.Posting;
import model.TempTerm;
import model.Term;

/**
 *
 * @author admin
 */
public class RetrievalInformation {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Document[] documents = new Document[3];
        documents[0] = new Document();
        documents[0].setId(1);
        documents[0].setContent("Candi borobudur merupakan candi terbesar yang ada di Indonesia");
        documents[1] = new Document();
        documents[1].setId(2);
        documents[1].setContent("Candi Prambanan dibangun oleh Bandung Bandawasa");
        documents[2] = new Document();
        documents[2].setId(3);
        documents[2].setContent("Prambanan merupakan nama sebuah kota yang ada seru");
        
        InvertedIndex invertedIndex = InvertedIndex.toInvertedIndex(documents);

        System.out.println("");
        System.out.println("");
        System.out.println(invertedIndex.toString());
    }

}
