/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package retrievalinformation;

import java.util.ArrayList;
import model.Document;
import model.Posting;

/**
 *
 * @author johan
 */
public class TestGetListPosting {
    public static void main(String[] args) {
        
        // documents
        Document doc1 = new Document(1, "Candi borobudur merupakan candi terbesar di Indonesia");
        
        ArrayList<Posting> result = doc1.getListOfPosting();
        
        for (int i = 0; i < result.size(); i++) {
            System.out.print(result.get(i).getTerm() + " : ");
            System.out.println(result.get(i).getNumberOfTerm());
        }
    }
}
