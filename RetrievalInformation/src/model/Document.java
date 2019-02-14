/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author puspaingtyas
 */
public class Document {
    private int id;
    private String content;

    public Document() {
    }

    public Document(int id, String content) {
        this.id = id;
        this.content = content;
    }
    

    public Document(String content) {
        this.content = content;
    }

    /**
     * @return the content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content the content to set
     */
    public void setContent(String content) {
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public String[] getListTerm(){
        return null;
    }
    
    public static String[] toTerms(String  content){
        return content.split(" ");
    }
}
