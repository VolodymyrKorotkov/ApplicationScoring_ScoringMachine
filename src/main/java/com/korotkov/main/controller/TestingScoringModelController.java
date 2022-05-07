package com.korotkov.main.controller;

import com.korotkov.main.config.PortalConstants;
import com.korotkov.main.model.ScoringSettingsModel;
import com.korotkov.main.model.TestScoringModel;
import com.korotkov.main.model.TestScoringModelResult;
import com.korotkov.main.model.UserAccount;
import com.korotkov.main.service.email.EmailService;
import com.korotkov.main.service.file.ExcelReader;
import com.korotkov.main.service.scoringModel.additionalService.additionalEntityForPortal.EntityForExportFile.TestOfModelExportFile;
import com.korotkov.main.service.scoringModel.scoringModelService.ScoringModelService;
import com.korotkov.main.service.scoringModel.scoringSettings.ScoringSettingsModelService;
import com.korotkov.main.service.scoringModel.testScoringModel.TestScoringModelService;
import com.korotkov.main.service.scoringModel.testScoringModelResult.TestScoringModelResultService;
import com.korotkov.main.service.scoringModel.testingCore.TestingCalculationService;
import com.korotkov.main.service.userAccount.UserAccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class TestingScoringModelController implements PortalConstants {
    private static final Logger logger = LoggerFactory.getLogger(TestingScoringModelController.class);

    ScoringSettingsModelService scoringSettingsModelService;
    ScoringModelService scoringModelService;
    TestScoringModelService testScoringModelService;
    UserAccountService userAccountService;
    TestingCalculationService testingCalculationService;
    TestScoringModelResultService testScoringModelResultService;

    @Autowired
    public void setScoringSettingsModelService(ScoringSettingsModelService scoringSettingsModelService){
        this.scoringSettingsModelService = scoringSettingsModelService;
    }

    @Autowired
    public void setScoringModelService(ScoringModelService scoringModelService){
        this.scoringModelService = scoringModelService;
    }

    @Autowired
    public void setTestScoringModelService(TestScoringModelService testScoringModelService){
        this.testScoringModelService = testScoringModelService;
    }

    @Autowired
    public void setUserAccountService(UserAccountService userAccountService){
        this.userAccountService = userAccountService;
    }

    @Autowired
    public void setTestingCalculationService(TestingCalculationService testingCalculationService){
        this.testingCalculationService = testingCalculationService;
    }

    @Autowired
    public void setTestScoringModelResultService(TestScoringModelResultService testScoringModelResultService){
        this.testScoringModelResultService = testScoringModelResultService;
    }

    @PreAuthorize("hasAnyAuthority(T(com.korotkov.main.enums.UserRoleEnum).LEVEL_THREE," +
            "T(com.korotkov.main.enums.UserRoleEnum).LEVEL_FOUR," +
            "T(com.korotkov.main.enums.UserRoleEnum).LEVEL_FIVE)")
    @RequestMapping(value = "/testing-model/scoring-model-test-settings/view", method = RequestMethod.GET)
    public ModelAndView testingScoringModelViewPage(@AuthenticationPrincipal UserAccount userAccount,
                                                    @RequestParam(defaultValue = "NaN") String message){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/testingScoringModel/testingScoringModelSettingsView");
        modelAndView.addObject("scoringSettingsModel", scoringSettingsModelService.findByUser(userAccount));
        if(message.equals("defaultSettings")){
            modelAndView.addObject("message","testingScoringModelSettingsPage.messageSuccessRestoredDefaultSettings");
        } else if(message.equals("changedData")){
            modelAndView.addObject("message","testingScoringModelSettingsPage.messageSuccessChangeData");
        }
        return modelAndView;
    }

    @PreAuthorize("hasAnyAuthority(T(com.korotkov.main.enums.UserRoleEnum).LEVEL_THREE," +
            "T(com.korotkov.main.enums.UserRoleEnum).LEVEL_FOUR," +
            "T(com.korotkov.main.enums.UserRoleEnum).LEVEL_FIVE)")
    @RequestMapping(value = "/testing-model/scoring-model-test-settings/restore-default-settings", method = RequestMethod.GET)
    public ModelAndView testingScoringModelRestoreDefaultSettings(@AuthenticationPrincipal UserAccount userAccount){
        ModelAndView modelAndView = new ModelAndView();
        scoringSettingsModelService.restoreDefaultTestingModelSettings(userAccount);
        modelAndView.setViewName("redirect:/testing-model/scoring-model-test-settings/view?message=defaultSettings");
        return modelAndView;
    }

    @PreAuthorize("hasAnyAuthority(T(com.korotkov.main.enums.UserRoleEnum).LEVEL_THREE," +
            "T(com.korotkov.main.enums.UserRoleEnum).LEVEL_FOUR," +
            "T(com.korotkov.main.enums.UserRoleEnum).LEVEL_FIVE)")
    @RequestMapping(value = "/testing-model/scoring-model-test-settings/edit", method = RequestMethod.GET)
    public ModelAndView testingScoringModelEditPage(@AuthenticationPrincipal UserAccount userAccount){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/testingScoringModel/testingScoringModelSettingsEdit");
        modelAndView.addObject("scoringSettingsModel", scoringSettingsModelService.findByUser(userAccount));
        return modelAndView;
    }

    @PreAuthorize("hasAnyAuthority(T(com.korotkov.main.enums.UserRoleEnum).LEVEL_THREE," +
            "T(com.korotkov.main.enums.UserRoleEnum).LEVEL_FOUR," +
            "T(com.korotkov.main.enums.UserRoleEnum).LEVEL_FIVE)")
    @RequestMapping(value = "/testing-model/scoring-model-test-settings/edit", method = RequestMethod.POST)
    public ModelAndView testingScoringModelEditPagePost(@AuthenticationPrincipal UserAccount userAccount,
                                                        @ModelAttribute("scoringSettingsModel")
                                                        ScoringSettingsModel scoringSettingsModel){
        ModelAndView modelAndView = new ModelAndView();
        if(scoringSettingsModel.getGoodResult().isBlank() || scoringSettingsModel.getGoodResult().isEmpty()){
            modelAndView.setViewName("/testingScoringModel/testingScoringModelSettingsEdit");
            modelAndView.addObject("goodResultError","modelCreationSettingsPage.errorEmptyField");
            return modelAndView;
        }
        if(scoringSettingsModel.getBadResult().isBlank() || scoringSettingsModel.getBadResult().isEmpty()){
            modelAndView.setViewName("/testingScoringModel/testingScoringModelSettingsEdit");
            modelAndView.addObject("badResultError","modelCreationSettingsPage.errorEmptyField");
            return modelAndView;
        }
        if(scoringSettingsModel.getNumberWishedRowsForCalcTestModel() == null){
            modelAndView.setViewName("/testingScoringModel/testingScoringModelSettingsEdit");
            modelAndView.addObject("numberWishedCalcRowsError","modelCreationSettingsPage.errorEmptyField");
            return modelAndView;
        }
        if(scoringSettingsModel.getNumberWishedRowsForCalcTestModel() < 10){
            modelAndView.setViewName("/testingScoringModel/testingScoringModelSettingsEdit");
            modelAndView.addObject("numberWishedCalcRowsError", "testingScoringModelSettingsPage.errorValueCanNotBeLess10");
            return modelAndView;
        }
        scoringSettingsModelService.updateTestingModelSettings(userAccount, scoringSettingsModel);
        modelAndView.setViewName("redirect:/testing-model/scoring-model-test-settings/view?message=changedData");
        return modelAndView;
    }

    @PreAuthorize("hasAnyAuthority(T(com.korotkov.main.enums.UserRoleEnum).LEVEL_THREE," +
            "T(com.korotkov.main.enums.UserRoleEnum).LEVEL_FOUR," +
            "T(com.korotkov.main.enums.UserRoleEnum).LEVEL_FIVE)")
    @RequestMapping(value = "/testing-model/creating-new-test", method = RequestMethod.GET)
    public ModelAndView creatingNewTestPage(@AuthenticationPrincipal UserAccount userAccount,
                                            @RequestParam(defaultValue = "NaN") String action){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/testingScoringModel/createNewTest");
        if(!testScoringModelService.isPossibleBuildNewTest(userAccount)){
            modelAndView.addObject("canBuildTestModel", "false");
            return modelAndView;
        }
        modelAndView.addObject("canBuildTestModel", "true");
        modelAndView.addObject("scoringModelForTest", scoringModelService.findActiveModelByUser(userAccount));
        modelAndView.addObject("countMaxPossibleSavedTests", userAccountService.getCountMaxPossibleSavedTest(userAccount));
        modelAndView.addObject("countCurrentSavedTests", userAccountService.getCurrentCountSavedTests(userAccount));
        if(action.equals("activated-model-and-test")){
            modelAndView.addObject("message", "creatingTestModelPage.messageActivateModelAnNewTest");
        } else if (action.equals("fileIsEmpty")) {
            modelAndView.addObject("fileError","modelCreationPage.fileErrorFileIsEmpty");
        } else if (action.equals("fileNotExcel")) {
            modelAndView.addObject("fileError","modelCreationPage.fileErrorFileIsNotNeedFormat");
        } else if (action.equals("fileException")) {
            modelAndView.addObject("fileError","modelCreationPage.fileErrorProcessFile");
        }
        return modelAndView;
    }

    @PreAuthorize("hasAnyAuthority(T(com.korotkov.main.enums.UserRoleEnum).LEVEL_THREE," +
            "T(com.korotkov.main.enums.UserRoleEnum).LEVEL_FOUR," +
            "T(com.korotkov.main.enums.UserRoleEnum).LEVEL_FIVE)")
    @RequestMapping(value = "/testing-model/started-creating-new-test", method = RequestMethod.GET)
    public ModelAndView startedCreationNewTestPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/testingScoringModel/startedCreatingTest");
        return modelAndView;
    }

    @PreAuthorize("hasAnyAuthority(T(com.korotkov.main.enums.UserRoleEnum).LEVEL_THREE," +
            "T(com.korotkov.main.enums.UserRoleEnum).LEVEL_FOUR," +
            "T(com.korotkov.main.enums.UserRoleEnum).LEVEL_FIVE)")
    @RequestMapping(value = "/upload-new-file-for-creating-test", method = RequestMethod.POST)
    public ModelAndView handleFileUploadForCreatingNewTest(@RequestParam("file") MultipartFile file,
                                                           @AuthenticationPrincipal UserAccount userAccount){
        ModelAndView modelAndView = new ModelAndView();
        if(!testScoringModelService.isPossibleBuildNewTest(userAccount)){
            modelAndView.setViewName("redirect:/testing-model/creating-new-test");
            return modelAndView;
        }
        if(file.isEmpty()){
            modelAndView.setViewName("redirect:/testing-model/creating-new-test?action=fileIsEmpty");
            return modelAndView;
        }
        if(!file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1).toLowerCase().equals("xls") &&
        !file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1).toLowerCase().equals("xlsx")){
            modelAndView.setViewName("redirect:/testing-model/creating-new-test?action=fileNotExcel");
            return modelAndView;
        }
        try {
            testingCalculationService.createNewTest(ExcelReader.readFileExcel(file),
                    userAccount);
        } catch (IOException e) {
            logger.error("Error excelFileReader, test, client id: " + userAccount.getId() + " - " + e.getMessage());
            modelAndView.setViewName("redirect:/testing-model/creating-new-test?action=fileException");
            return modelAndView;
        }
        modelAndView.setViewName("redirect:/testing-model/started-creating-new-test");
        return modelAndView;
    }

    @PreAuthorize("hasAnyAuthority(T(com.korotkov.main.enums.UserRoleEnum).LEVEL_THREE," +
            "T(com.korotkov.main.enums.UserRoleEnum).LEVEL_FOUR," +
            "T(com.korotkov.main.enums.UserRoleEnum).LEVEL_FIVE)")
    @RequestMapping(value = "/testing-model/scoring-model-test-list", method = RequestMethod.GET)
    public ModelAndView testingScoringModelListView(@AuthenticationPrincipal UserAccount userAccount,
                                                    @RequestParam(defaultValue = "1") String pageString,
                                                    @RequestParam(defaultValue = "") String titleTestingModel,
                                                    @RequestParam(defaultValue = "") String titleScoringModel,
                                                    @RequestParam(defaultValue = "NaN") String sf,
                                                    @RequestParam(defaultValue = "NaN") String action){
        int page;
        if (pageString.equals("NaN")){
            page = 1;
        } else {
            page = Integer.parseInt(pageString);
        }
        ModelAndView modelAndView = new ModelAndView();
        if(!sf.equals("NaN")){
            modelAndView.setViewName("redirect:/testing-model/scoring-model-test-list" + "?titleTestingModel=" + titleTestingModel
            + "&titleScoringModel=" + titleScoringModel);
            return modelAndView;
        }
        List<TestScoringModel> testScoringModelList;
        if(!titleTestingModel.equals("") && !titleScoringModel.equals("")){
            testScoringModelList = testScoringModelService.getTestingModelWithFilterTitlesScoringAndTestingModel(userAccount,
                    page, titleTestingModel, titleScoringModel);
        } else if (!titleTestingModel.equals("")){
            testScoringModelList = testScoringModelService.getTestingModelsWithFilterTitleTestingModel(userAccount,
                    page, titleTestingModel);
        } else if (!titleScoringModel.equals("")){
            testScoringModelList = testScoringModelService.getTestingModelsWithFilterTitleScoringModel(userAccount,
                    page, titleScoringModel);
        } else {
            testScoringModelList = testScoringModelService.getAllTestingModels(userAccount, page);
        }
        Long testingModelsCount = testScoringModelService.getCountTestingModelsWithFilter(userAccount, titleTestingModel,
                titleScoringModel);
        int pagesCount = (int) ((testingModelsCount + 9) / 10);
        modelAndView.setViewName("/testingScoringModel/testingScoringModelList");
        modelAndView.addObject("page", page);
        modelAndView.addObject("testScoringModelList", testScoringModelList);
        modelAndView.addObject("testingModelsCount", testingModelsCount);
        modelAndView.addObject("pagesCount", pagesCount);
        if(action.equals("delete")){
            modelAndView.addObject("message", "testingScoringModelsListPage.messageSuccessDeleted");
        }
        return modelAndView;
    }

    @PreAuthorize("hasAuthority(T(com.korotkov.main.enums.UserRoleEnum).LEVEL_FIVE)")
    @RequestMapping(value = "/testing-model/test-scoring-model/{testModelId}/export-to-excel", method = RequestMethod.GET)
    public ModelAndView testScoringModelExportToExcel(@AuthenticationPrincipal UserAccount userAccount,
                                                      @PathVariable(value = "testModelId") Long testModelId){
        TestScoringModel testScoringModel = testScoringModelService.getByUserAndTestId(userAccount,
                testModelId);
        TestScoringModelResult testScoringModelResultTotal = testScoringModelResultService
                .getTestScoringModelResultTotal(userAccount, testModelId);
        List<TestScoringModelResult> listTestResultsRowsWithoutTotal =
                testScoringModelResultService.getListTestResultsWithoutTotal(userAccount, testModelId);
        if (testScoringModel != null && testScoringModelResultTotal != null &&
        !listTestResultsRowsWithoutTotal.isEmpty()){
            return new ModelAndView("excelExportTestOfModelData", "modelObject",
                    new TestOfModelExportFile(testScoringModel.getTitle(), testScoringModel.getCreatedAt(),
                            testScoringModel.getScoringModel().getTitle(), testScoringModel.getGiniIndex(),
                            testScoringModelResultTotal, listTestResultsRowsWithoutTotal));
        } else {
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("redirect:/testing-model/test-scoring-model/" + testModelId + "?action=not-export");
            return modelAndView;
        }

    }

    @PreAuthorize("hasAnyAuthority(T(com.korotkov.main.enums.UserRoleEnum).LEVEL_THREE," +
            "T(com.korotkov.main.enums.UserRoleEnum).LEVEL_FOUR," +
            "T(com.korotkov.main.enums.UserRoleEnum).LEVEL_FIVE)")
    @RequestMapping(value = "/testing-model/test-scoring-model/{testModelId}", method = RequestMethod.GET)
    public ModelAndView testScoringModelPageView(@AuthenticationPrincipal UserAccount userAccount,
                                                 @PathVariable(value = "testModelId") Long testModelId,
                                                 @RequestParam(defaultValue = "NaN") String action){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/testingScoringModel/testScoringModelView");
        List<TestScoringModelResult> listTestResultsRowsWithoutTotal =
                testScoringModelResultService.getListTestResultsWithoutTotal(userAccount, testModelId);
        listTestResultsRowsWithoutTotal.sort(Comparator.comparing(TestScoringModelResult::getOrderNumberRow));
        modelAndView.addObject("listTestResultRows", listTestResultsRowsWithoutTotal);
        modelAndView.addObject("totalTestResult", testScoringModelResultService
                .getTestScoringModelResultTotal(userAccount, testModelId));
        modelAndView.addObject("testScoringModel", testScoringModelService.getByUserAndTestId(userAccount,
                testModelId));
        if(action.equals("edit")){
            modelAndView.addObject("message", "testScoringModelPage.messageSuccessChanged");
        } else if (action.equals("created-new")){
            modelAndView.addObject("message", "testScoringModelPage.messageSuccessCreatedNewTest");
        } else if (action.equals("not-export")){
            modelAndView.addObject("message", "testScoringModelPage.messageNotExportToExcel");
        }
        return modelAndView;
    }

    @PreAuthorize("hasAnyAuthority(T(com.korotkov.main.enums.UserRoleEnum).LEVEL_THREE," +
            "T(com.korotkov.main.enums.UserRoleEnum).LEVEL_FOUR," +
            "T(com.korotkov.main.enums.UserRoleEnum).LEVEL_FIVE)")
    @RequestMapping(value = "/testing-model/test-scoring-model/{testModelId}/edit", method = RequestMethod.GET)
    public ModelAndView testScoringModelPageEdit(@AuthenticationPrincipal UserAccount userAccount,
                                                 @PathVariable(value = "testModelId") Long testModelId){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/testingScoringModel/testScoringModelEdit");
        modelAndView.addObject("testScoringModel", testScoringModelService.getByUserAndTestId(userAccount,
                testModelId));
        return modelAndView;
    }

    @PreAuthorize("hasAnyAuthority(T(com.korotkov.main.enums.UserRoleEnum).LEVEL_THREE," +
            "T(com.korotkov.main.enums.UserRoleEnum).LEVEL_FOUR," +
            "T(com.korotkov.main.enums.UserRoleEnum).LEVEL_FIVE)")
    @RequestMapping(value = "/testing-model/test-scoring-model/{testModelId}/edit", method = RequestMethod.POST)
    public ModelAndView testScoringModelPageEditPost(@AuthenticationPrincipal UserAccount userAccount,
                                                     @PathVariable(value = "testModelId") Long testModelId,
                                                     @ModelAttribute("testScoringModel") TestScoringModel testScoringModel){
        ModelAndView modelAndView = new ModelAndView();
        if(testScoringModel.getTitle().isEmpty() || testScoringModel.getTitle().isBlank()){
            modelAndView.setViewName("/testingScoringModel/testScoringModelEdit");
            modelAndView.addObject("titleError", "modelCreationSettingsPage.errorEmptyField");
            return modelAndView;
        }
        testScoringModelService.updateTitleAndDescriptionForTestScoringModel(userAccount, testModelId, testScoringModel);
        modelAndView.setViewName("redirect:/testing-model/test-scoring-model/" + testModelId + "?action=edit");
        return modelAndView;
    }

    @PreAuthorize("hasAnyAuthority(T(com.korotkov.main.enums.UserRoleEnum).LEVEL_THREE," +
            "T(com.korotkov.main.enums.UserRoleEnum).LEVEL_FOUR," +
            "T(com.korotkov.main.enums.UserRoleEnum).LEVEL_FIVE)")
    @RequestMapping(value = "/testing-model/test-scoring-model/{testModelId}/delete", method = RequestMethod.GET)
    public ModelAndView testScoringModelPageDelete(@AuthenticationPrincipal UserAccount userAccount,
                                                   @PathVariable(value = "testModelId") Long testModelId){
        testScoringModelService.deleteTestScoringModel(userAccount, testModelId);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/testing-model/scoring-model-test-list?action=delete");
        return modelAndView;
    }

}
