/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.CharArraySet;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.core.StopFilter;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.en.PorterStemFilter;
import org.apache.lucene.analysis.id.IndonesianAnalyzer;
import org.apache.lucene.analysis.id.IndonesianStemFilter;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.util.Version;

/**
 *
 * @author puspaingtyas
 */
public class Document implements Comparable<Document> {

    private int id;
    private String content;
    private String realContent;

    public Document() {
    }

    public Document(int id, String content) {
        this.id = id;
        this.content = content;
        this.realContent = content;
    }

    public Document(String content) {
        this.content = content;
        this.realContent = content;
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

    public String[] getListTerm() {
        return null;
    }

    public static String[] toTerms(String content) {
        return content.split(" ");
    }

    public String[] getListofTerm() {
        String value = this.getContent().toLowerCase();
        value = value.replaceAll("[.,?!]", "");
        return value.split(" ");
    }

    public ArrayList<Posting> getListOfPosting() {
        String[] terms = getListofTerm();

        // menampung hasil
        ArrayList<Posting> result = new ArrayList<>();

        for (int i = 0; i < terms.length; i++) {
            if (i == 0) {

                // untuk kata pertama
                Posting tempPosting = new Posting(terms[i], this);
                result.add(tempPosting);
            } else {

                // urutkan result
                Collections.sort(result);

                Posting tempPosting = new Posting(terms[i], this);

                // cari apakah term sudah ada di dalam arraylist result
                int pos = Collections.binarySearch(result, tempPosting);
                if (pos < 0) { // jika tidak ketemu

                    result.add(tempPosting);

                } else { // ika term sudah ada

                    // tambahkan numberOfTerm
                    int tempNumber = result.get(pos).getNumberOfTerm() + 1;
                    result.get(pos).setNumberOfTerm(tempNumber);
                }
            }
        }
        Collections.sort(result);

        return result;
    }

    @Override
    public int compareTo(Document o) {
        return Integer.compare(this.id, o.id);
    }

    /**
     * Fungsi untuk menghilangkan kata stop word
     */
    public void removeStopWords() {
        // asumsi content sudah ada
        String text = content;
        Version matchVersion = Version.LUCENE_7_7_0; // Substitute desired Lucene version for XY
        Analyzer analyzer = new StandardAnalyzer();
        analyzer.setVersion(matchVersion);
        // ambil stopwords
        CharArraySet stopWords = EnglishAnalyzer.getDefaultStopSet();
        // buat token
        TokenStream tokenStream = analyzer.tokenStream(
                "myField",
                new StringReader(text.trim()));
        // buang stop word
        tokenStream = new StopFilter(tokenStream, stopWords);
        // buat string baru tanpa stopword
        StringBuilder sb = new StringBuilder();
        CharTermAttribute charTermAttribute = tokenStream.addAttribute(CharTermAttribute.class);
        try {
            tokenStream.reset();
            while (tokenStream.incrementToken()) {
                String term = charTermAttribute.toString();
                sb.append(term + " ");
            }
        } catch (Exception ex) {
            System.out.println("Exception: " + ex);
        }
        content = sb.toString();
    }
    
    public void removeIndoStopWords(){
        // asumsi content sudah ada
        String text = content;
        Version matchVersion = Version.LUCENE_7_7_0; // Substitute desired Lucene version for XY
        Analyzer analyzer = new IndonesianAnalyzer();
        analyzer.setVersion(matchVersion);
        // ambil stopwords
        CharArraySet stopWords = IndonesianAnalyzer.getDefaultStopSet();
        // buat token
        TokenStream tokenStream = analyzer.tokenStream(
                "myField",
                new StringReader(text.trim()));
        // buang stop word
        tokenStream = new StopFilter(tokenStream, stopWords);
        // buat string baru tanpa stopword
        StringBuilder sb = new StringBuilder();
        CharTermAttribute charTermAttribute = tokenStream.addAttribute(CharTermAttribute.class);
        try {
            tokenStream.reset();
            while (tokenStream.incrementToken()) {
                String term = charTermAttribute.toString();
                sb.append(term + " ");
            }
        } catch (Exception ex) {
            System.out.println("Exception: " + ex);
        }
        content = sb.toString();
    }

    /**
     * Fungsi untuk menghilangkan stop word dan stemming
     */
    public void stemming() {
        String text = content;
//        System.out.println("Text = "+text);
        Version matchVersion = Version.LUCENE_7_7_0; // Substitute desired Lucene version for XY
        Analyzer analyzer = new StandardAnalyzer();
        analyzer.setVersion(matchVersion);
        // buat token
        TokenStream tokenStream = analyzer.tokenStream(
                "myField",
                new StringReader(text.trim()));
        // stemming
        tokenStream = new PorterStemFilter(tokenStream);
        
        StringBuilder sb = new StringBuilder();
        CharTermAttribute charTermAttribute = tokenStream.addAttribute(CharTermAttribute.class);
        try {
            tokenStream.reset();
            while (tokenStream.incrementToken()) {
                String term = charTermAttribute.toString();
                sb.append(term + " ");
            }
        } catch (Exception ex) {
            System.out.println("Exception: " + ex);
        }
        content = sb.toString();
    }
    
    public void indoStemming(){
        String text = content;
        
        Version matchVersion = Version.LUCENE_7_7_0; // Substitute desired Lucene version for XY
        Analyzer analyzer = new IndonesianAnalyzer();
        analyzer.setVersion(matchVersion);
        // buat token
        TokenStream tokenStream = analyzer.tokenStream(
                "myField",
                new StringReader(text.trim()));
        // stemming
        tokenStream = new IndonesianStemFilter(tokenStream);
        
        StringBuilder sb = new StringBuilder();
        CharTermAttribute charTermAttribute = tokenStream.addAttribute(CharTermAttribute.class);
        try {
            tokenStream.reset();
            while (tokenStream.incrementToken()) {
                String term = charTermAttribute.toString();
                sb.append(term + " ");
            }
        } catch (Exception ex) {
            System.out.println("Exception: " + ex);
        }
        content = sb.toString();
    }

    @Override
    public String toString() {
        return "Document{" + "id=" + id + ", content=" + content + ", realContent=" + realContent + '}';
    }

    public String getRealContent() {
        return realContent;
    }

    public void setRealContent(String realContent) {
        this.realContent = realContent;
    }

public static Document readFile(int doc, File file) {

        String content = "";
        FileReader fileReader;
        try {
            fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String str;
            while ((str = bufferedReader.readLine()) != null) {
                content += str;
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Document.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Document.class.getName()).log(Level.SEVERE, null, ex);
        }
        Document document = new Document(doc, content);
        document.indoStemming();

        return document;
    }
}
