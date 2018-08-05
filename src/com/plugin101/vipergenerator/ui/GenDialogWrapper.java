package com.plugin101.vipergenerator.ui;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.plugin101.vipergenerator.model.GeneratedClass;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class GenDialogWrapper extends DialogWrapper {

  private ClassGeneratorForm classGenForm;

  public GenDialogWrapper(@Nullable Project project, @Nullable String title) {
    super(project);
    classGenForm = new ClassGeneratorForm(GeneratedClass.INSTANCE);
    init();
    setTitle(title == null ? "VIPEnoR generator": title);
  }

  @Nullable
  @Override
  protected JComponent createCenterPanel() {
    return classGenForm.getContent();
  }

}
