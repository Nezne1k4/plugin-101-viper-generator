package com.plugin101.vipergenerator.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.psi.*;
import com.plugin101.vipergenerator.common.Constants;
import com.plugin101.vipergenerator.common.GeneratorType;
import com.plugin101.vipergenerator.model.GeneratedClass;
import com.plugin101.vipergenerator.ui.GenDialogWrapper;

import java.util.HashMap;
import java.util.Map;

public class ClassSourceGenerateAction extends AnAction {

    private PsiDirectory mSelectedDir;

    /**
     * Method which is called during registered action event.
     * It handles visibility of our menu action and getting selected dir from given action.
     */
    @Override
    public void update(AnActionEvent e) {
        PsiElement selectedElement = CommonDataKeys.PSI_ELEMENT.getData(e.getDataContext());
        if (selectedElement instanceof PsiDirectory) {
            mSelectedDir = (PsiDirectory) selectedElement;
        } else if (selectedElement instanceof PsiClass) {
            PsiFile psiFile = selectedElement.getContainingFile();
            mSelectedDir = psiFile.getContainingDirectory();
        } else {
            e.getPresentation().setEnabledAndVisible(false);
        }
    }

    /**
     * Method which is called after performing action
     */
    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        GenDialogWrapper dialogWrapper = new GenDialogWrapper(anActionEvent.getProject(), "VIPEnoR for Gang of Four", GeneratorType.GANG_OF_FOUR);
        dialogWrapper.show();

        if (dialogWrapper.isOK()) {
            // create file from template files
            generateClassFile(anActionEvent, mSelectedDir);
        }
    }

    public void generateClassFile(AnActionEvent anActionEvent, PsiDirectory selectedDir) {
        GeneratedClass classInfo = GeneratedClass.INSTANCE;
        String className = classInfo.getClassName();
        if (className == null || className.isEmpty()) {
            return;
        }

        Map<String, String> params = new HashMap<String, String>();
        if (classInfo.isLibByDefault()) {
            params.put(Constants.PARAM_IMPORT_BASE_VIPER, Constants.DEFAULT_LIB_BASE);
            params.put(Constants.PARAM_IMPORT_APP_DATA_SERVICE, Constants.DEFAULT_LIB_APP_SERVICE);
            params.put(Constants.PARAM_IMPORT_LOCAL_DATA_SERVICE, Constants.DEFAULT_LIB_LOCAL_SERVICE);
            params.put(Constants.PARAM_IMPORT_REMOTE_DATA_SERVICE, Constants.DEFAULT_LIB_REMOTE_SERVICE);
            params.put(Constants.PARAM_IMPORT_DI, Constants.DEFAULT_LIB_DI);
        } else {
            params.put(Constants.PARAM_IMPORT_BASE_VIPER, classInfo.getImportBasePackage());
            params.put(Constants.PARAM_IMPORT_APP_DATA_SERVICE, classInfo.getImportAppDataPackage());
            params.put(Constants.PARAM_IMPORT_LOCAL_DATA_SERVICE, classInfo.getImportLocalDataPackage());
            params.put(Constants.PARAM_IMPORT_REMOTE_DATA_SERVICE, classInfo.getImportRemotePackage());
            params.put(Constants.PARAM_IMPORT_DI, classInfo.getImportBasePackage());
        }
        JavaDirectoryService.getInstance().createClass(
                selectedDir, className, "ContractsTemplate.java", true, params);
        JavaDirectoryService.getInstance().createClass(
                selectedDir, className, "DataManagerTemplate.java", true, params);
        JavaDirectoryService.getInstance().createClass(
                selectedDir, className, "InteractorTemplate.java", true, params);
        JavaDirectoryService.getInstance().createClass(
                selectedDir, className, "PresenterTemplate.java", true, params);
    }
}
