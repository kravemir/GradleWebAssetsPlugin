package org.kravemir.gradle.webassets;

import com.google.template.soy.SoyFileSet;
import com.google.template.soy.jssrc.SoyJsSrcOptions;
import org.gradle.api.DefaultTask;
import org.gradle.api.file.ConfigurableFileTree;
import org.gradle.api.file.FileCollection;
import org.gradle.api.tasks.InputFiles;
import org.gradle.api.tasks.OutputFile;
import org.gradle.api.tasks.TaskAction;

import javax.inject.Inject;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

// TODO: extract to own plugin
public class SoyCompileTask extends DefaultTask {
    String inputDir = "src/main/templates";
    String include = "**/*.soy";

    String outputFile;

    @Inject
    public SoyCompileTask() {
    }

    @InputFiles
    public FileCollection getTemplateFiles() {
        ConfigurableFileTree fileTree = getProject().fileTree(inputDir);
        fileTree.include(include);
        return fileTree;
    }

    @OutputFile
    public File getTemplateOutput() {
        return new File(outputFile);
    }

    @TaskAction
    void compile() throws IOException {
        FileCollection tree = getTemplateFiles();

        SoyFileSet.Builder builder = SoyFileSet.builder();
        for(File f : tree) {
            builder.add(f);
        }

        OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(getTemplateOutput()));
        for(String s : builder.build().compileToJsSrc(new SoyJsSrcOptions(),null)) {
            out.write(s);
        }
        out.close();
    }

    public String getInputDir() {
        return inputDir;
    }

    public void setInputDir(String inputDir) {
        this.inputDir = inputDir;
    }

    public String getInclude() {
        return include;
    }

    public void setInclude(String include) {
        this.include = include;
    }

    public String getOutputFile() {
        return outputFile;
    }

    public void setOutputFile(String outputFile) {
        this.outputFile = outputFile;
    }
}
