/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TestLucene;

import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import model.Document;
import model.InvertedIndex;

/**
 *
 * @author johan
 */
public class TestReadFolder {

    public static void main(String[] args) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        int ret = fileChooser.showOpenDialog(null);
        if (ret == JFileChooser.APPROVE_OPTION) {
            InvertedIndex index = new InvertedIndex();
            index.readDirectory(fileChooser.getSelectedFile());
            ArrayList<Document> documents = index.getDocuments();
            for (int i = 0; i < documents.size(); i++) {
                System.out.println("Id : " + documents.get(i).getId());
                System.out.println("Content : " + documents.get(i).getContent());
            }
        }
    }

}
