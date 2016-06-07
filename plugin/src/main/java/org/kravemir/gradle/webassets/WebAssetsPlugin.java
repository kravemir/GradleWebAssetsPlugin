package org.kravemir.gradle.webassets;

import org.gradle.api.*;
import org.gradle.api.plugins.JavaPluginConvention;
import org.kravemir.gradle.sass.SassBuildResourceSet;
import org.kravemir.gradle.sass.SassCompileTask;

import java.util.Collections;

public class WebAssetsPlugin implements Plugin<Project> {
    @Override
    public void apply(Project project) {
        DomainObjectCollection<WebAssetsSet> webAssetsBuildConfigurations = project.container(WebAssetsSet.class);
        project.getExtensions().add("webAssets", webAssetsBuildConfigurations);

        project.afterEvaluate(p -> webAssetsBuildConfigurations.all(build -> {
            DefaultTask assetsTask = project.getTasks().create(build.getName() + "WebAssets", DefaultTask.class);
            assetsTask.setGroup("webAssets");

            if(build.getSassClosure() != null) {
                WebAssetsSassBuildConfiguration sassBuildConfiguration = new WebAssetsSassBuildConfiguration(build);
                project.configure(sassBuildConfiguration,build.getSassClosure());

                String taskName = build.getName() + "Sass";
                assetsTask.dependsOn(project.getTasks().create(taskName, SassCompileTask.class, t -> {
                    t.setGroup("webAssets");
                    t.setConfiguration(sassBuildConfiguration);
                }));
            }

            if(build.getSoyClosure() != null) {
                String taskName = build.getName() + "Soy";
                assetsTask.dependsOn(project.getTasks().create(taskName, SoyCompileTask.class, t -> {
                    t.setGroup("webAssets");
                    project.configure(t, build.getSoyClosure());
                }));
            }

            registerInSourceSets(project,build,assetsTask);
        }));
    }


    private void registerInSourceSets(Project project, WebAssetsSet build, Task task) {
        if(build.getRegisterInSourceSets() == null || build.getRegisterInSourceSets().length == 0) return;

        try {
            JavaPluginConvention javaPlugin = project.getConvention().getPlugin(JavaPluginConvention.class);
            if (javaPlugin == null) {
                throw new GradleException("You must apply the java plugin if you're using 'registerInSourceSets' functionality.");
            }

            for (String sourceSet : build.getRegisterInSourceSets()) {
                javaPlugin.getSourceSets().getByName(sourceSet).getOutput().dir(
                        Collections.singletonMap("builtBy", task),
                        build.getBaseOutDir()
                );
            }
        } catch (Exception e) {
            throw new GradleException("You must apply the java plugin if you're using 'registerInSourceSets' functionality.");
        }
    }
}
