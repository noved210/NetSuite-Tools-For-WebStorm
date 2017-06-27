package actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import projectsettings.ProjectSettingsController;

/**
 * Created by greg.smith on 6/26/2017.
 */
public class SetBaseFolderAction extends AnAction {
    @Override
    public void update(AnActionEvent e) {
        final Project project = e.getProject();
        ProjectSettingsController projectSettingsController = new ProjectSettingsController(project);
        Boolean showAction = projectSettingsController.hasAllProjectSettings();
        final VirtualFile[] files = e.getData(PlatformDataKeys.VIRTUAL_FILE_ARRAY);
        if(files.length != 1){
            showAction = false;
        } else{
            showAction = files[0].isDirectory();
        }
        e.getPresentation().setVisible(showAction);
        e.getPresentation().setEnabled(showAction);


    }

    @Override
    public void actionPerformed(AnActionEvent e) {

        final Project project = e.getProject();

        project.getBaseDir().getName();
        final VirtualFile[] files = e.getData(PlatformDataKeys.VIRTUAL_FILE_ARRAY);

        if(files.length == 1 && files[0].isDirectory()){
            String folderName = files[0].getName();
            if(files[0].equals(project.getBaseDir())){ //If Base Directory, then set as empty
                folderName = "";
            }
            ProjectSettingsController projectSettingsController = new ProjectSettingsController(project);
            projectSettingsController.setNsBaseFolder(folderName);
        }
    }
}
