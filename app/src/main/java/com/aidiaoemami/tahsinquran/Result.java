package com.aidiaoemami.tahsinquran;

import android.util.Log;

import java.text.Normalizer;

public class Result {
    char star, end;
    String pattern;
    String sebab;


    public Result(char star, char end, String pattern, String sebab) {
        this.star = star;
        this.end = end;
        this.pattern = pattern;
        this.sebab = sebab;
    }

    public char getStar() {
        return star;
    }

    public char getEnd() {
        return end;
    }

    public String getPattern() {
        return pattern;
    }

    public String getSebab() {
        return sebab;
    }

    public static Result prepocessingArabic(String pattern){
        String diacless=null, newPattern = null, sebab=null;
        pattern = pattern.replaceAll("\\s", "");
        int length = pattern.length();
        int indexN=0;
        char start=0, end=0;
        char m;
        if (pattern.contains("نْ") ){
//            diacless = Normalizer.normalize(pattern, Normalizer.Form.NFKD).replaceAll("\\s", "");
//            diacless = Normalizer.normalize(pattern, Normalizer.Form.NFKD).replaceAll("\\s", "").
//                    replaceAll("\\p{M}", "");
            for (int x = 0; x<length;x++){
                if (pattern.charAt(x) == 'ن' && pattern.charAt(x+1) == 'ْ' )
                    indexN = x;

                if (indexN>0)
                    break;
            }

            sebab = "Nun mati bertemu dengan huruf";
            start = pattern.charAt(indexN);
            end = pattern.charAt(indexN+2);


            newPattern = pattern.substring(indexN, indexN+3);

            Log.d("masuk if" , "  "+end +" dan " +start);
        }
//        else if (pattern.contains("ن") && pattern.charAt(length-1) != 'ن' || pattern.contains("ن") && pattern.charAt(0) != 'ن'){
//            diacless = Normalizer.normalize(pattern, Normalizer.Form.NFKD).replaceAll("\\s", "").
//                    replaceAll("\\p{M}", "");
//            for (int x = 0; x<length;x++){
//                m = diacless.charAt(x);
//                if (m == 'ن' && x>0 && x!=length-1 ){
//                    indexN = x;
//                }
//                if (indexN>0)
//                    break;
//            }
//
//            sebab = "Nun mati bertemu dengan huruf";
//            start = diacless.charAt(indexN);
//            end = diacless.charAt(indexN+2);
//            Log.d("masuk else if 1" , "masuk else if 1");
//
//            newPattern = diacless.substring(indexN, indexN+2);
//        }
//        else if (pattern.contains(" ")){
//            diacless = Normalizer.normalize(pattern, Normalizer.Form.NFKD).
//                    replaceAll("\\p{M}", "");
//            for (int x = 0; x<length;x++){
//                m = diacless.charAt(x);
//                if (m == ' ' && x>0 && x!=length-1){
//                    indexN = x;
//                }
//
//                if (indexN>0)
//                    break;
//            }
//
//            sebab = "Tanwin bertemu dengan huruf";
//            start = diacless.charAt(indexN);
////            end = diacless.charAt(indexN+2);
////            diacless = Normalizer.normalize(pattern, Normalizer.Form.NFKD).replaceAll("\\s", "ن");
//            newPattern = diacless.substring(indexN, indexN+2);
//
//            Log.d("masuk else if 1" , "masuk else if 1");
//        }
        else if (pattern.contains("ٌ") || pattern.contains("ٍ")){
//            diacless = Normalizer.normalize(pattern, Normalizer.Form.NFKD).
//                    replaceAll("\\p{M}", "");
            for (int x = 0; x<length;x++){
                if (pattern.charAt(x) == 'ٌ' || pattern.charAt(x+1) == 'ٍ' )
                    indexN = x;

                if (indexN>0)
                    break;
            }

            sebab = "Tanwin mati bertemu dengan huruf";
            start = pattern.charAt(indexN);
            end = pattern.charAt(indexN+2);


            newPattern = pattern.substring(indexN, indexN+3);

            Log.d("masuk else if 2" , "masuk else if 2"+end +" dan " +start);
        }
        else if (pattern.contains("ً")){
//            diacless = Normalizer.normalize(pattern, Normalizer.Form.NFKD).
//                    replaceAll("\\p{M}", "");
            for (int x = 0; x<length;x++){
                if (pattern.charAt(x) == 'ً' )
                    indexN = x;

                if (indexN>0)
                    break;
            }

            sebab = "Tanwin mati bertemu dengan huruf";
            start = pattern.charAt(indexN);
            end = pattern.charAt(indexN+2);


            newPattern = pattern.substring(indexN+1, indexN+3);

            Log.d("masuk else if 3" , "masuk else if 3" +end +" dan " +start);
        }
        else{
            start =0;
            end = 0;
            newPattern = "Bukan Contoh Hukum Nun Mati atau Tanwin";
            sebab = "";
        }
        return new Result(start, end, newPattern, sebab);


    }
}
