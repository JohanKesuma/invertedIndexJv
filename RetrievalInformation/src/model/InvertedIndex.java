/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author admin
 */
public class InvertedIndex {

    private ArrayList<Document> documents;
    private ArrayList<Term> dictionary;

    public InvertedIndex() {
        this.dictionary = new ArrayList<>();
        this.documents = new ArrayList<>();
    }

    public InvertedIndex(ArrayList<Term> termList) {
        this.dictionary = termList;
    }

    /**
     * @return the dictionary
     */
    public ArrayList<Term> getDictionary() {
        return dictionary;
    }

    /**
     * @param dictionary the dictionary to set
     */
    public void setDictionary(ArrayList<Term> dictionary) {
        this.dictionary = dictionary;
    }

    public void addNewDocument(Document document) {
        this.documents.add(document);
        Collections.sort(getDocuments());
    }

    public int getDocumentSize() {
        return this.documents.size();
    }

    @Override
    public String toString() {
        String string = "";

        for (int i = 0; i < this.dictionary.size(); i++) {
            System.out.print(this.dictionary.get(i).getTerm() + " -> ");
            for (int j = 0; j < this.dictionary.get(i).getTermList().getPostings().size(); j++) {
                System.out.print(this.dictionary.get(i).getTermList().getPostings().get(j).getDocument().getId() + ", ");
            }
            System.out.println("");
        }

        return string;
    }

    public ArrayList<Posting> getUnsortedPostingListWithTermNumber() {

        ArrayList<Posting> postingList = new ArrayList<>();

        for (int i = 0; i < getDocumentSize(); i++) {
//            String[] terms = Document.toTerms(this.documents.get(i).getContent().toLowerCase());

            ArrayList<Posting> postingDocument = getDocuments().get(i).getListOfPosting();
            for (int j = 0; j < postingDocument.size(); j++) {

                Posting t = postingDocument.get(j);
                postingList.add(t);
            }
        }

        return postingList;
    }

    public ArrayList<Posting> getUnsortedIndex() {
        ArrayList<Posting> postingList = new ArrayList<>();
        for (int i = 0; i < getDocumentSize(); i++) {
            String[] terms = Document.toTerms(this.documents.get(i).getContent().toLowerCase());
            for (int j = 0; j < terms.length; j++) {
                Posting t = new Posting(terms[j], this.documents.get(i));
                postingList.add(t);
            }
        }

        return postingList;
    }

    public ArrayList<Posting> getSortedPostingListWithTermNumber() {
        // siapkan posting List
        ArrayList<Posting> list = new ArrayList<>();
        // panggil list yang belum terurut
        list = this.getUnsortedPostingListWithTermNumber();
        // urutkan
        Collections.sort(list);
        return list;
    }

    public ArrayList<Posting> getSortedIndex() {
        ArrayList<Posting> postingList = this.getUnsortedIndex();

        Collections.sort(postingList);

        return postingList;
    }

    public void makeDictionary() {
        ArrayList<Posting> postingList = this.getSortedIndex();

        Term term = new Term();
        for (int i = 0; i < postingList.size(); i++) {

            term.setTerm(postingList.get(i).getTerm());

            if (i > 0) {
                if (postingList.get(i).getTerm()
                        .equalsIgnoreCase(postingList.get(i - 1).getTerm())) {
                    if (postingList.get(i).getDocument().getId() != postingList.get(i - 1).getDocument().getId()) {
                        Posting posting = postingList.get(i);
                        term.getTermList().getPostings().add(posting);
                    }
                } else {
                    Posting posting = postingList.get(i);
                    term.getTermList().getPostings().add(posting);
                }
            } else {
                Posting posting = postingList.get(i);
                term.getTermList().getPostings().add(posting);
            }
            if (i < postingList.size() - 1) {
                if (!postingList.get(i).getTerm()
                        .equalsIgnoreCase(postingList.get(i + 1).getTerm())) {
                    getDictionary().add(term);
                    term = new Term();
                }
            } else {
                getDictionary().add(term);
            }

        }
    }

    public void makeDictionaryWithTermNumber() {
        getDictionary().clear();
        // cek deteksi ada term yang frekuensinya lebih dari 
        // 1 pada sebuah dokumen
        // buat posting list term terurut
        ArrayList<Posting> list = getSortedPostingListWithTermNumber();
        // looping buat list of term (dictionary)
        for (int i = 0; i < list.size(); i++) {
            // cek dictionary kosong?
            if (getDictionary().isEmpty()) {
                // buat term
                Term term = new Term(list.get(i).getTerm());
                // tambah posting ke posting list utk term ini
                term.getTermList().getPostings().add(list.get(i));
                // tambah ke dictionary
                getDictionary().add(term);
            } else {
                // dictionary sudah ada isinya
                Term tempTerm = new Term(list.get(i).getTerm());
                // pembandingan apakah term sudah ada atau belum
                // luaran dari binarysearch adalah posisi
                int position = Collections.binarySearch(getDictionary(), tempTerm);
                if (position < 0) {
                    // term baru
                    // tambah postinglist ke term
                    tempTerm.getTermList().getPostings().add(list.get(i));
                    // tambahkan term ke dictionary
                    getDictionary().add(tempTerm);
                } else {
                    // term ada
                    // tambahkan postinglist saja dari existing term
                    getDictionary().get(position).
                            getTermList().getPostings().add(list.get(i));
                    // urutkan posting list
                    Collections.sort(getDictionary().get(position)
                            .getTermList().getPostings());
                }
                // urutkan term dictionary
                Collections.sort(getDictionary());
            }

        }

    }

    /**
     * Fungsi untuk membentuk posting list dari sebuah query
     *
     * @param query
     * @return
     */
    public ArrayList<Posting> getQueryPosting(String query) {
        // buat dokumen
        Document temp = new Document(-1, query);
        // buat posting list
        ArrayList<Posting> result = temp.getListOfPosting();
        // hitung bobot
        // isi bobot dari posting list
        for (int i = 0; i < result.size(); i++) {
            // ambil term
            String tempTerm = result.get(i).getTerm();
            // cari idf
            double idf = getInverseDocumentFrequency(tempTerm);
            // cari tf
            int tf = result.get(i).getNumberOfTerm();
            // hitung bobot
            double bobot = tf * idf;
            // set bobot pada posting
            result.get(i).setWeight(bobot);
        }
        Collections.sort(result);
        return result;
    }

    public ArrayList<Posting> search(String query) {
        makeDictionary();

        String tempQuery[] = query.split(" ");
        ArrayList<Posting> result = new ArrayList<>();

        for (int i = 0; i < tempQuery.length; i++) {
            String string = tempQuery[i];
            if (i == 0) {
                result = searchOneWord(string);
            } else {
                ArrayList<Posting> result1 = searchOneWord(tempQuery[i]);
                result = intersection(result, result1);
            }
        }

        return result;
    }

    public ArrayList<Posting> intersection(ArrayList<Posting> p1, ArrayList<Posting> p2) {
        if (p1 == null || p2 == null) {
            return new ArrayList<>();
        }

        ArrayList<Posting> postings = new ArrayList<>();
        int p1Index = 0;
        int p2Index = 0;

        Posting post1 = p1.get(p1Index);
        Posting post2 = p2.get(p2Index);

        while (true) {
            if (post1.getDocument().getId() == post2.getDocument().getId()) {
                try {
                    postings.add(post1);
                    p1Index++;
                    p2Index++;
                    post1 = p1.get(p1Index);
                    post2 = p2.get(p2Index);
                } catch (Exception e) {
                    break;
                }

            } else if (post1.getDocument().getId() < post2.getDocument().getId()) {
                try {
                    p1Index++;
                    post1 = p1.get(p1Index);
                } catch (Exception e) {
                    break;
                }

            } else {
                try {
                    p2Index++;
                    post2 = p2.get(p2Index);
                } catch (Exception e) {
                    break;
                }
            }
        }
        return postings;
    }

    public ArrayList<Posting> searchOneWord(String word) {
        Term tempTerm = new Term(word);
        if (getDictionary().isEmpty()) {
            // dictionary kosong
            return null;
        } else {
            int position = Collections.binarySearch(getDictionary(), tempTerm);
            if (position < 0) {
                //tidak ditemukan
                return null;
            } else {
                return getDictionary().get(position).getTermList().getPostings();
            }
        }
    }

    public void makeDictionaryV2() {
        ArrayList<Posting> list = this.getSortedIndex();

        for (int i = 0; i < list.size(); i++) {
            if (dictionary.isEmpty()) {
                Term term = new Term(list.get(i).getTerm());
                term.getTermList().getPostings().add(list.get(i));
                getDictionary().add(term);
            } else {
                Term tempTerm = new Term(list.get(i).getTerm());

                int position = Collections.binarySearch(getDictionary(), tempTerm);
                if (position < 0) {
                    tempTerm.getTermList().getPostings().add(list.get(i));
                    getDictionary().add(tempTerm);
                } else {

                    getDictionary().get(position).getTermList().getPostings().add(list.get(i));
                    Collections.sort(getDictionary());
                }

                Collections.sort(getDictionary());
            }
        }

    }

    public ArrayList<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(ArrayList<Document> documents) {
        this.documents = documents;
    }

    public int getDocumentFrequency(String term) {
        Term tempTerm = new Term(term);

        int pos = Collections.binarySearch(getDictionary(), tempTerm);
        if (pos >= 0) {
            return getDictionary().get(pos).getTermList().getPostings().size();
        }
        return -1;
    }

    public double getInverseDocumentFrequency(String term) {
        double N = getDocumentSize();
        double n = getDocumentFrequency(term);

        if (n < 0) {
            return 0;
        }

        return Math.log10(N / n);
    }

    public int getTermFrequency(String term, int idDoc) {
        Document document = new Document();
        document.setId(idDoc);
        int pos = Collections.binarySearch(getDocuments(), document);

        if (pos >= 0) {
            ArrayList<Posting> tempPosting = getDocuments().get(pos).getListOfPosting();
            Posting posting = new Posting();
            posting.setTerm(term);
            int postingIndex = Collections.binarySearch(tempPosting, posting);
            if (postingIndex >= 0) {
                return tempPosting.get(postingIndex).getNumberOfTerm();
            }
            return 0;
        }

        return 0;
    }

    public ArrayList<Posting> makeTFIDF(int idDocument) {
        Document doc = new Document();
        doc.setId(idDocument);
        int pos = Collections.binarySearch(getDocuments(), doc);
        if (pos < 0) {
            return null;
        }

//        ArrayList<Term> terms = getDictionary();
        doc = getDocuments().get(pos);

        ArrayList<Posting> result = doc.getListOfPosting();
        for (int i = 0; i < result.size(); i++) {
            // weight = tf * idf
            double weight = result.get(i).getNumberOfTerm() * getInverseDocumentFrequency(result.get(i).getTerm());

            result.get(i).setWeight(weight);
        }

        return result;
    }

    public ArrayList<Posting> makeQueryTFIDF(String query) {
        Document doc = new Document();
        doc.setContent(query);

        ArrayList<Posting> result = doc.getListOfPosting();
        for (int i = 0; i < result.size(); i++) {
            // weight = tf * idf
            double weight = result.get(i).getNumberOfTerm() * getInverseDocumentFrequency(result.get(i).getTerm());

            result.get(i).setWeight(weight);
        }

        return result;
    }

    /**
     * Fungsi untuk menghitung panjang dari sebuah posting
     *
     * @param posting
     * @return
     */
    public double getLengthOfPosting(ArrayList<Posting> posting) {
        double length = 0;
        for (int i = 0; i < posting.size(); i++) {
            length += posting.get(i).getWeight() * posting.get(i).getWeight();
        }

        return Math.sqrt(length);
    }

    /**
     *
     * @param p1 posting list1
     * @param p2 posting list2
     * @return hasil perkalian 2 buah postinglist
     */
    public double getInnerProduct(ArrayList<Posting> p1, ArrayList<Posting> p2) {
        double result = 0;
        for (int i = 0; i < p1.size(); i++) {
            // cari kata yang sama dengan p1 index ke-i
            int pos = Collections.binarySearch(p2, p1.get(i));
            if (pos >= 0) {

                result = result + (p1.get(i).getWeight() * p2.get(pos).getWeight());
            }
        }

        return result;
    }

    /**
     * Fungsi untuk menghitung cosine similarity
     *
     * @param posting
     * @param posting1
     *
     * @return
     */
    public double getCosineSimilarity(ArrayList<Posting> posting,
            ArrayList<Posting> posting1) {

        // hitung inner product dari kedua posting list
        double innerProduct = getInnerProduct(posting, posting1);

        // hitung jarak
        double length = getLengthOfPosting(posting) * getLengthOfPosting(posting1);

        if (length == 0) {
            return 0;
        }

        // hitung cosine similarity
        double cosineSimilarity = innerProduct / length;

        return cosineSimilarity;
    }

    /**
     * Fungsi untuk mencari berdasar nilai TFIDF
     *
     * @param query
     * @return
     */
    public ArrayList<SearchingResult> searchTFIDF(String query) {
        // buat list of searchingResult untuk menampung hasil pencarian
        ArrayList<SearchingResult> searchingResults = new ArrayList<>();

        // hitung tfidf untuk query
        ArrayList<Posting> queryTFIDF = makeQueryTFIDF(query);

        for (int i = 0; i < getDocumentSize(); i++) {
            // hitung tfidf dari dokumen
            ArrayList<Posting> documentTFIDF = makeTFIDF(getDocuments().get(i).getId());

            // hitung inner product antara query dan dokumen
            double similarity = getInnerProduct(queryTFIDF, documentTFIDF);

            if (similarity > 0) {
                // masukkan similarity dan dokumen yang bersangkutan ke list searchingResult
                searchingResults.add(new SearchingResult(similarity, getDocuments().get(i)));
            }

        }

        // urutkan searchingResults berdasar similarity terbesar
        Collections.sort(searchingResults, Collections.reverseOrder());

        return searchingResults;
    }

    /**
     * Fungsi untuk mencari dokumen berdasarkan cosine similarity
     *
     * @param query
     * @return
     */
    public ArrayList<SearchingResult> searchCosineSimilarity(String query) {
        // buat list of searchingResult untuk menampung hasil pencarian
        ArrayList<SearchingResult> searchingResults = new ArrayList<>();

        // hitung tfidf untuk query
        ArrayList<Posting> queryTFIDF = makeQueryTFIDF(query);

        for (int i = 0; i < getDocumentSize(); i++) {
            // hitung tfidf dari dokumen
            ArrayList<Posting> documentTFIDF = makeTFIDF(getDocuments().get(i).getId());

            // similarity
            double similarity = getCosineSimilarity(queryTFIDF, documentTFIDF);

            if (similarity > 0) {
                // masukkan similarity dan dokumen yang bersangkutan ke list searchingResult
                searchingResults.add(new SearchingResult(similarity, getDocuments().get(i)));
            }
        }

        // urutkan searchingResults dari similarity yang paling besar
        Collections.sort(searchingResults, Collections.reverseOrder());

        return searchingResults;
    }

public void readDirectory(File directory) {
        File[] fileNames = directory.listFiles();
        int i = getDocumentSize() + 1;
        for (File currentFile : fileNames) {
            if (currentFile.isDirectory()) {
                readDirectory(currentFile);
            } else {
                Document doc = Document.readFile(i, currentFile);
                addNewDocument(doc);
            }
            i++;
        }
        makeDictionaryWithTermNumber();
    }

}
