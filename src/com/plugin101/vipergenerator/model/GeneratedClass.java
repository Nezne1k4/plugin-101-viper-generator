package com.plugin101.vipergenerator.model;

public class GeneratedClass {

    public static final GeneratedClass INSTANCE = new GeneratedClass();

    private String className;

    private String importBasePackage;
    private String importAppDataPackage;
    private String importLocalDataPackage;
    private String importRemotePackage;
    private String importDiPackage;
    private String importMockContextPackage;

    private boolean isLibByDefault;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public boolean isLibByDefault() {
        return isLibByDefault;
    }

    public void setIsLibByDefault(boolean isLibByDefault) {
        this.isLibByDefault = isLibByDefault;
    }

    public String getImportBasePackage() {
        return importBasePackage;
    }

    public void setImportBasePackage(String importBasePackage) {
        this.importBasePackage = importBasePackage;
    }

    public String getImportAppDataPackage() {
        return importAppDataPackage;
    }

    public void setImportAppDataPackage(String importAppDataPackage) {
        this.importAppDataPackage = importAppDataPackage;
    }

    public String getImportLocalDataPackage() {
        return importLocalDataPackage;
    }

    public void setImportLocalDataPackage(String importLocalDataPackage) {
        this.importLocalDataPackage = importLocalDataPackage;
    }

    public String getImportRemotePackage() {
        return importRemotePackage;
    }

    public void setImportRemotePackage(String importRemotePackage) {
        this.importRemotePackage = importRemotePackage;
    }

    public String getImportDiPackage() {
        return importDiPackage;
    }

    public void setImportDiPackage(String importDiPackage) {
        this.importDiPackage = importDiPackage;
    }

    public String getImportMockContextPackage() {
        return importMockContextPackage;
    }

    public void setImportMockContextPackage(String importMockContextPackage) {
        this.importMockContextPackage = importMockContextPackage;
    }
}
