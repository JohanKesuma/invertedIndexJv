/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package retrievalinformation;

import java.util.ArrayList;
import model.Document;
import model.InvertedIndex;
import model.Posting;

/**
 *
 * @author johan
 */
public class TestSortedIndex {
    public static void main(String[] args) {
        Document doc1 = new Document(1, "Candi borobudur merupakan candi terbesar yang ada di Indonesia");
        Document doc2 = new Document(2, "Candi Prambanan dibangun oleh Bandung Bandawasa");
        Document doc3 = new Document(3, "Prambanan merupakan nama sebuah kota yang ada di Yogyakarta");

        InvertedIndex invertedIndex = new InvertedIndex();

        invertedIndex.addNewDocument(doc1);
        invertedIndex.addNewDocument(doc2);
        invertedIndex.addNewDocument(doc3);

        ArrayList<Posting> posting = invertedIndex.getSortedIndex();

        for (int i = 0; i < posting.size(); i++) {
            System.out.println(posting.get(i).getTerm() + " -> " + posting.get(i).getDocument().getId());
        }
    }
    
}
