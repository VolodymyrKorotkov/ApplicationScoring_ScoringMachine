package com.korotkov.main.service.scoringModel.additionalService.dataAnalysis;

import java.util.Map;
import java.util.Set;

public class ScoringParameter {
    private final String nameParameter;
    private final int goodCountTotal;
    private final int badCountTotal;
    private final int totalCountTotal;
    private final double ivTotal;

    private final double goodRateTotal;
    private final double badRateTotal;

    private final Set<String> listTitleOfParameter;
    private final Map<String, Integer> mapTotalCount;
    private final Map<String, Integer> mapGoodCount;
    private final Map<String, Integer> mapBadCount;
    private final Map<String, Double> mapGoodRate;
    private final Map<String, Double> mapBadRate;
    private final Map<String, Double> mapPopulationGoodPercent;
    private final Map<String, Double> mapPopulationBadPercent;
    private final Map<String, Double> mapPopulationTotalPercent;

    private final Map<String, Double> mapPgPb;
    private final Map<String, Double> mapWOE;
    private final Map<String, Double> mapIV;
    private final Map<String, Integer> mapScore;

    public ScoringParameter(String nameParameter, int goodCountTotal, int badCountTotal, int totalCountTotal,
                            double ivTotal, double goodRateTotal, double badRateTotal, Set<String> listTitleOfParameter,
                            Map<String, Integer> mapTotalCount, Map<String, Integer> mapGoodCount,
                            Map<String, Integer> mapBadCount, Map<String, Double> mapGoodRate,
                            Map<String, Double> mapBadRate, Map<String, Double> mapPopulationGoodPercent,
                            Map<String, Double> mapPopulationBadPercent, Map<String, Double> mapPopulationTotalPercent,
                            Map<String, Double> mapPgPb, Map<String, Double> mapWOE, Map<String, Double> mapIV,
                            Map<String, Integer> mapScore){
        this.nameParameter = nameParameter;
        this.goodCountTotal = goodCountTotal;
        this.badCountTotal = badCountTotal;
        this.totalCountTotal = totalCountTotal;
        this.ivTotal = ivTotal;
        this.goodRateTotal = goodRateTotal;
        this.badRateTotal = badRateTotal;
        this.listTitleOfParameter = listTitleOfParameter;
        this.mapTotalCount = mapTotalCount;
        this.mapGoodCount = mapGoodCount;
        this.mapBadCount = mapBadCount;
        this.mapGoodRate = mapGoodRate;
        this.mapBadRate = mapBadRate;
        this.mapPopulationGoodPercent = mapPopulationGoodPercent;
        this.mapPopulationBadPercent = mapPopulationBadPercent;
        this.mapPopulationTotalPercent = mapPopulationTotalPercent;
        this.mapPgPb = mapPgPb;
        this.mapWOE = mapWOE;
        this.mapIV = mapIV;
        this.mapScore = mapScore;
    }

    public String getNameParameter(){
        return nameParameter;
    }

    public int getGoodCountTotal(){
        return goodCountTotal;
    }

    public int getBadCountTotal(){
        return badCountTotal;
    }

    public int getTotalCountTotal(){
        return totalCountTotal;
    }

    public double getIvTotal(){
        return ivTotal;
    }

    public double getGoodRateTotal(){
        return goodRateTotal;
    }

    public double getBadRateTotal(){
        return badRateTotal;
    }

    public Set<String> getListTitleOfParameter(){
        return listTitleOfParameter;
    }

    public Map<String, Integer> getMapTotalCount(){
        return mapTotalCount;
    }

    public Map<String, Integer> getMapGoodCount(){
        return mapGoodCount;
    }

    public Map<String, Integer> getMapBadCount(){
        return mapBadCount;
    }

    public Map<String, Double> getMapGoodRate(){
        return mapGoodRate;
    }

    public Map<String, Double> getMapBadRate(){
        return mapBadRate;
    }

    public Map<String, Double> getMapPopulationGoodPercent(){
        return mapPopulationGoodPercent;
    }

    public Map<String, Double> getMapPopulationBadPercent(){
        return mapPopulationBadPercent;
    }

    public Map<String, Double> getMapPopulationTotalPercent(){
        return mapPopulationTotalPercent;
    }

    public Map<String, Double> getMapPgPb(){
        return mapPgPb;
    }

    public Map<String, Double> getMapWOE(){
        return mapWOE;
    }

    public Map<String, Double> getMapIV(){
        return mapIV;
    }

    public Map<String, Integer> getMapScore(){
        return mapScore;
    }
}
