package org.kravemir.gradle.webassets;

import org.gradle.api.DomainObjectCollection;
import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class WebAssetsPlugin implements Plugin<Project> {
    @Override
    public void apply(Project project) {
        DomainObjectCollection<WebAssetsTask> webAssetsBuildConfigurations = project.container(
                WebAssetsTask.class,
                name -> project.getTasks().create(name + "WebAssets", WebAssetsTask.class, task -> {
                    task.setGroup("webAssets");
                    task.setSetName(name);
                })
        );
        project.getExtensions().add("webAssets", webAssetsBuildConfigurations);
    }
}
