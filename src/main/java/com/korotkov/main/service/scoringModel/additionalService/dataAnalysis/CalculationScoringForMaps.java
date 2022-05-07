package com.korotkov.main.service.scoringModel.additionalService.dataAnalysis;

import java.util.ArrayList;

public abstract class CalculationScoringForMaps {
    protected static int setGoodCountForMap(ArrayList<String> arrayParameter, String textSet,
                                            ArrayList<String> arrayGoodBad, String goodResult){
        int goodCountTemp = 0;
        for(int a = 0; a < arrayParameter.size(); a++){
            if(arrayGoodBad.get(a).equals(goodResult) && arrayParameter.get(a).equals(textSet)){
                goodCountTemp += 1;
            }
        }
        return goodCountTemp;
    }

    protected static int setBadCountForMap(ArrayList<String> arrayParameter, String textSet,
                                           ArrayList<String> arrayGoodBad, String badResult){
        int badCountTemp = 0;
        for(int a = 0; a < arrayParameter.size(); a++){
            if(arrayGoodBad.get(a).equals(badResult) && arrayParameter.get(a).equals(textSet)){
                badCountTemp += 1;
            }
        }
        return badCountTemp;
    }

    protected static int setTotalCountForMap(ArrayList<String> arrayParameter, String text){
        int totalCountTemp = 0;
        for(int a = 0; a < arrayParameter.size(); a++){
            if(arrayParameter.get(a).equals(text)){
                totalCountTemp += 1;
            }
        }
        return totalCountTemp;
    }

    protected static double setGoodRateForMap(Integer goodCountForMap, Integer totalCountForMap){
        double goodRateTemp = 0.00;
        if(totalCountForMap > 0){
            if(goodCountForMap > 0){
                goodRateTemp = (double) goodCountForMap / (double) totalCountForMap;
            }
        }
        return goodRateTemp;
    }

    protected static double setBadRateForMap(Integer badCountForMap, Integer totalCountForMap){
        double badRateTemp = 0.00;
        if(totalCountForMap > 0){
            if(badCountForMap > 0){
                badRateTemp = (double) badCountForMap / (double) totalCountForMap;
            }
        }
        return badRateTemp;
    }

    protected static double setPopulationForMap(Integer fromMapGoodBadTotal, Integer commonGoodBadTotal){
        double populationTemp = 0.00;
        if(commonGoodBadTotal > 0){
            if(fromMapGoodBadTotal > 0){
                populationTemp = (double) fromMapGoodBadTotal / (double) commonGoodBadTotal;
            }
        }
        return populationTemp;
    }

    protected static double setPgPbForMap(Double fromMapGiG, Double fromMapBiB){
        double pgPbTemp = 0.00;
        if(fromMapBiB > 0 && fromMapGiG > 0){
            pgPbTemp = fromMapGiG / fromMapBiB;
        }
        return pgPbTemp;
    }
}
