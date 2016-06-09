package org.kravemir.gradle.webassets;

import groovy.lang.Closure;
import org.gradle.api.DefaultTask;
import org.gradle.api.GradleException;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.plugins.JavaPluginConvention;

import java.io.File;
import java.util.Collections;

public class WebAssetsTask extends DefaultTask {

    private String setName;

    private File baseSrcDir;
    private File baseOutDir;

    private boolean minify = false;
    private String[] registerInSourceSets = new String[0];

    private SassCompileTask sassCompileTask;
    private SoyCompileTask soyCompileTask;

    public String getSetName() {
        return setName;
    }

    public void setSetName(String setName) {
        this.setName = setName;
    }

    public File getBaseSrcDir() {
        return baseSrcDir;
    }

    public void setBaseSrcDir(File baseSrcDir) {
        this.baseSrcDir = baseSrcDir;
    }

    public void setBaseSrcDir(String filename) { setBaseSrcDir(getProject().file(filename));}

    public File getBaseOutDir() {
        return baseOutDir;
    }

    public void setBaseOutDir(String filename) { setBaseOutDir(getProject().file(filename));}

    public void setBaseOutDir(File baseOutDir) {
        this.baseOutDir = baseOutDir;
    }

    public boolean getMinify() {
        return minify;
    }

    public void setMinify(boolean minify) {
        this.minify = minify;
    }

    public void sass(Closure closure) {
        if(sassCompileTask != null)
            throw new GradleException(String.format("SASS has already been defined for %s resourceSet", getSetName()));
        sassCompileTask = getProject().getTasks().create(
                getSetName() + "Sass",
                SassCompileTask.class
        );
        sassCompileTask.setAssetsSet(this);
        sassCompileTask.setGroup("webAssets");
        getProject().configure(sassCompileTask, closure);
        dependsOn(sassCompileTask);
    }

    public SassCompileTask getSassCompileTask() {
        return sassCompileTask;
    }

    public void soy(Closure closure) {
        if(soyCompileTask != null)
            throw new GradleException(String.format("SOY has already been defined for %s resourceSet", getSetName()));
        soyCompileTask = getProject().getTasks().create(getSetName()+"Soy", SoyCompileTask.class);
        soyCompileTask.setGroup("webAssets");
        getProject().configure(soyCompileTask, closure);
        dependsOn(soyCompileTask);
    }

    public SoyCompileTask getSoyCompileTask() {
        return soyCompileTask;
    }

    public void registerInSourceSets(String... sourceSets) {
        if(sourceSets == null || sourceSets.length == 0) return;

        try {
            JavaPluginConvention javaPlugin = getProject().getConvention().getPlugin(JavaPluginConvention.class);
            if (javaPlugin == null) {
                throw new GradleException("You must apply the java plugin if you're using 'registerInSourceSets' functionality.");
            }

            for (String sourceSet : sourceSets) {
                javaPlugin.getSourceSets().getByName(sourceSet).getOutput().dir(
                        Collections.singletonMap("builtBy", this),
                        getBaseOutDir()
                );
            }
        } catch (Exception e) {
            throw new GradleException("You must apply the java plugin if you're using 'registerInSourceSets' functionality.");
        }
    }
}
