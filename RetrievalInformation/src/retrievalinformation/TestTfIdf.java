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
import model.Term;

/**
 *
 * @author johan
 */
public class TestTfIdf {
    public static void main(String[] args) {
        // seting dokumen
        Document doc1 = new Document(1, "Shipment of gold damaged in a fire");
        Document doc2 = new Document(2, "delivery of silver arrived in a silver truck");
        Document doc3 = new Document(3, "shipment of gold arrived in a truck");
        
        // buat object invertedIndex
        InvertedIndex index = new InvertedIndex();
        // tmbahkan document ke index
        index.addNewDocument(doc1);
        index.addNewDocument(doc2);
        index.addNewDocument(doc3);
        
        // panggil fungsi search
        index.makeDictionaryWithTermNumber();
        ArrayList<Term> result = index.getDictionary();
        // tampilkan isi document dan id-nya
        for (int i = 0; i < result.size(); i++) {
            System.out.println("Term = "+result.get(i).getTerm()+
                    ",numberOfDocument = "+result.get(i).getNumberOfDocument());
            ArrayList<Posting> tempPosting = result.get(i).getTermList().getPostings();
            for (int j = 0; j < tempPosting.size(); j++) {
                System.out.println("id_doc = "+tempPosting.get(j).getDocument().getId()
                +", numberofTerm = "+tempPosting.get(j).getNumberOfTerm());
            }
        }
        
        // number of document
        String tempString = "silver";
        int result2 = index.getDocumentFrequency(tempString);
        System.out.println("Number of Doc with "+tempString+" is "+result2);
        
        // idf
        String tempString1 = "arrived";
        double result3 = index.getInverseDocumentFreuency(tempString1);
        System.out.println("IDF of "+tempString1+" is "+result3);
        
        // tf
        // idf
        String tempString2 = "shipment";
        int idDoc=1;
        int result4 = index.getTermFrequency(tempString2, idDoc);
        System.out.println("TF of "+tempString2+" in idDoc ="+idDoc+ " is "+result4);
        

    }
}
