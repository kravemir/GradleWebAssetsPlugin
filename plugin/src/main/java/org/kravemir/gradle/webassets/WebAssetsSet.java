package org.kravemir.gradle.webassets;

import groovy.lang.Closure;

import java.io.File;

public class WebAssetsSet {

    private final String name;

    private File baseDir;
    private boolean minify = false;
    private String[] registerInSourceSets = new String[0];

    private Closure sassClosure;

    public WebAssetsSet(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public File getBaseDir() {
        return baseDir;
    }

    public void setBaseDir(File baseDir) {
        this.baseDir = baseDir;
    }

    public boolean getMinify() {
        return minify;
    }

    public void setMinify(boolean minify) {
        this.minify = minify;
    }

    public String[] getRegisterInSourceSets() {
        return registerInSourceSets;
    }

    public void setRegisterInSourceSets(String[] registerInSourceSets) {
        this.registerInSourceSets = registerInSourceSets;
    }

    public void sass(Closure closure) {
        this.sassClosure = closure;
    }

    public Closure getSassClosure() {
        return sassClosure;
    }
}
