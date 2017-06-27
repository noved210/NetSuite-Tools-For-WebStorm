package actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import projectsettings.ProjectSettingsController;

/**
 * Created by greg.smith on 6/27/2017.
 */
public class ShowSDFAppAction extends DefaultActionGroup {
    @Override
    public void update(AnActionEvent e) {
        final Project project = e.getProject();
        ProjectSettingsController projectSettingsController = new ProjectSettingsController(project);
        Boolean showAction = projectSettingsController.getNsIsSDFProject();
        e.getPresentation().setEnabledAndVisible(showAction);
    }
}
