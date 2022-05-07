package com.korotkov.main.service.scoringModel.additionalService.dataAnalysis;

import com.korotkov.main.service.scoringModel.additionalService.dataAnalysis.ScoringParameter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static java.util.Collections.unmodifiableSet;
import static java.util.Collections.unmodifiableMap;

public abstract class CalculateScoringParameter {
    public static ScoringParameter calculateNewParameter(String nameParameter,
                                                         ArrayList<String> arrayGoodBad,
                                                         ArrayList<String> arrayParameter, Integer factor,
                                                         Integer offset, String goodResult, String badResult){
        Integer totalCountTotal = arrayParameter.size();
        Integer goodCountTotal = setGoodCountTotal(arrayGoodBad,goodResult);
        Integer badCountTotal = setBadCountTotal(arrayGoodBad,badResult);
        Double goodRateTotal = setGoodRateTotal(totalCountTotal, goodCountTotal);
        Double badRateTotal = setBadRateTotal(totalCountTotal, badCountTotal);

        Set<String> listUniqueTypesOfParameter = unmodifiableSet(chooseUniqueTypesFromArray(arrayParameter));
        Map<String, Integer> mapTotalCount = unmodifiableMap(setMapTotalCount(arrayParameter,listUniqueTypesOfParameter));
        Map<String, Integer> mapGoodCount = unmodifiableMap(setMapGoodCount(arrayParameter,listUniqueTypesOfParameter,
                arrayGoodBad,goodResult));
        Map<String, Integer> mapBadCount = unmodifiableMap(setMapBadCount(arrayParameter,listUniqueTypesOfParameter,
                arrayGoodBad,badResult));
        Map<String, Double> mapGoodRate = unmodifiableMap(setMapGoodRate(listUniqueTypesOfParameter,mapTotalCount,
                mapGoodCount));
        Map<String, Double> mapBadRate = unmodifiableMap(setMapBadRate(listUniqueTypesOfParameter,mapTotalCount,
                mapBadCount));
        Map<String, Double> mapPopulationGoodPercent = unmodifiableMap(setPopulation(listUniqueTypesOfParameter,
                mapGoodCount,goodCountTotal));
        Map<String, Double> mapPopulationBadPercent = unmodifiableMap(setPopulation(listUniqueTypesOfParameter,
                mapBadCount,badCountTotal));
        Map<String, Double> mapPopulationTotalPercent = unmodifiableMap(setPopulation(listUniqueTypesOfParameter,
                mapTotalCount, totalCountTotal));
        Map<String, Double> mapPgPb = unmodifiableMap(setPgPb(listUniqueTypesOfParameter,mapPopulationGoodPercent,
                mapPopulationBadPercent));
        Map<String, Double> mapWOE = unmodifiableMap(setWOE(listUniqueTypesOfParameter,mapPgPb));
        Map<String, Double> mapIV = unmodifiableMap(setIV(listUniqueTypesOfParameter, mapPopulationGoodPercent,
                mapPopulationBadPercent, mapWOE));
        Double ivTotal = setIvTotal(listUniqueTypesOfParameter,mapIV);
        Map<String, Integer> mapScore = unmodifiableMap(setScore(listUniqueTypesOfParameter, mapWOE,factor,offset));
        return new ScoringParameter(nameParameter,goodCountTotal,badCountTotal,totalCountTotal,ivTotal,goodRateTotal,
                badRateTotal,listUniqueTypesOfParameter,mapTotalCount,mapGoodCount,mapBadCount,mapGoodRate,
                mapBadRate,mapPopulationGoodPercent,mapPopulationBadPercent,mapPopulationTotalPercent,
                mapPgPb,mapWOE,mapIV,mapScore);
    }

    private static Set<String> chooseUniqueTypesFromArray(ArrayList<String> arrayParameter){
        Set<String> uniqueTypesFromArray = new HashSet<>();
        for(int a = 0; a < arrayParameter.size(); a++){
            uniqueTypesFromArray.add(arrayParameter.get(a));
        }
        return uniqueTypesFromArray;
    }

    private static int setGoodCountTotal(ArrayList<String> arrayGoodBad, String goodResult){
        int goodCountTemp = 0;
        for(int a = 0; a < arrayGoodBad.size(); a++){
            if(arrayGoodBad.get(a).equals(goodResult)){
                goodCountTemp += 1;
            }
        }
        return goodCountTemp;
    }

    private static int setBadCountTotal(ArrayList<String> arrayGoodBad, String badResult){
        int badCountTemp = 0;
        for(int a = 0; a < arrayGoodBad.size(); a++){
            if(arrayGoodBad.get(a).equals(badResult)){
                badCountTemp += 1;
            }
        }
        return badCountTemp;
    }

    private static double setGoodRateTotal(Integer totalCountTotalSet, Integer goodCountTotalSet){
        double goodRateTotalSet = 0.00;
        if(totalCountTotalSet > 0){
            if(goodCountTotalSet > 0){
                goodRateTotalSet = (double) goodCountTotalSet / (double) totalCountTotalSet;
            }
        }
        return goodRateTotalSet;
    }

    private static double setBadRateTotal(Integer totalCountTotalSet, Integer badCountTotalSet){
        double badRateTotalSet = 0.00;
        if(totalCountTotalSet > 0){
            if(badCountTotalSet > 0){
                badRateTotalSet = (double) badCountTotalSet / (double) totalCountTotalSet;
            }
        }
        return badRateTotalSet;
    }

    private static Map<String, Integer> setMapGoodCount (ArrayList<String> arrayParameter,
                                                         Set<String> uniqueTypesOfParameter,
                                                         ArrayList<String> arrayGoodBad,
                                                         String goodResult){
        Map<String, Integer> mapGoodCountSet = new HashMap<>();
        for(String text : uniqueTypesOfParameter){
            mapGoodCountSet.put(text, CalculationScoringForMaps.setGoodCountForMap(arrayParameter, text, arrayGoodBad,
                    goodResult));
        }
        return mapGoodCountSet;
    }

    private static Map<String, Integer> setMapBadCount (ArrayList<String> arrayParameter,
                                                        Set<String> uniqueTypesOfParameter,
                                                        ArrayList<String> arrayGoodBad,
                                                        String badResult){
        Map<String, Integer> mapBadCountSet = new HashMap<>();
        for(String text : uniqueTypesOfParameter){
            mapBadCountSet.put(text, CalculationScoringForMaps.setBadCountForMap(arrayParameter, text, arrayGoodBad,
                    badResult));
        }
        return mapBadCountSet;
    }

    private static Map<String, Integer> setMapTotalCount(ArrayList<String> arrayParameter,
                                                         Set<String> uniqueTypesOfParameter){
        Map<String, Integer> mapTotalCountSet = new HashMap<>();
        for(String text : uniqueTypesOfParameter){
            mapTotalCountSet.put(text, CalculationScoringForMaps.setTotalCountForMap(arrayParameter, text));
        }
        return mapTotalCountSet;
    }

    private static Map<String, Double> setMapGoodRate(Set<String> uniqueTypesOfParameter,
                                                      Map<String, Integer> totalCountMap,
                                                      Map<String, Integer> goodCountMap){
        Map<String, Double> mapGoodRateSet = new HashMap<>();
        for(String text : uniqueTypesOfParameter){
            if(totalCountMap.containsKey(text)){
                if(goodCountMap.containsKey(text)){
                    mapGoodRateSet.put(text, CalculationScoringForMaps.setGoodRateForMap(goodCountMap.get(text),
                            totalCountMap.get(text)));
                } else {
                    mapGoodRateSet.put(text, 0.00);
                }
            } else {
                mapGoodRateSet.put(text, 0.00);
            }
        }
        return mapGoodRateSet;
    }

    private static Map<String, Double> setMapBadRate(Set<String> uniqueTypesOfParameter,
                                                     Map<String, Integer> totalCountMap,
                                                     Map<String, Integer> badCountMap){
        Map<String, Double> mapBadRateSet = new HashMap<>();
        for(String text : uniqueTypesOfParameter){
            if(totalCountMap.containsKey(text)){
                if(badCountMap.containsKey(text)){
                    mapBadRateSet.put(text, CalculationScoringForMaps.setBadRateForMap(badCountMap.get(text),
                            totalCountMap.get(text)));
                } else {
                    mapBadRateSet.put(text, 0.00);
                }
            } else {
                mapBadRateSet.put(text, 0.00);
            }
        }
        return mapBadRateSet;
    }

    private static Map<String, Double> setPopulation(Set<String> uniqueTypesOfParameter,
                                                     Map<String, Integer> countMapTotalOrGoodOrBad,
                                                     Integer countCommonTotalOrGoodOrBad){
        Map<String, Double> mapPopulationSet = new HashMap<>();
        for(String text : uniqueTypesOfParameter){
            if(countCommonTotalOrGoodOrBad > 0){
                if(countMapTotalOrGoodOrBad.containsKey(text)){
                    mapPopulationSet.put(text,
                            CalculationScoringForMaps.setPopulationForMap(countMapTotalOrGoodOrBad.get(text),
                                    countCommonTotalOrGoodOrBad));
                } else {
                    mapPopulationSet.put(text, 0.00);
                }
            } else {
                mapPopulationSet.put(text, 0.00);
            }
        }
        return mapPopulationSet;
    }

    private static Map<String, Double> setPgPb(Set<String> uniqueTypesOfParameter,
                                               Map<String, Double> mapGiG,
                                               Map<String, Double> mapBiB){
        Map<String, Double> mapPgPbSet = new HashMap<>();
        for(String text : uniqueTypesOfParameter){
            mapPgPbSet.put(text,CalculationScoringForMaps.setPgPbForMap(mapGiG.get(text),mapBiB.get(text)));
        }
        return mapPgPbSet;
    }

    private static Map<String, Double> setWOE(Set<String> uniqueTypesOfParameter,
                                              Map<String, Double> mapPgPbForSet){
        Map<String, Double> mapWoeSet = new HashMap<>();
        for(String text : uniqueTypesOfParameter){
            if(mapPgPbForSet.get(text) > 0){
                mapWoeSet.put(text, Math.log(mapPgPbForSet.get(text)));
            } else {
                mapWoeSet.put(text, 0.00);
            }
        }
        return mapWoeSet;
    }

    private static Map<String, Double> setIV(Set<String> uniqueTypesOfParameter,
                                             Map<String, Double> mapGiG,
                                             Map<String, Double> mapBiB,
                                             Map<String, Double> mapWoE){
        Map<String, Double> mapIVSet = new HashMap<>();
        for(String text : uniqueTypesOfParameter) {
            mapIVSet.put(text, (mapGiG.get(text) - mapBiB.get(text)) * mapWoE.get(text));
        }
        return mapIVSet;
    }

    private static double setIvTotal(Set<String> uniqueTypesOfParameter, Map<String, Double> mapIv){
        double ivSet = 0.00;
        for(String text : uniqueTypesOfParameter){
            ivSet += mapIv.get(text);
        }
        return ivSet;
    }

    private static Map<String, Integer> setScore(Set<String> uniqueTypesOfParameter,
                                                 Map<String, Double> mapWoeForScoring,
                                                 Integer factorForSet,
                                                 Integer offsetForSet){
        Map<String, Integer> scoreSet = new HashMap<>();
        for(String text : uniqueTypesOfParameter) {
            if(mapWoeForScoring.get(text) != 0.00){
                scoreSet.put(text, (int) Math.round((mapWoeForScoring.get(text) * factorForSet) + offsetForSet));
            } else {
                scoreSet.put(text, 0);
            }
        }
        return scoreSet;
    }
}
