package com.plugin101.vipergenerator.ui;

import com.plugin101.vipergenerator.common.Constants;
import com.plugin101.vipergenerator.common.GeneratorType;
import com.plugin101.vipergenerator.model.GeneratedClass;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

public class ViperGeneratorForm {
    private JPanel jMainPanel;
    private JTextField jtfClassName;
    private JTextField jtfPackageBase;
    private JTextField jtfPackageAppData;
    private JTextField jtfPackageLocalData;
    private JTextField jtfPackageRemoteData;
    private JTextField jtfPackageDi;
    private JTextField jtfPackageMockContextTest;
    private JCheckBox jIsLibrariesChanged;

    public ViperGeneratorForm(final GeneratedClass generatedClass, final GeneratorType genType) {
        // default disable lib
        setDefaultLibraries(generatedClass);
        generatedClass.setIsLibByDefault(true);
        updateLibrariesViewEditable(false, genType);

        jMainPanel.setPreferredSize(new Dimension(100, 300));

        jtfClassName.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                generatedClass.setClassName(jtfClassName.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                generatedClass.setClassName(jtfClassName.getText());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                generatedClass.setClassName(jtfClassName.getText());
            }
        });

        jtfPackageBase.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                generatedClass.setImportBasePackage(jtfPackageBase.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                generatedClass.setImportBasePackage(jtfPackageBase.getText());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                generatedClass.setImportBasePackage(jtfPackageBase.getText());
            }
        });

        jtfPackageAppData.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                generatedClass.setImportAppDataPackage(jtfPackageAppData.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                generatedClass.setImportAppDataPackage(jtfPackageAppData.getText());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                generatedClass.setImportAppDataPackage(jtfPackageAppData.getText());
            }
        });

        jtfPackageLocalData.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                generatedClass.setImportLocalDataPackage(jtfPackageLocalData.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                generatedClass.setImportLocalDataPackage(jtfPackageLocalData.getText());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                generatedClass.setImportLocalDataPackage(jtfPackageLocalData.getText());
            }
        });

        jtfPackageRemoteData.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                generatedClass.setImportRemotePackage(jtfPackageRemoteData.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                generatedClass.setImportRemotePackage(jtfPackageRemoteData.getText());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                generatedClass.setImportRemotePackage(jtfPackageRemoteData.getText());
            }
        });

        jtfPackageDi.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                generatedClass.setImportDiPackage(jtfPackageDi.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                generatedClass.setImportDiPackage(jtfPackageDi.getText());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                generatedClass.setImportDiPackage(jtfPackageDi.getText());
            }
        });

        jtfPackageMockContextTest.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                generatedClass.setImportMockContextPackage(jtfPackageMockContextTest.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                generatedClass.setImportMockContextPackage(jtfPackageMockContextTest.getText());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                generatedClass.setImportMockContextPackage(jtfPackageMockContextTest.getText());
            }
        });

        jIsLibrariesChanged.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                AbstractButton abstractButton =
                        (AbstractButton) changeEvent.getSource();
                ButtonModel buttonModel = abstractButton.getModel();
                updateLibrariesViewEditable(buttonModel.isSelected(), genType);
                generatedClass.setIsLibByDefault(!buttonModel.isSelected());
            }
        });
    }

    private void setDefaultLibraries(GeneratedClass generatedClass) {
        jtfPackageBase.setText(Constants.DEFAULT_LIB_BASE);
        jtfPackageAppData.setText(Constants.DEFAULT_LIB_APP_SERVICE);
        jtfPackageLocalData.setText(Constants.DEFAULT_LIB_LOCAL_SERVICE);
        jtfPackageRemoteData.setText(Constants.DEFAULT_LIB_REMOTE_SERVICE);
        jtfPackageDi.setText(Constants.DEFAULT_LIB_DI);
        jtfPackageMockContextTest.setText(Constants.DEFAULT_LIB_TEST);
    }

    private void updateLibrariesViewEditable(boolean selected, GeneratorType genType) {
        jtfPackageBase.setEnabled(selected);
        jtfPackageAppData.setEnabled(selected);
        jtfPackageLocalData.setEnabled(selected);
        jtfPackageRemoteData.setEnabled(selected);

        if (genType == GeneratorType.UNIT_TEST_ONLY) {
            jtfPackageDi.setEnabled(false);
            jtfPackageDi.setText("");
        } else {
            jtfPackageDi.setEnabled(selected);
        }

        if (genType == GeneratorType.GANG_OF_FOUR) {
            jtfPackageMockContextTest.setEnabled(false);
            jtfPackageMockContextTest.setText("");
        } else {
            jtfPackageMockContextTest.setEnabled(selected);
        }
    }

    public JComponent getContent() {
        return jMainPanel;
    }
}
