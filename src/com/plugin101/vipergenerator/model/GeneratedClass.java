package com.plugin101.vipergenerator.model;

public class GeneratedClass {

  public static final GeneratedClass INSTANCE = new GeneratedClass();

  private String className;

  private boolean isUnittest = true;

  public String getClassName() {
    return className;
  }

  public void setClassName(String className) {
    this.className = className;
  }

  public boolean isUnittest() {
    return isUnittest;
  }

  public void setUnittest(boolean unittest) {
    isUnittest = unittest;
  }
}
