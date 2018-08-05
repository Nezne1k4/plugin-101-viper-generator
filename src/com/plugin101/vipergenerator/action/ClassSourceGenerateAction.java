package com.plugin101.vipergenerator.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.psi.*;
import com.plugin101.vipergenerator.model.GeneratedClass;
import com.plugin101.vipergenerator.ui.GenDialogWrapper;

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
        JavaDirectoryService.getInstance().createClass(selectedDir,
                GeneratedClass.INSTANCE.getClassName(), "ContractsTemplate.java", true);
        JavaDirectoryService.getInstance().createClass(selectedDir,
                GeneratedClass.INSTANCE.getClassName(), "DataManagerTemplate.java", true);
        JavaDirectoryService.getInstance().createClass(selectedDir,
                GeneratedClass.INSTANCE.getClassName(), "InteractorTemplate.java", true);
        JavaDirectoryService.getInstance().createClass(selectedDir,
                GeneratedClass.INSTANCE.getClassName(), "PresenterTemplate.java", true);
    }
}
