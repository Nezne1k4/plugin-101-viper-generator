package com.plugin101.vipergenerator.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.*;
import com.intellij.psi.impl.file.PsiDirectoryFactory;
import com.plugin101.vipergenerator.common.Constants;
import com.plugin101.vipergenerator.common.GeneratorType;
import com.plugin101.vipergenerator.model.GeneratedClass;
import com.plugin101.vipergenerator.ui.GenDialogWrapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClassUnittestGenerateAction extends AnAction {
    private static final String DEFAULT_ANDROID_SOURCE_FOLDER = "src";

    private PsiDirectory mSelectedSourceDir;

    /**
     * Method which is called during registered action event.
     * It handles visibility of our menu action and getting selected dir from given action.
     */
    @Override
    public void update(AnActionEvent e) {
        PsiElement selectedElement = CommonDataKeys.PSI_ELEMENT.getData(e.getDataContext());
        if (selectedElement instanceof PsiDirectory) {
            mSelectedSourceDir = (PsiDirectory) selectedElement;
        } else if (selectedElement instanceof PsiClass) {
            PsiFile psiFile = selectedElement.getContainingFile();
            mSelectedSourceDir = psiFile.getContainingDirectory();
        } else {
            e.getPresentation().setEnabledAndVisible(false);
        }
    }

    /**
     * Method which is called after performing action
     */
    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        GenDialogWrapper dialogWrapper = new GenDialogWrapper(anActionEvent.getProject(), "VIPEnoR UnitTest Generator", GeneratorType.UNIT_TEST_ONLY);
        dialogWrapper.show();
        if (dialogWrapper.isOK()) {
            generateClassFile(anActionEvent, mSelectedSourceDir);
        }
    }

    public void generateClassFile(AnActionEvent anActionEvent, PsiDirectory mSelectedSourceDir) {
        GeneratedClass classInfo = GeneratedClass.INSTANCE;
        String className = classInfo.getClassName();
        if (className == null || className.isEmpty()) {
            return;
        }

        final String rootTestFolder = "test";
        Project project = getEventProject(anActionEvent);
        try {
            VirtualFile testVirtualFile = createTestModule(project, mSelectedSourceDir, rootTestFolder);
            if (testVirtualFile == null) {
                return;
            }

            PsiDirectory testPsiDirectory = PsiDirectoryFactory.getInstance(project).createDirectory(testVirtualFile);
            // more input parameters
            Map<String, String> params = new HashMap<String, String>();
            if (classInfo.isLibByDefault()) {
                params.put(Constants.PARAM_IMPORT_BASE_VIPER, Constants.DEFAULT_LIB_BASE);
                params.put(Constants.PARAM_IMPORT_APP_DATA_SERVICE, Constants.DEFAULT_LIB_APP_SERVICE);
                params.put(Constants.PARAM_IMPORT_LOCAL_DATA_SERVICE, Constants.DEFAULT_LIB_LOCAL_SERVICE);
                params.put(Constants.PARAM_IMPORT_REMOTE_DATA_SERVICE, Constants.DEFAULT_LIB_REMOTE_SERVICE);
                params.put(Constants.PARAM_IMPORT_MOCK_TEST, Constants.DEFAULT_LIB_TEST);
            } else {
                params.put(Constants.PARAM_IMPORT_BASE_VIPER, classInfo.getImportBasePackage());
                params.put(Constants.PARAM_IMPORT_APP_DATA_SERVICE, classInfo.getImportAppDataPackage());
                params.put(Constants.PARAM_IMPORT_LOCAL_DATA_SERVICE, classInfo.getImportLocalDataPackage());
                params.put(Constants.PARAM_IMPORT_REMOTE_DATA_SERVICE, classInfo.getImportRemotePackage());
                params.put(Constants.PARAM_IMPORT_MOCK_TEST, classInfo.getImportMockContextPackage());
            }
            // create file from template files
            JavaDirectoryService.getInstance().createClass(testPsiDirectory,
                    className, "UnittestInteractorTemplate.java", true, params);
            JavaDirectoryService.getInstance().createClass(testPsiDirectory,
                    className, "UnittestPresenterTemplate.java", true, params);
            JavaDirectoryService.getInstance().createClass(testPsiDirectory,
                    className, "UnittestDataManagerTemplate.java", true, params);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Create test module if not existing
     */
    private VirtualFile createTestModule(final Project project, PsiDirectory selectedSourceDir, String rootTestFolder) throws IOException {
        final String testPath = replaceToCreateTestFolderPath(selectedSourceDir.toString(), project, rootTestFolder);
        if (testPath == null) {
            System.out.println("NOT FOUND TEST FOLDER");
            return null;
        } else {
            // https://intellij-support.jetbrains.com/hc/en-us/community/posts/206754235-Write-access-is-allowed-inside-write-action-only-see-com-intellij-openapi-application-Application-runWriteAction-
            // must implement Runnable and WriteCommandAction to avoid `Write access` issue
            final VirtualFile[] virtualFile = new VirtualFile[1];
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    try {
                        virtualFile[0] = createFolderIfNotExist(project, testPath);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            };
            WriteCommandAction.runWriteCommandAction(project, runnable);
            return virtualFile[0];
        }
    }

    private String replaceToCreateTestFolderPath(String srcPath, Project project, String rootTestFolder) {
        Pattern pattern = Pattern.compile(String.format(".*%s/(.*)/java.*", DEFAULT_ANDROID_SOURCE_FOLDER));
        Matcher m = pattern.matcher(srcPath);
        if (m.matches()) {
            final String fullTestPath = srcPath.replaceAll(m.group(1), rootTestFolder);
            final String projectBaseDir = project.getBaseDir().toString().replace("file:///", "");
            final String testPathOnly = fullTestPath.replaceAll(".*" + projectBaseDir, "");
            return testPathOnly;
        } else {
            return null;
        }
    }

    /**
     * Utils
     */
    private VirtualFile createFolderIfNotExist(Project project, String folder) throws IOException {
        VirtualFile directory = project.getBaseDir();
        String[] folders = folder.split("/");
        for (String childFolder : folders) {
            if (childFolder.isEmpty()) {
                continue;
            }
            VirtualFile childDirectory = directory.findChild(childFolder);
            if (childDirectory != null && childDirectory.isDirectory()) {
                directory = childDirectory;
            } else {
                directory = directory.createChildDirectory(project, childFolder);
            }
        }
        return directory;
    }
}
