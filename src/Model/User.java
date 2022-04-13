package Model;

public abstract class User {
    private int userId;
    private String fullName;
    private String username;
    private String password;
    private String type;
    private String department;

    public User(int userId, String fullName, String username, String password, String type, String department) {
        this.userId = userId;
        this.fullName = fullName;
        this.username = username;
        this.password = password;
        this.type = type;
        this.department = department;
    }

    @Override
    public String toString() {
        return this.getFullName();
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}
