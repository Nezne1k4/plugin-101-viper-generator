package com.plugin101.vipergenerator.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.psi.*;
import com.plugin101.vipergenerator.common.GeneratorType;
import com.plugin101.vipergenerator.model.GeneratedClass;
import com.plugin101.vipergenerator.ui.GenDialogWrapper;

public class ClassAllGenerateAction extends AnAction {

    private PsiDirectory selectedDir;

    /**
     * Method which is called during registered action event.
     * It handles visibility of our menu action and getting selected dir from given action.
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
     * Method which is called after performing action
     */
    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        GenDialogWrapper dialogWrapper = new GenDialogWrapper(anActionEvent.getProject(), "Gang of 4 and Unittest generator", GeneratorType.BOTH);
        dialogWrapper.show();

        if (dialogWrapper.isOK()) {
            new ClassSourceGenerateAction().generateClassFile(anActionEvent, selectedDir);
            new ClassUnittestGenerateAction().generateClassFile(anActionEvent, selectedDir);
        }
    }
}
