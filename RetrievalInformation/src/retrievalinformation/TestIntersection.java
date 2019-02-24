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
public class TestIntersection {
    public static void main(String[] args) {
        Document doc1 = new Document(1, "Computer Information Retrieval");
        Document doc2 = new Document(2, "Computer Organization and Architecture");
        Document doc3 = new Document(3, "Machine Learning Architecture");

        InvertedIndex invertedIndex = new InvertedIndex();

        invertedIndex.addNewDocument(doc1);
        invertedIndex.addNewDocument(doc2);
        invertedIndex.addNewDocument(doc3);

        invertedIndex.makeDictionary();
        
        String query = "machine Learning";

        ArrayList<Posting> postings = invertedIndex.search(query);

        for (int i = 0; i < postings.size(); i++) {
            System.out.println((i + 1) + ". " + postings.get(i).getDocument().getContent());
        }
    }
}
