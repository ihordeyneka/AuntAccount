package dido.auntaccount.dto;

public class PasswordDTO {

    private String newPassword;
    private String oldPassword;
    private String recoveryToken;

    public PasswordDTO() {

    }

    public PasswordDTO(String newPassword, String oldPassword, String recoveryToken) {
        this.newPassword = newPassword;
        this.oldPassword = oldPassword;
        this.recoveryToken = recoveryToken;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getRecoveryToken() {
        return recoveryToken;
    }

    public void setRecoveryToken(String recoveryToken) {
        this.recoveryToken = recoveryToken;
    }
}
