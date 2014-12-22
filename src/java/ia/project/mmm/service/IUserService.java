package ia.project.mmm.service;

/**
 *
 * @author Mohamed Kamal
 */
public interface IUserService {
    boolean isValidUser(String username, String password);
    boolean addNewUser(String username, String password, String fullname);
}
