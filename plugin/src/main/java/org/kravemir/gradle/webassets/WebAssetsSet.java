package org.kravemir.gradle.webassets;

import groovy.lang.Closure;
import org.gradle.api.GradleException;
import org.gradle.api.Project;

import java.io.File;

public class WebAssetsSet {

    private final Project project;
    private final String name;

    private File baseSrcDir;
    private File baseOutDir;

    private boolean minify = false;
    private String[] registerInSourceSets = new String[0];

    private SassCompileTask sassCompileTask;
    private Closure soyClosure;

    public WebAssetsSet(Project project, String name) {
        this.project = project;
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

    public void setBaseSrcDir(String filename) { setBaseSrcDir(project.file(filename));}

    public File getBaseOutDir() {
        return baseOutDir;
    }

    public void setBaseOutDir(String filename) { setBaseOutDir(project.file(filename));}

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
        if(sassCompileTask != null)
            throw new GradleException(String.format("SASS has already been defined for %s resourceSet", name));
        sassCompileTask = getProject().getTasks().create(
                name + "Sass",
                SassCompileTask.class
        );
        sassCompileTask.setAssetsSet(this);
        sassCompileTask.setGroup("webAssets");
        project.configure(sassCompileTask, closure);
    }

    public SassCompileTask getSassCompileTask() {
        return sassCompileTask;
    }

    public void soy(Closure closure) { this.soyClosure = closure; }

    public Closure getSoyClosure() {
        return soyClosure;
    }

    public Project getProject() {
        return project;
    }
}
