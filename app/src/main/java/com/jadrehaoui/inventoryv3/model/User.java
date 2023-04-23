package com.jadrehaoui.inventoryv3.model;

public class User {
    private long id;
    private String fullName;
    private String username;
    private String passwordHash;
    private String privilege;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getPrivilege() {
        return privilege;
    }

    public void setPrivilege(int privilege) {
        switch (privilege) {
            case 4:
                this.privilege = "CUSTOMER";
                break;
            case 3:
                this.privilege = "SALES";
                break;
            case 2:
                this.privilege = "MANAGER";
                break;
            case 1:
                this.privilege = "ADMIN";
                break;
        }

    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", username='" + username + '\'' +
                ", passwordHash='" + passwordHash + '\'' +
                ", privilege='" + privilege + '\'' +
                '}';
    }
}
