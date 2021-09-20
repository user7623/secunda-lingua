package com.example.diplomska;

import java.sql.Array;

public class translations {
    public static String[] sentencesArrayGlobal = new String[]{"Hello", "Bye", "Good evening", "My name is John Smith", "Im 20 years old", "I live in London",
      "Im hungry", "Im thirsty", "Where is the train station?", "Nice weather"};
    public static String[] translationsArrayGlobal = new String[]{"Zdravo" , "Cao", "Dobra vecer" , "Jas se vikam John Smith",
            "jas imam 20 godini", "Jas ziveam vo London" , "Jas sum gladen" , "Jas sum zeden" , "Kade e zeleznickata stanica" , "Ubavo vreme"};
    public static String[] alternativeTranslationsarrayGlobal = new String[]{"Zdravo", "Prijatno", "prijatna vecer" , "Moeto ime e John Smith" ,
    "jas sum 20 godini star" , "Ziveam vo London" , "Gladen sum" , "Zeden sum" , "Kade e zeleznickata" , "Prijatno vreme"};

    public static String[] imagesIdsGlobal = new String[]{"book" , "car" , "cat", "dog" , "food" , "house" , "pc" ,
            "pencil" , "phone" , "phone"};

    public static void setSentencesArrayGlobal(String[] sentencesArrayGlobal) {
        translations.sentencesArrayGlobal = sentencesArrayGlobal;
    }

    public static void setTranslationsArrayGlobal(String[] translationsArrayGlobal) {
        translations.translationsArrayGlobal = translationsArrayGlobal;
    }

    public static void setAlternativeTranslationsarrayGlobal(String[] alternativeTranslationsarrayGlobal) {
        translations.alternativeTranslationsarrayGlobal = alternativeTranslationsarrayGlobal;
    }

    public static String[] getImagesIdsGlobal(){return imagesIdsGlobal;}

    public static String[] getSentencesArrayGlobal() {
        return sentencesArrayGlobal;
    }

    public static String[] getTranslationsArrayGlobal() {
        return translationsArrayGlobal;
    }

    public static String[] getAlternativeTranslationsarrayGlobal() {
        return alternativeTranslationsarrayGlobal;
    }
}
