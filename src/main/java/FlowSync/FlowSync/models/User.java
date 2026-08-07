package FlowSync.FlowSync.models;

import java.time.LocalDateTime;

public class User {

    private Long id;
    private String userCode;
    private String name;
    private String username;
    private String email;
    private String password;
    private LocalDateTime createdDt;
    private LocalDateTime updatedDt;
    private LocalDateTime deletedDt;
    private Integer ruleId;
    private String grpId;

    public User() {
    }

    public User(Long id, String userCode, String name, String username,
                String email, String password,
                LocalDateTime createdDt, LocalDateTime updatedDt,
                LocalDateTime deletedDt, Integer ruleId, String grpId) {
        this.id = id;
        this.userCode = userCode;
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
        this.createdDt = createdDt;
        this.updatedDt = updatedDt;
        this.deletedDt = deletedDt;
        this.ruleId = ruleId;
        this.grpId = grpId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getCreatedDt() {
        return createdDt;
    }

    public void setCreatedDt(LocalDateTime createdDt) {
        this.createdDt = createdDt;
    }

    public LocalDateTime getUpdatedDt() {
        return updatedDt;
    }

    public void setUpdatedDt(LocalDateTime updatedDt) {
        this.updatedDt = updatedDt;
    }

    public LocalDateTime getDeletedDt() {
        return deletedDt;
    }

    public void setDeletedDt(LocalDateTime deletedDt) {
        this.deletedDt = deletedDt;
    }

    public Integer getRuleId() {
        return ruleId;
    }

    public void setRuleId(Integer ruleId) {
        this.ruleId = ruleId;
    }

    public String getGrpId() {
        return grpId;
    }

    public void setGrpId(String grpId) {
        this.grpId = grpId;
    }
}