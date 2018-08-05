package com.plugin101.vipergenerator.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.psi.*;
import com.plugin101.vipergenerator.Constants;
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
        GenDialogWrapper dialogWrapper = new GenDialogWrapper(anActionEvent.getProject(), "VIPEnoR for Gang of Four");
        dialogWrapper.show();

        if (dialogWrapper.isOK()) {
            // create file from template files
            generateClassFile(anActionEvent, mSelectedDir);
        }
    }

    public void generateClassFile(AnActionEvent anActionEvent, PsiDirectory selectedDir) {
        String className = GeneratedClass.INSTANCE.getClassName();
        if (className == null || className.isEmpty()) {
            return;
        }

        Map<String, String> params = new HashMap<String, String>();
        params.put(Constants.PARAM_IMPORT_BASE_VIPER, "mobi.quoine.ui.base");
        params.put(Constants.PARAM_IMPORT_APP_DATA_SERVICE, "mobi.quoine.data.app");
        params.put(Constants.PARAM_IMPORT_LOCAL_DATA_SERVICE, "mobi.quoine.data.local");
        params.put(Constants.PARAM_IMPORT_REMOTE_DATA_SERVICE, "mobi.quoine.data.remote");
        params.put(Constants.PARAM_IMPORT_DI, "mobi.quoine.di");

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
