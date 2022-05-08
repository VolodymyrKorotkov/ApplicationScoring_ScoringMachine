package com.korotkov.main.controller;

import com.korotkov.main.enums.ScoringParameterType;
import com.korotkov.main.model.ScoringModel;
import com.korotkov.main.model.ScoringModelParameter;
import com.korotkov.main.model.ScoringSettingsModel;
import com.korotkov.main.model.UserAccount;
import com.korotkov.main.service.email.EmailService;
import com.korotkov.main.service.file.ExcelReader;
import com.korotkov.main.service.scoringModel.additionalService.additionalEntityForPortal.EntityForExportFile.ScoringModelExportFile;
import com.korotkov.main.service.scoringModel.additionalService.additionalEntityForPortal.ScoringParameterPortalCommon;
import com.korotkov.main.service.scoringModel.additionalService.additionalEntityForPortal.TestPortal;
import com.korotkov.main.service.scoringModel.scoringModelParameterService.ScoringModelParameterService;
import com.korotkov.main.service.scoringModel.scoringModelService.ScoringModelService;
import com.korotkov.main.service.scoringModel.scoringSettings.ScoringSettingsModelService;
import com.korotkov.main.service.scoringModel.scoringCore.ScoringCalculationService;
import com.korotkov.main.service.scoringModel.testScoringModel.TestScoringModelService;
import com.korotkov.main.service.userAccount.UserAccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.*;


@Controller
public class CreatingNewModelController {
    private static final Logger logger = LoggerFactory.getLogger(CreatingNewModelController.class);

    UserAccountService userAccountService;
    ScoringCalculationService scoringCalculationService;
    ScoringSettingsModelService scoringSettingsModelService;
    ScoringModelService scoringModelService;
    ScoringModelParameterService scoringModelParameterService;
    TestScoringModelService testScoringModelService;

    @Autowired
    public void setUserAccountService(UserAccountService userAccountService){
        this.userAccountService = userAccountService;
    }


    @Autowired
    public void setScoringCalculationService(ScoringCalculationService scoringCalculationService){
        this.scoringCalculationService = scoringCalculationService;
    }

    @Autowired
    public void setScoringSettingsModelService(ScoringSettingsModelService scoringSettingsModelService){
        this.scoringSettingsModelService = scoringSettingsModelService;
    }

    @Autowired
    public void setScoringModelService(ScoringModelService scoringModelService){
        this.scoringModelService = scoringModelService;
    }

    @Autowired
    public void setScoringModelParameterService(ScoringModelParameterService scoringModelParameterService){
        this.scoringModelParameterService = scoringModelParameterService;
    }

    @Autowired
    public void setTestScoringModelService(TestScoringModelService testScoringModelService){
        this.testScoringModelService = testScoringModelService;
    }

    @PreAuthorize("hasAnyAuthority(T(com.korotkov.main.enums.UserRoleEnum).LEVEL_TWO," +
            "T(com.korotkov.main.enums.UserRoleEnum).LEVEL_THREE," +
            "T(com.korotkov.main.enums.UserRoleEnum).LEVEL_FOUR," +
            "T(com.korotkov.main.enums.UserRoleEnum).LEVEL_FIVE)")
    @RequestMapping(value = "/creating-model/creating-new-model", method = RequestMethod.GET)
    public ModelAndView creatingNewModelPage(@AuthenticationPrincipal UserAccount userAccount,
                                             @RequestParam(defaultValue = "NaN") String action){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/modelCreation/createNewModel");
        modelAndView.addObject("countCurrentSavedModels",userAccountService.getCurrentCountSavedModels(userAccount));
        modelAndView.addObject("countMaxPossibleSavedModels",userAccountService.getCountMaxPossibleSavedModel(userAccount));
        modelAndView.addObject("canBuildModel","true");
        if (action.equals("fileIsEmpty")) {
            modelAndView.addObject("fileError","modelCreationPage.fileErrorFileIsEmpty");
        } else if (action.equals("fileNotExcel")) {
            modelAndView.addObject("fileError", "modelCreationPage.fileErrorFileIsNotNeedFormat");
        } else if (action.equals("fileException")) {
            modelAndView.addObject("fileError", "modelCreationPage.fileErrorProcessFile");
        }
        return modelAndView;
    }

    @PreAuthorize("hasAnyAuthority(T(com.korotkov.main.enums.UserRoleEnum).LEVEL_TWO," +
            "T(com.korotkov.main.enums.UserRoleEnum).LEVEL_THREE," +
            "T(com.korotkov.main.enums.UserRoleEnum).LEVEL_FOUR," +
            "T(com.korotkov.main.enums.UserRoleEnum).LEVEL_FIVE)")
    @RequestMapping(value = "/creating-model/started-creating-new-model", method = RequestMethod.GET)
    public ModelAndView newScoringModelWasStartedPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/modelCreation/startedBuildingScoringModel");
        return modelAndView;
    }

    @PreAuthorize("hasAnyAuthority(T(com.korotkov.main.enums.UserRoleEnum).LEVEL_TWO," +
            "T(com.korotkov.main.enums.UserRoleEnum).LEVEL_THREE," +
            "T(com.korotkov.main.enums.UserRoleEnum).LEVEL_FOUR," +
            "T(com.korotkov.main.enums.UserRoleEnum).LEVEL_FIVE)")
    @RequestMapping(value = "/upload-new-file-for-creating-model", method = RequestMethod.POST)
    public ModelAndView handleFileUploadForCreatingNewModel(@RequestParam("file") MultipartFile file,
                                                            @AuthenticationPrincipal UserAccount userAccount){
        ModelAndView modelAndView = new ModelAndView();
        if(file.isEmpty()){
            modelAndView.setViewName("redirect:/creating-model/creating-new-model?action=fileIsEmpty");
            return modelAndView;
        }
        if(!Objects.requireNonNull(file.getOriginalFilename()).substring(file.getOriginalFilename().lastIndexOf(".") + 1).toLowerCase().equals("xls") &&
        !file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1).toLowerCase().equals("xlsx")){
            modelAndView.setViewName("redirect:/creating-model/creating-new-model?action=fileNotExcel");
            return modelAndView;
        }
        try {
            scoringCalculationService.createNewScoringModel(ExcelReader.readFileExcel(file),userAccount);
        } catch (IOException e) {
            logger.error("Error excelFileReader, scoring, client id: " + userAccount.getId() + " - " + e.getMessage());
            modelAndView.setViewName("redirect:/creating-model/creating-new-model?action=fileException");
            return modelAndView;
        }
        modelAndView.setViewName("redirect:/creating-model/started-creating-new-model");
        return modelAndView;
    }

    @PreAuthorize("hasAnyAuthority(T(com.korotkov.main.enums.UserRoleEnum).LEVEL_TWO," +
            "T(com.korotkov.main.enums.UserRoleEnum).LEVEL_THREE," +
            "T(com.korotkov.main.enums.UserRoleEnum).LEVEL_FOUR," +
            "T(com.korotkov.main.enums.UserRoleEnum).LEVEL_FIVE)")
    @RequestMapping(value = "/creating-model/scoring-model-settings/view", method = RequestMethod.GET)
    public ModelAndView scoringModelSettingsViewPage(@AuthenticationPrincipal UserAccount userAccount,
                                                     @RequestParam(defaultValue = "NaN") String message){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/modelCreation/scoringModelSettingsView");
        modelAndView.addObject("scoringSettingsModel", scoringSettingsModelService.findByUser(userAccount));
        if(message.equals("defaultSettings")){
            modelAndView.addObject("message","modelCreationSettingsPage.messageSuccessRestoredDefaultSettings");
        } else if (message.equals("changedData")){
            modelAndView.addObject("message","modelCreationSettingsPage.messageSuccessChangeData");
        }
        return modelAndView;
    }

    @PreAuthorize("hasAuthority(T(com.korotkov.main.enums.UserRoleEnum).LEVEL_FIVE)")
    @RequestMapping(value = "/creating-model/scoring-model-advanced-settings/view", method = RequestMethod.GET)
    public ModelAndView scoringModelAdvancedSettingsViewPage(@AuthenticationPrincipal UserAccount userAccount,
                                                             @RequestParam(defaultValue = "NaN") String message){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/modelCreation/scoringModelAdvancedSettingsView");
        modelAndView.addObject("scoringSettingsModel", scoringSettingsModelService.findByUser(userAccount));
        if(message.equals("defaultSettings")){
            modelAndView.addObject("message","modelCreationSettingsPage.messageSuccessRestoredDefaultSettings");
        } else if(message.equals("changedData")){
            modelAndView.addObject("message","modelCreationSettingsPage.messageSuccessChangeData");
        }
        return modelAndView;
    }

    @PreAuthorize("hasAnyAuthority(T(com.korotkov.main.enums.UserRoleEnum).LEVEL_TWO," +
            "T(com.korotkov.main.enums.UserRoleEnum).LEVEL_THREE," +
            "T(com.korotkov.main.enums.UserRoleEnum).LEVEL_FOUR," +
            "T(com.korotkov.main.enums.UserRoleEnum).LEVEL_FIVE)")
    @RequestMapping(value = "/creating-model/scoring-model-settings/edit", method = RequestMethod.GET)
    public ModelAndView scoringModelSettingsEditPage(@AuthenticationPrincipal UserAccount userAccount){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/modelCreation/scoringModelSettingsEdit");
        modelAndView.addObject("scoringSettingsModel", scoringSettingsModelService.findByUser(userAccount));
        return modelAndView;
    }

    @PreAuthorize("hasAuthority(T(com.korotkov.main.enums.UserRoleEnum).LEVEL_FIVE)")
    @RequestMapping(value = "/creating-model/scoring-model-advanced-settings/edit", method = RequestMethod.GET)
    public ModelAndView scoringModelAdvancedSettingsEditPage(@AuthenticationPrincipal UserAccount userAccount){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/modelCreation/scoringModelAdvancedSettingsEdit");
        modelAndView.addObject("scoringSettingsModel", scoringSettingsModelService.findByUser(userAccount));
        return modelAndView;
    }

    @PreAuthorize("hasAnyAuthority(T(com.korotkov.main.enums.UserRoleEnum).LEVEL_TWO," +
            "T(com.korotkov.main.enums.UserRoleEnum).LEVEL_THREE," +
            "T(com.korotkov.main.enums.UserRoleEnum).LEVEL_FOUR," +
            "T(com.korotkov.main.enums.UserRoleEnum).LEVEL_FIVE)")
    @RequestMapping(value = "/creating-model/scoring-model-settings/edit", method = RequestMethod.POST)
    public ModelAndView scoringModelSettingsEditPost(@AuthenticationPrincipal UserAccount userAccount,
                                                     @ModelAttribute("scoringSettingsModel")
                                                     ScoringSettingsModel scoringSettingsModel){
        ModelAndView modelAndView = new ModelAndView();
        if(scoringSettingsModel.getBadResult().isEmpty() || scoringSettingsModel.getBadResult().isBlank()){
            modelAndView.setViewName("/modelCreation/scoringModelSettingsEdit");
            modelAndView.addObject("badResultError","modelCreationSettingsPage.errorEmptyField");
            return modelAndView;
        }
        if (scoringSettingsModel.getGoodResult().isEmpty() || scoringSettingsModel.getGoodResult().isBlank()){
            modelAndView.setViewName("/modelCreation/scoringModelSettingsEdit");
            modelAndView.addObject("goodResultError","modelCreationSettingsPage.errorEmptyField");
            return modelAndView;
        }
        if (scoringSettingsModel.getModelQualityLevel().isEmpty() || scoringSettingsModel.getModelQualityLevel().isBlank()){
            modelAndView.setViewName("/modelCreation/scoringModelSettingsEdit");
            modelAndView.addObject("modelQualityLevelError","modelCreationSettingsPage.errorEmptyField");
            return modelAndView;
        }
        if(!scoringSettingsModelService.updateScoringSettings(userAccount, scoringSettingsModel)){
            modelAndView.setViewName("/modelCreation/scoringModelSettingsEdit");
            modelAndView.addObject("modelQualityLevelError","modelCreationSettingsPage.errorIncorrectValue");
            return modelAndView;
        }
        modelAndView.setViewName("redirect:/creating-model/scoring-model-settings/view?message=changedData");
        return modelAndView;
    }

    @PreAuthorize("hasAuthority(T(com.korotkov.main.enums.UserRoleEnum).LEVEL_FIVE)")
    @RequestMapping(value = "/creating-model/scoring-model-advanced-settings/edit", method = RequestMethod.POST)
    public ModelAndView scoringModelAdvancedSettingsEditPost(@AuthenticationPrincipal UserAccount userAccount,
                                                             @ModelAttribute("scoringSettingsModel")
                                                             ScoringSettingsModel scoringSettingsModel){
        ModelAndView modelAndView = new ModelAndView();
        if(scoringSettingsModel.getBadResult().isBlank() || scoringSettingsModel.getBadResult().isEmpty()){
            modelAndView.setViewName("/modelCreation/scoringModelAdvancedSettingsEdit");
            modelAndView.addObject("badResultError","modelCreationSettingsPage.errorEmptyField");
            return modelAndView;
        }
        if(scoringSettingsModel.getGoodResult().isEmpty() || scoringSettingsModel.getGoodResult().isBlank()){
            modelAndView.setViewName("/modelCreation/scoringModelAdvancedSettingsEdit");
            modelAndView.addObject("goodResultError","modelCreationSettingsPage.errorEmptyField");
            return modelAndView;
        }
        if(scoringSettingsModel.getFactor() == null){
            modelAndView.setViewName("/modelCreation/scoringModelAdvancedSettingsEdit");
            modelAndView.addObject("factorError","modelCreationSettingsPage.errorEmptyField");
            return modelAndView;
        }
        if(scoringSettingsModel.getFactor() < 1){
            modelAndView.setViewName("/modelCreation/scoringModelAdvancedSettingsEdit");
            modelAndView.addObject("factorError", "modelCreationAdvancedSettingsPage.errorValueCanNotBeLess1");
            return modelAndView;
        }
        if(scoringSettingsModel.getOffset() == null){
            modelAndView.setViewName("/modelCreation/scoringModelAdvancedSettingsEdit");
            modelAndView.addObject("offsetError","modelCreationSettingsPage.errorEmptyField");
            return modelAndView;
        }
        if(scoringSettingsModel.getOffset() < 0){
            modelAndView.setViewName("/modelCreation/scoringModelAdvancedSettingsEdit");
            modelAndView.addObject("offsetError","modelCreationAdvancedSettingsPage.errorValueCanNotBeLess0");
            return modelAndView;
        }
        if(scoringSettingsModel.getMinimumNeededIVForParameterOne() == null){
            modelAndView.setViewName("/modelCreation/scoringModelAdvancedSettingsEdit");
            modelAndView.addObject("minIvOneValueError","modelCreationSettingsPage.errorEmptyField");
            return modelAndView;
        }
        if(scoringSettingsModel.getMinimumNeededIVForParameterOne() < 0){
            modelAndView.setViewName("/modelCreation/scoringModelAdvancedSettingsEdit");
            modelAndView.addObject("minIvOneValueError","modelCreationAdvancedSettingsPage.errorValueCanNotBeLess0");
            return modelAndView;
        }
        if(scoringSettingsModel.getMinimumNeededAverageIVForKeyOfParameterOne() == null){
            modelAndView.setViewName("/modelCreation/scoringModelAdvancedSettingsEdit");
            modelAndView.addObject("minIvAverageOneValueError","modelCreationSettingsPage.errorEmptyField");
            return modelAndView;
        }
        if(scoringSettingsModel.getMinimumNeededAverageIVForKeyOfParameterOne() < 0){
            modelAndView.setViewName("/modelCreation/scoringModelAdvancedSettingsEdit");
            modelAndView.addObject("minIvAverageOneValueError","modelCreationAdvancedSettingsPage.errorValueCanNotBeLess0");
            return modelAndView;
        }
        if(scoringSettingsModel.getMinimumNeededIVForParameterTwo() == null){
            modelAndView.setViewName("/modelCreation/scoringModelAdvancedSettingsEdit");
            modelAndView.addObject("minIvTwoValueError","modelCreationSettingsPage.errorEmptyField");
            return modelAndView;
        }
        if(scoringSettingsModel.getMinimumNeededIVForParameterTwo() < 0){
            modelAndView.setViewName("/modelCreation/scoringModelAdvancedSettingsEdit");
            modelAndView.addObject("minIvTwoValueError","modelCreationAdvancedSettingsPage.errorValueCanNotBeLess0");
            return modelAndView;
        }
        if(scoringSettingsModel.getMinimumNeededAverageIVForKeyOfParameterTwo() == null){
            modelAndView.setViewName("/modelCreation/scoringModelAdvancedSettingsEdit");
            modelAndView.addObject("minIvAverageTwoValueError","modelCreationSettingsPage.errorEmptyField");
            return modelAndView;
        }
        if(scoringSettingsModel.getMinimumNeededAverageIVForKeyOfParameterTwo() < 0){
            modelAndView.setViewName("/modelCreation/scoringModelAdvancedSettingsEdit");
            modelAndView.addObject("minIvAverageTwoValueError","modelCreationAdvancedSettingsPage.errorValueCanNotBeLess0");
            return modelAndView;
        }
        if(scoringSettingsModel.getMaxRowsForInfluenceParameterTwo() == null){
            modelAndView.setViewName("/modelCreation/scoringModelAdvancedSettingsEdit");
            modelAndView.addObject("maxRowsTwoError","modelCreationSettingsPage.errorEmptyField");
            return modelAndView;
        }
        if(scoringSettingsModel.getMaxRowsForInfluenceParameterTwo() < 10){
            modelAndView.setViewName("/modelCreation/scoringModelAdvancedSettingsEdit");
            modelAndView.addObject("maxRowsTwoError","modelCreationAdvancedSettingsPage.errorValueCanNotBeLess10");
            return modelAndView;
        }
        scoringSettingsModelService.updateScoringAdvanceSettings(userAccount, scoringSettingsModel);
        modelAndView.setViewName("redirect:/creating-model/scoring-model-advanced-settings/view?message=changedData");
        return modelAndView;
    }

    @PreAuthorize("hasAnyAuthority(T(com.korotkov.main.enums.UserRoleEnum).LEVEL_TWO," +
            "T(com.korotkov.main.enums.UserRoleEnum).LEVEL_THREE," +
            "T(com.korotkov.main.enums.UserRoleEnum).LEVEL_FOUR," +
            "T(com.korotkov.main.enums.UserRoleEnum).LEVEL_FIVE)")
    @RequestMapping(value = "/creating-model/scoring-model-settings/restore-default-settings", method = RequestMethod.GET)
    public ModelAndView scoringModelSettingsRestoreDefaultSettings(@AuthenticationPrincipal UserAccount userAccount){
        ModelAndView modelAndView = new ModelAndView();
        scoringSettingsModelService.restoreDefaultScoringSettings(userAccount);
        modelAndView.setViewName("redirect:/creating-model/scoring-model-settings/view?message=defaultSettings");
        return modelAndView;
    }

    @PreAuthorize("hasAuthority(T(com.korotkov.main.enums.UserRoleEnum).LEVEL_FIVE)")
    @RequestMapping(value = "/creating-model/scoring-model-advanced-settings/restore-default-settings", method = RequestMethod.GET)
    public ModelAndView scoringModelAdvancedSettingsRestoreDefaultSettings(@AuthenticationPrincipal UserAccount userAccount){
        ModelAndView modelAndView = new ModelAndView();
        scoringSettingsModelService.restoreDefaultScoringSettings(userAccount);
        modelAndView.setViewName("redirect:/creating-model/scoring-model-advanced-settings/view?message=defaultSettings");
        return modelAndView;
    }

    @PreAuthorize("hasAnyAuthority(T(com.korotkov.main.enums.UserRoleEnum).LEVEL_TWO," +
            "T(com.korotkov.main.enums.UserRoleEnum).LEVEL_THREE," +
            "T(com.korotkov.main.enums.UserRoleEnum).LEVEL_FOUR," +
            "T(com.korotkov.main.enums.UserRoleEnum).LEVEL_FIVE)")
    @RequestMapping(value = "/creating-model/scoring-model-list", method = RequestMethod.GET)
    public ModelAndView scoringModelListView(@AuthenticationPrincipal UserAccount userAccount,
                                             @RequestParam(defaultValue = "1") String pageString,
                                             @RequestParam(defaultValue = "") String title,
                                             @RequestParam(defaultValue = "NaN") String status,
                                             @RequestParam(defaultValue = "NaN") String sf,
                                             @RequestParam(defaultValue = "NaN") String action){
        int page;
        if(pageString.equals("NaN")){
            page = 1;
        } else {
            page = Integer.parseInt(pageString);
        }
        ModelAndView modelAndView = new ModelAndView();
        if(!sf.equals("NaN")){
            modelAndView.setViewName("redirect:/creating-model/scoring-model-list?" + "title=" + title + "&status=" + status);
            return modelAndView;
        }
        List<ScoringModel> scoringModelList;
        if(!title.equals("") && !status.equals("NaN")){
            scoringModelList = scoringModelService.getModelsWithFilter(userAccount,page,title,status);
        } else if (!title.equals("")){
            scoringModelList = scoringModelService.getModelsWithFilter(userAccount,page,title);
        } else if (!status.equals("NaN")){
            scoringModelList = scoringModelService.getModelsWithFilter(userAccount, status, page);
        } else {
            scoringModelList = scoringModelService.getAllModels(userAccount, page);
        }
        Long scoringModelsCount = scoringModelService.getCountModelsWithFilter(userAccount, title, status);
        int pagesCount = (int) ((scoringModelsCount + 9) / 10);
        modelAndView.setViewName("/modelCreation/scoringModelLists");
        modelAndView.addObject("page", page);
        modelAndView.addObject("scoringModelList", scoringModelList);
        modelAndView.addObject("scoringModelsCount", scoringModelsCount);
        modelAndView.addObject("pagesCount", pagesCount);
        if(action.equals("delete")){
            modelAndView.addObject("message", "scoringModelPage.messageSuccessDeleted");
        }
        return modelAndView;
    }

    @PreAuthorize("hasAuthority(T(com.korotkov.main.enums.UserRoleEnum).LEVEL_FIVE)")
    @RequestMapping(value = "/creating-model/scoring-model/{modelId}/export-to-excel")
    public ModelAndView scoringModelExportToExcel(@AuthenticationPrincipal UserAccount userAccount,
                                                  @PathVariable(value = "modelId") Long modelId){
        ScoringModel scoringModel = scoringModelService.getScoringModelByIdAndUser(userAccount, modelId);
        List<ScoringModelParameter> allParametersInfluenceOneTotal =
                scoringModelService.getScoringModelAllParametersTotalList(userAccount, modelId,
                        String.valueOf(ScoringParameterType.ONE_PARAMETER));
        if(!allParametersInfluenceOneTotal.isEmpty()){
            return new ModelAndView("excelExportScoringModelData","modelObject",
                    new ScoringModelExportFile(scoringModel.getCreatedAt(), scoringModel.getTitle(),
                            allParametersInfluenceOneTotal,
                            scoringModelService.getScoringModelRecommendedParametersList(userAccount, modelId,
                                    String.valueOf(ScoringParameterType.ONE_PARAMETER)),
                            scoringModelService.getScoringModelRecommendedParametersList(userAccount, modelId,
                                    String.valueOf(ScoringParameterType.TWO_PARAMETERS))));
        } else {
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("redirect:/creating-model/scoring-model/" + modelId + "?action=not-export");
            return modelAndView;
        }
    }

    @PreAuthorize("hasAnyAuthority(T(com.korotkov.main.enums.UserRoleEnum).LEVEL_TWO," +
            "T(com.korotkov.main.enums.UserRoleEnum).LEVEL_THREE," +
            "T(com.korotkov.main.enums.UserRoleEnum).LEVEL_FOUR," +
            "T(com.korotkov.main.enums.UserRoleEnum).LEVEL_FIVE)")
    @RequestMapping(value = "/creating-model/scoring-model/{modelId}", method = RequestMethod.GET)
    public ModelAndView scoringModelPageView(@AuthenticationPrincipal UserAccount userAccount,
                                             @PathVariable(value = "modelId") Long modelId,
                                             @RequestParam(defaultValue = "NaN") String action){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/modelCreation/scoringModelView");
        List<ScoringParameterPortalCommon> scoringModelRecommendedParametersListOne =
                scoringModelService.getScoringModelRecommendedParametersList(userAccount, modelId,
                        String.valueOf(ScoringParameterType.ONE_PARAMETER));
        scoringModelRecommendedParametersListOne.sort(Comparator.comparing(ScoringParameterPortalCommon::getIvTotal).reversed());
        List<ScoringParameterPortalCommon> scoringModelRecommendedParametersListTwo =
                scoringModelService.getScoringModelRecommendedParametersList(userAccount, modelId,
                        String.valueOf(ScoringParameterType.TWO_PARAMETERS));
        scoringModelRecommendedParametersListTwo.sort(Comparator.comparing(ScoringParameterPortalCommon::getIvTotal).reversed());
        List<ScoringModelParameter> allParametersInfluenceOneTotal =
                scoringModelService.getScoringModelAllParametersTotalList(userAccount, modelId,
                        String.valueOf(ScoringParameterType.ONE_PARAMETER));
        allParametersInfluenceOneTotal.sort(Comparator.comparing(ScoringModelParameter::getIv).reversed());

        modelAndView.addObject("scoringModel",
                scoringModelService.getScoringModelByIdAndUser(userAccount, modelId));
        modelAndView.addObject("scoringModelRecommendedParametersListOne",
                scoringModelRecommendedParametersListOne);
        modelAndView.addObject("scoringModelRecommendedParametersListTwo",
                scoringModelRecommendedParametersListTwo);
        modelAndView.addObject("allParametersInfluenceOne",
                allParametersInfluenceOneTotal);
        modelAndView.addObject("scoringModelRecommendedParametersListOneSize",
                scoringModelRecommendedParametersListOne.size());
        modelAndView.addObject("scoringModelRecommendedParametersListTwoSize",
                scoringModelRecommendedParametersListTwo.size());
        modelAndView.addObject("allParametersInfluenceOneSize", allParametersInfluenceOneTotal.size());

        List<TestPortal> testPortalList = testScoringModelService.getAllModelsForScoringModelPortal(userAccount, modelId);
        if (!testPortalList.isEmpty()){
            testPortalList.sort(Comparator.comparing(TestPortal::getCreatedAt));
            modelAndView.addObject("testsForModel", testPortalList);
        }

        if(action.equals("edit")){
            modelAndView.addObject("message", "scoringModelPage.messageSuccessChangeCommonData");
        } else if(action.equals("activate")){
            modelAndView.addObject("message", "scoringModelPage.messageSuccessActivated");
        } else if(action.equals("deactivate")){
            modelAndView.addObject("message", "scoringModelPage.messageSuccessDeactivated");
        } else if(action.equals("change-attribute-score")){
            modelAndView.addObject("message","scoringModelPage.messageSuccessChangedAttributeValueScore");
        } else if(action.equals("created-new")){
            modelAndView.addObject("message", "scoringModelPage.messageSuccessCreatedNewScoringModel");
        } else if(action.equals("not-export")){
            modelAndView.addObject("message", "scoringModelPage.messageNotExportToExcel");
        }
        return modelAndView;
    }

    @PreAuthorize("hasAnyAuthority(T(com.korotkov.main.enums.UserRoleEnum).LEVEL_TWO," +
            "T(com.korotkov.main.enums.UserRoleEnum).LEVEL_THREE," +
            "T(com.korotkov.main.enums.UserRoleEnum).LEVEL_FOUR," +
            "T(com.korotkov.main.enums.UserRoleEnum).LEVEL_FIVE)")
    @RequestMapping(value = "/creating-model/scoring-model/{modelId}/edit", method = RequestMethod.GET)
    public ModelAndView scoringModelPageEdit(@AuthenticationPrincipal UserAccount userAccount,
                                             @PathVariable(value = "modelId") Long modelId){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/modelCreation/scoringModelEdit");
        modelAndView.addObject("scoringModel",
                scoringModelService.getScoringModelByIdAndUser(userAccount, modelId));
        return modelAndView;
    }

    @PreAuthorize("hasAnyAuthority(T(com.korotkov.main.enums.UserRoleEnum).LEVEL_TWO," +
            "T(com.korotkov.main.enums.UserRoleEnum).LEVEL_THREE," +
            "T(com.korotkov.main.enums.UserRoleEnum).LEVEL_FOUR," +
            "T(com.korotkov.main.enums.UserRoleEnum).LEVEL_FIVE)")
    @RequestMapping(value = "/creating-model/scoring-model/{modelId}/edit", method = RequestMethod.POST)
    public ModelAndView scoringModelPageEditPost(@AuthenticationPrincipal UserAccount userAccount,
                                                 @PathVariable(value = "modelId") Long modelId,
                                                 @ModelAttribute("scoringModel") ScoringModel scoringModel){
        ModelAndView modelAndView = new ModelAndView();
        if(scoringModel.getTitle().isEmpty() || scoringModel.getTitle().isBlank()){
            modelAndView.setViewName("/modelCreation/scoringModelEdit");
            modelAndView.addObject("titleError", "modelCreationSettingsPage.errorEmptyField");
            return modelAndView;
        }
        scoringModelService.updateTitleAndDescriptionInScoringModel(userAccount, modelId, scoringModel);
        modelAndView.setViewName("redirect:/creating-model/scoring-model/" + modelId + "?action=edit");
        return modelAndView;
    }

    @PreAuthorize("hasAnyAuthority(T(com.korotkov.main.enums.UserRoleEnum).LEVEL_TWO," +
            "T(com.korotkov.main.enums.UserRoleEnum).LEVEL_THREE," +
            "T(com.korotkov.main.enums.UserRoleEnum).LEVEL_FOUR," +
            "T(com.korotkov.main.enums.UserRoleEnum).LEVEL_FIVE)")
    @RequestMapping(value = "/creating-model/scoring-model/{modelId}/delete", method = RequestMethod.GET)
    public ModelAndView scoringModelPageDelete(@AuthenticationPrincipal UserAccount userAccount,
                                               @PathVariable(value = "modelId") Long modelId){
        scoringModelService.deleteScoringModel(userAccount, modelId);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/creating-model/scoring-model-list?action=delete");
        return modelAndView;
    }

    @PreAuthorize("hasAnyAuthority(T(com.korotkov.main.enums.UserRoleEnum).LEVEL_THREE," +
            "T(com.korotkov.main.enums.UserRoleEnum).LEVEL_FOUR," +
            "T(com.korotkov.main.enums.UserRoleEnum).LEVEL_FIVE)")
    @RequestMapping(value = "/creating-model/scoring-model/{modelId}/activate", method = RequestMethod.GET)
    public ModelAndView scoringModelPageActivate(@AuthenticationPrincipal UserAccount userAccount,
                                                 @PathVariable(value = "modelId") Long modelId){
        scoringModelService.activateScoringModel(userAccount, modelId);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/creating-model/scoring-model/" + modelId + "?action=activate");
        return modelAndView;
    }

    @PreAuthorize("hasAnyAuthority(T(com.korotkov.main.enums.UserRoleEnum).LEVEL_THREE," +
            "T(com.korotkov.main.enums.UserRoleEnum).LEVEL_FOUR," +
            "T(com.korotkov.main.enums.UserRoleEnum).LEVEL_FIVE)")
    @RequestMapping(value = "/creating-model/scoring-model/{modelId}/activate-and-make-test", method = RequestMethod.GET)
    public ModelAndView scoringModelActivateAndMakeTest(@AuthenticationPrincipal UserAccount userAccount,
                                                        @PathVariable(value = "modelId") Long modelId){
        scoringModelService.activateScoringModel(userAccount, modelId);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/testing-model/creating-new-test?action=activated-model-and-test");
        return modelAndView;
    }

    @PreAuthorize("hasAnyAuthority(T(com.korotkov.main.enums.UserRoleEnum).LEVEL_THREE," +
            "T(com.korotkov.main.enums.UserRoleEnum).LEVEL_FOUR," +
            "T(com.korotkov.main.enums.UserRoleEnum).LEVEL_FIVE)")
    @RequestMapping(value = "/creating-model/scoring-model/{modelId}/deactivate", method = RequestMethod.GET)
    public ModelAndView scoringModelPageDeactivate(@AuthenticationPrincipal UserAccount userAccount,
                                                   @PathVariable(value = "modelId") Long modelId){
        scoringModelService.deactivateScoringModel(userAccount, modelId);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/creating-model/scoring-model/" + modelId + "?action=deactivate");
        return modelAndView;
    }

    @PreAuthorize("hasAnyAuthority(T(com.korotkov.main.enums.UserRoleEnum).LEVEL_FOUR," +
            "T(com.korotkov.main.enums.UserRoleEnum).LEVEL_FIVE)")
    @RequestMapping(value = "/creating-model/scoring-model/{modelId}/attribute/{idInDataBase}/attribute-edit-score", method = RequestMethod.GET)
    public ModelAndView scoringAttributeValueEditPage(@AuthenticationPrincipal UserAccount userAccount,
                                                      @PathVariable(value = "modelId") Long modelId,
                                                      @PathVariable(value = "idInDataBase") Long idInDataBase){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/modelCreation/scoringModelAttributeScoreEdit");
        modelAndView.addObject("scoringModelAttribute",
                scoringModelParameterService.getModelAttributeValue(userAccount, modelId, idInDataBase));
        return modelAndView;
    }

    @PreAuthorize("hasAnyAuthority(T(com.korotkov.main.enums.UserRoleEnum).LEVEL_FOUR," +
            "T(com.korotkov.main.enums.UserRoleEnum).LEVEL_FIVE)")
    @RequestMapping(value = "/creating-model/scoring-model/{modelId}/attribute/{idInDataBase}/attribute-edit-score", method = RequestMethod.POST)
    public ModelAndView scoringAttributeValueEditPagePost(@AuthenticationPrincipal UserAccount userAccount,
                                                          @PathVariable(value = "modelId") Long modelId,
                                                          @PathVariable(value = "idInDataBase") Long idInDataBase,
                                                          @ModelAttribute("scoringModelAttribute") ScoringModelParameter scoringModelParameter){
        scoringModelParameterService.setChangedScoreInAttributeValue(userAccount,modelId,idInDataBase,scoringModelParameter);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/creating-model/scoring-model/" + modelId + "?action=change-attribute-score");
        return modelAndView;
    }
}
