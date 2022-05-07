package com.korotkov.main.service.scoringModel.testingCore;

import com.korotkov.main.model.UserAccount;

import java.util.ArrayList;

public interface TestingCalculationService {
    void createNewTest(ArrayList<ArrayList<String>> listFromFileExcel, UserAccount userAccount);
}
