package com.plugin101.vipergenerator.ui;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.plugin101.vipergenerator.common.GeneratorType;
import com.plugin101.vipergenerator.model.GeneratedClass;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class GenDialogWrapper extends DialogWrapper {

    private ViperGeneratorForm classGenForm;

    public GenDialogWrapper(@Nullable Project project, @Nullable String title, GeneratorType type) {
        super(project);
        classGenForm = new ViperGeneratorForm(GeneratedClass.INSTANCE, type);
        init();
        setTitle(title == null ? "VIPEnoR generator" : title);
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        JComponent jMain = classGenForm.getContent();
        return jMain;
    }

    @Override
    protected void dispose() {
        super.dispose();
    }
}
