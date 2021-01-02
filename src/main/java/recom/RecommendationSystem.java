package recom;

import com.project.socializingApp.model.PhotoModel;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class RecommendationSystem {

    private double inverseDocumentFrequency(List<String> documents, String term) {
        double idf = 0;
        double size = documents.size();
        double c = 0;
        term = term.toLowerCase();
        for (String doc : documents) {

            if (doc.replace('.', ' ').replace(',', ' ').replace(':', ' ').replace(';', ' ')
                    .replace('(', ' ').replace(')', ' ').replace('[', ' ').replace(']', ' ')
                    .replace('{', ' ').replace('}', ' ').replace('!', ' ').replace('?', ' ').toLowerCase().contains(term))
                c++;
        }
        idf = Math.log10((1 + size) / (1 + c) + 1);
        return idf;
    }

    private double frequencyInDocument(String document, String term) {
        document = document.replace('.', ' ').replace(',', ' ').replace(':', ' ').replace(';', ' ')
                .replace('(', ' ').replace(')', ' ').replace('[', ' ').replace(']', ' ')
                .replace('{', ' ').replace('}', ' ').replace('!', ' ').replace('?', ' ').toLowerCase();
        term = term.toLowerCase();
        Pattern p = Pattern.compile(term);
        Matcher m = p.matcher(document);
        double count = 0;
        while (m.find()) {
            count++;
        }
        return count;
    }

    private double mediumTermFrequency(List<String> documents, String term) {
        double tfm = 0;
        double size = documents.size();
        term = term.toLowerCase();
        for (String doc : documents) {
            tfm += frequencyInDocument(doc, term) / doc.length();
        }
        return tfm / size;
    }

    private double similarity(List<String> liked, List<String> disliked, String term) {
        return mediumTermFrequency(liked, term) * inverseDocumentFrequency(liked, term) - mediumTermFrequency(disliked, term) * inverseDocumentFrequency(disliked, term);
    }

    private boolean checkStopWord(String term) throws FileNotFoundException {
        String s = "";
        File file = new File("stop_words_english.txt");
        Scanner sc = new Scanner(file);
        while (sc.hasNextLine()) {
            s += " " + sc.nextLine();
        }
        if (s.contains(term)) return false;
        return true;
    }

    public List<PhotoModel> recommendations(Integer index, List<PhotoModel> descriptions, List<PhotoModel> liked, List<PhotoModel> disliked) throws FileNotFoundException {
        HashMap<PhotoModel,Double> recommendations = new HashMap<PhotoModel,Double>();
        List<PhotoModel> photoModels;
        if(index > 0) {
            photoModels = new ArrayList<>(descriptions.subList(0, index+1));
        }else{
            photoModels = new ArrayList<>();
            photoModels.add(descriptions.get(0));
        }
        List<String> likedDescription = new ArrayList<String>();
        List<String> dislikedDescription = new ArrayList<>();
        likedDescription.add("the");
        dislikedDescription.add("the");
        if(!liked.isEmpty()){
            likedDescription = liked.stream()
                    .map(PhotoModel::getDescription)
                    .collect(Collectors.toList());
        }
        if(!disliked.isEmpty()){
            dislikedDescription = disliked.stream()
                    .map(PhotoModel::getDescription)
                    .collect(Collectors.toList());
        }
        for (PhotoModel photoModel : descriptions.subList(index+1, descriptions.size())) {
            String description = photoModel.getDescription();
            double simillarityCoefficient = 0;
            String[] terms = description.replace('.', ' ').replace(',', ' ').replace(':', ' ').replace(';', ' ')
                    .replace('(', ' ').replace(')', ' ').replace('[', ' ').replace(']', ' ')
                    .replace('{', ' ').replace('}', ' ').replace('!', ' ').replace('?', ' ').split(" ");
            for (String term : terms) {
                if (checkStopWord(term)) {
                    simillarityCoefficient+=similarity( likedDescription,dislikedDescription,term);
                }
            }
            recommendations.put(photoModel,simillarityCoefficient);
        }
        List<Double> values = new ArrayList<>(recommendations.values());
        Collections.sort(values);
        Collections.reverse(values);
        photoModels.addAll(recommendations.keySet());
        //System.out.println(recommendations.toString());
        return photoModels;
    }
}
