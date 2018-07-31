package com.plugin101.vipergenerator.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.psi.*;
import com.plugin101.vipergenerator.model.GeneratedClass;
import com.plugin101.vipergenerator.ui.GenDialogWrapper;

public class ClassGenerateAction extends AnAction {

    private PsiDirectory selectedDir;

    /**
     * Method which is called during registered action event. It handles visibility of our menu action
     * and getting selected dir from given action.
     */
    @Override
    public void update(AnActionEvent e) {
        PsiElement selectedElement = CommonDataKeys.PSI_ELEMENT.getData(e.getDataContext());
        if (selectedElement instanceof PsiDirectory) {
            selectedDir = (PsiDirectory) selectedElement;
        } else if (selectedElement instanceof PsiClass) {
            PsiFile psiFile = selectedElement.getContainingFile();
            selectedDir = psiFile.getContainingDirectory();
        } else {
            e.getPresentation().setEnabledAndVisible(false);
        }

    }

    /**
     * Method which is called after performing action. It shows our custom dialog defined by {@link
     * GenDialogWrapper} and handle given information from {@link GeneratedClass} for generating our
     * custom class from templates.
     */
    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        GenDialogWrapper dialogWrapper = new GenDialogWrapper(anActionEvent.getProject());
        dialogWrapper.show();

        if (dialogWrapper.isOK()) {

            String template;
            if (GeneratedClass.INSTANCE.isHasPsvm()) {
                template = "PsvmClassTemplate.java";
            } else {
                //template = "BasicClassTemplate.java";
                template = "ContractsClassTemplate.java";
            }

            JavaDirectoryService.getInstance().createClass(selectedDir,
                    GeneratedClass.INSTANCE.getClassName(), template, true);
        }
    }
}
