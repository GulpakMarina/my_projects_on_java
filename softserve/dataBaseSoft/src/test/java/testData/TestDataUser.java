package testData;

import tabels.User;

import java.util.List;

public class TestDataUser {
    private List<User> userBeforeSuite;
    private List<User> addPositiveTests;
    private List<User> updatePositiveTests;
    private List<User> negativeAddUser;
    private List<User> negativeAddUserNull;




    public List<User> getNegativeAddUserNull() {
        return negativeAddUserNull;
    }

    public void setNegativeAddUserNull(List<User> negativeAddUserNull) {
        this.negativeAddUserNull = negativeAddUserNull;
    }

    public List<User> getNegativeAddUser() {
        return negativeAddUser;
    }

    public void setNegativeAddUser(List<User> negativeAddUser) {
        this.negativeAddUser = negativeAddUser;
    }

    public List<User> getUserBeforeSuite() {
        return userBeforeSuite;
    }

    public void setUserBeforeSuite(List<User> userBeforeSuite) {
        this.userBeforeSuite = userBeforeSuite;
    }

    public List<User> getAddPositiveTests() {
        return addPositiveTests;
    }

    public void setAddPositiveTests(List<User> addPositiveTests) {
        this.addPositiveTests = addPositiveTests;
    }

    public List<User> getUpdatePositiveTests() {
        return updatePositiveTests;
    }

    public void setUpdatePositiveTests(List<User> updatePositiveTests) {
        this.updatePositiveTests = updatePositiveTests;
    }

}
