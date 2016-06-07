package org.kravemir.gradle.webassets;

import org.gradle.api.DefaultTask;
import org.gradle.api.DomainObjectCollection;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.kravemir.gradle.sass.SassCompileTask;

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

                assetsTask.dependsOn(
                        project.getTasks().create(build.getName() + "Sass", SassCompileTask.class, t -> {
                            t.setGroup("webAssets");
                            t.setConfiguration(sassBuildConfiguration);
                        })
                );
            }
        }));
    }
}
