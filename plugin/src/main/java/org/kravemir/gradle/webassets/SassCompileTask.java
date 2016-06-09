package org.kravemir.gradle.webassets;

import org.kravemir.gradle.sass.AbstractSassCompileTask;

import java.io.File;
import java.nio.file.Paths;

public class SassCompileTask extends AbstractSassCompileTask {

    private WebAssetsSet assetsSet;

    private File srcDir = null;
    private File outDir = null;
    private String srcSubDir = null;
    private String outSubDir = null;
    private String include = null;
    private String exclude = null;
    private Boolean minify = null;

    @Override
    public File getOutputDirectory() {
        if(outSubDir == null && outDir == null)
            return Paths.get(getOutDir().getPath(), "css").toFile();
        if(outSubDir == null)
            return getOutDir();
        return Paths.get(getOutDir().getPath(),outSubDir).toFile();
    }

    @Override
    public File getSrcDir() {
        if(srcDir == null && srcSubDir == null)
            return Paths.get(assetsSet.getBaseSrcDir().getPath(), "sass").toFile();
        if(srcDir == null)
            return assetsSet.getBaseSrcDir();
        return srcDir;
    }

    public void setSrcDir(File srcDir) {
        this.srcDir = srcDir;
    }

    public void setSrcDir(String srcDir) {
        setSrcDir(assetsSet.getProject().file(srcDir));
    }

    public File getOutDir() {
        if(outDir == null)
            return assetsSet.getBaseOutDir();
        return outDir;
    }

    public void setOutDir(File outDir) {
        this.outDir = outDir;
    }

    public void setOutDir(String outDir) {
        setOutDir(assetsSet.getProject().file(outDir));
    }

    @Override
    public String getInclude() {
        return include;
    }

    public void setInclude(String include) {
        this.include = include;
    }

    @Override
    public String getExclude() {
        return exclude;
    }

    @Override
    public boolean getMinify() {
        if(this.minify != null)
            return this.minify;
        return assetsSet.getMinify();
    }

    public void setExclude(String exclude) {
        this.exclude = exclude;
    }

    public void setAssetsSet(WebAssetsSet assetsSet) {
        this.assetsSet = assetsSet;
    }
}
