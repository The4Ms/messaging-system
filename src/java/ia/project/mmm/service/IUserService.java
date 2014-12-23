package ia.project.mmm.service;

import ia.project.mmm.model.UserInfo;

/**
 *
 * @author Mohamed Kamal
 */
public interface IUserService {
    boolean isValidUser(String username, String password);
    boolean addNewUser(String username, String password, String fullname);
    void editUser(String username, String newFullname, String newPassword);
    UserInfo getUserById(int userId);
}
