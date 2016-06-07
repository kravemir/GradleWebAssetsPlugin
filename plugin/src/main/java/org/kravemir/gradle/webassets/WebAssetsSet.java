package org.kravemir.gradle.webassets;

import groovy.lang.Closure;

import java.io.File;

public class WebAssetsSet {

    private final String name;

    private File baseSrcDir;
    private File baseOutDir;


    private boolean minify = false;
    private String[] registerInSourceSets = new String[0];

    private Closure sassClosure;
    private Closure soyClosure;

    public WebAssetsSet(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public File getBaseSrcDir() {
        return baseSrcDir;
    }

    public void setBaseSrcDir(File baseSrcDir) {
        this.baseSrcDir = baseSrcDir;
    }

    public File getBaseOutDir() {
        return baseOutDir;
    }

    public void setBaseOutDir(File baseOutDir) {
        this.baseOutDir = baseOutDir;
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

    public void soy(Closure closure) { this.soyClosure = closure; }

    public Closure getSoyClosure() {
        return soyClosure;
    }
}
