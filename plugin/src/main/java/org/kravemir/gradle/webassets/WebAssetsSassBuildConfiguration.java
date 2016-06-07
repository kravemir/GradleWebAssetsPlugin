package org.kravemir.gradle.webassets;

import org.kravemir.gradle.sass.api.SassBuildConfiguration;

import java.io.File;
import java.nio.file.Paths;

public class WebAssetsSassBuildConfiguration implements SassBuildConfiguration {
    private final WebAssetsSet assetsSet;

    private File srcDir = null;
    private File outDir = null;
    private String outSubDir = null;
    private String include = null;
    private String exclude = null;
    private Boolean minify = null;

    public WebAssetsSassBuildConfiguration(WebAssetsSet assetsSet) {
        this.assetsSet = assetsSet;
    }

    @Override
    public File getBuildDir() {
        if(outSubDir == null)
            return getOutDir();
        return Paths.get(getOutDir().getPath(),outSubDir).toFile();
    }

    @Override
    public File getSrcDir() {
        if(srcDir == null)
            return assetsSet.getBaseSrcDir();
        return srcDir;
    }

    public void setSrcDir(File srcDir) {
        this.srcDir = srcDir;
    }

    public File getOutDir() {
        return outDir;
    }

    public void setOutDir(File outDir) {
        this.outDir = outDir;
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
}
