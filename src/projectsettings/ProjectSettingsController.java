package projectsettings;

import com.intellij.credentialStore.CredentialAttributes;
import com.intellij.credentialStore.Credentials;
import netsuite.NSAccount;
import netsuite.NSClient;

import com.intellij.ide.passwordSafe.PasswordSafe;
import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.project.Project;
import netsuite.NSRolesRestServiceController;

import java.util.ArrayList;

public class ProjectSettingsController {

    final private String PROJECT_SETTING_NETSUITE_EMAIL          = "nsProjectEmail";
    final private String PROJECT_SETTING_NETSUITE_BASE_FOLDER    = "nsProjectBaseFolder";
    final private String PROJECT_SETTING_NETSUITE_ROOT_FOLDER    = "nsProjectRootFolder";
    final private String PROJECT_SETTING_NETSUITE_ACCOUNT        = "nsAccount";
    final private String PROJECT_SETTING_NETSUITE_ACCOUNT_NAME   = "nsAccountName";
    final private String PROJECT_SETTING_NETSUITE_ACCOUNT_ROLE   = "nsAccountRole";
    final private String PROJECT_SETTING_NETSUITE_ENVIRONMENT    = "nsEnvironment";
    final private String PROJECT_SETTING_NETSUITE_IS_SDF         = "nsIsSDFProject";

    private final PropertiesComponent propertiesComponent;

    public ProjectSettingsController(Project project) {
        this.propertiesComponent = PropertiesComponent.getInstance(project);
    }

    public String getNsEmail() {
        return propertiesComponent.getValue(PROJECT_SETTING_NETSUITE_EMAIL);
    }

    public void setNsEmail(String nsEmail) {
        if (nsEmail != null && !nsEmail.isEmpty()) {
            propertiesComponent.setValue(PROJECT_SETTING_NETSUITE_EMAIL, nsEmail);
        }
    }

    public String getNsBaseFolder() {
        return propertiesComponent.getValue(PROJECT_SETTING_NETSUITE_BASE_FOLDER);
    }

    public void setNsBaseFolder(String nsBaseFolder) {
        if (nsBaseFolder == null) {
            nsBaseFolder = "";
        }
        propertiesComponent.setValue(PROJECT_SETTING_NETSUITE_BASE_FOLDER, nsBaseFolder);
    }

    public String getNsRootFolder() {
        return propertiesComponent.getValue(PROJECT_SETTING_NETSUITE_ROOT_FOLDER);
    }

    public void setNsRootFolder(String nsRootFolder) {
        if (nsRootFolder == null) {
            nsRootFolder = "";
        }
        propertiesComponent.setValue(PROJECT_SETTING_NETSUITE_ROOT_FOLDER, nsRootFolder);
    }

    public String getNsAccount() {
        return propertiesComponent.getValue(PROJECT_SETTING_NETSUITE_ACCOUNT);
    }

    public void setNsAccount(String nsAccount) {
        if (nsAccount != null && !nsAccount.isEmpty()) {
            propertiesComponent.setValue(PROJECT_SETTING_NETSUITE_ACCOUNT, nsAccount);
        }
    }

    public String getNsAccountName()  {
        return propertiesComponent.getValue(PROJECT_SETTING_NETSUITE_ACCOUNT_NAME);
    }

    public void setNsAccountName(String nsAccountName) {
        if (nsAccountName != null && !nsAccountName.isEmpty()) {
            propertiesComponent.setValue(PROJECT_SETTING_NETSUITE_ACCOUNT_NAME, nsAccountName);
        }
    }

    public String getNsAccountRole()   {
        return propertiesComponent.getValue(PROJECT_SETTING_NETSUITE_ACCOUNT_ROLE);
    }

    public void setNsAccountRole(String nsAccountRole) {
        if (nsAccountRole != null && !nsAccountRole.isEmpty()) {
            propertiesComponent.setValue(PROJECT_SETTING_NETSUITE_ACCOUNT_ROLE, nsAccountRole);
        }
    }

    public String getNsEnvironment() {
        return propertiesComponent.getValue(PROJECT_SETTING_NETSUITE_ENVIRONMENT);
    }

    public void setNsEnvironment(String nsEnvironment) {
        if (nsEnvironment != null && !nsEnvironment.isEmpty()) {
            propertiesComponent.setValue(PROJECT_SETTING_NETSUITE_ENVIRONMENT, nsEnvironment);
        }
    }

    public Boolean getNsIsSDFProject() {
        Boolean isNSSDFProject = propertiesComponent.getBoolean(PROJECT_SETTING_NETSUITE_IS_SDF);
        isNSSDFProject = isNSSDFProject == null ? false : true;
        return isNSSDFProject;
    }

    public void setNsIsSDFProject(Boolean nsIsSDFProject) {
        if(nsIsSDFProject == null){
            nsIsSDFProject = false;
        }
        propertiesComponent.setValue(PROJECT_SETTING_NETSUITE_IS_SDF, nsIsSDFProject);
    }

    public void saveProjectPassword(NSClient client) {
        if (client != null) {
            CredentialAttributes attributes = new CredentialAttributes(client.getNSAccount().getAccountName() + ":" + client.getNSAccount().getAccountId(), client.getNSAccount().getAccountEmail(), this.getClass(), false);
            Credentials saveCredentials = new Credentials(attributes.getUserName(), client.getNSAccount().getAccountPassword());
            PasswordSafe.getInstance().set(attributes, saveCredentials);
        }
    }

    public String getProjectPassword() {
        CredentialAttributes attributes = new CredentialAttributes(this.getNsAccountName() + ":" + this.getNsAccount(), this.getNsEmail(), this.getClass(), false);
        return PasswordSafe.getInstance().getPassword(attributes);
    }

    public boolean hasAllProjectSettings() {
        return (getNsEmail()       != null && !getNsEmail().isEmpty()      &&
                getNsAccount()     != null && !getNsAccount().isEmpty()    &&
                getNsEnvironment() != null && !getNsEnvironment().isEmpty()) &&
                getNsIsSDFProject() != null;
    }

    public NSAccount getNSAccountForProject() {
        NSRolesRestServiceController nsRolesRestServiceController = new NSRolesRestServiceController();

        ArrayList<NSAccount> nsAccounts = nsRolesRestServiceController.getNSAccounts(getNsEmail(), getProjectPassword(), getNsEnvironment());

        NSAccount projectNSAccount = null;

        if (nsAccounts != null) {
            for (NSAccount account : nsAccounts) {
                if (account.getAccountName().equals(getNsAccountName()) &&
                        account.getAccountId().equals(getNsAccount()) &&
                        account.getRoleId().equals(getNsAccountRole())) {
                    projectNSAccount = account;
                    break;
                }
            }
        }

        return projectNSAccount;
    }
}
