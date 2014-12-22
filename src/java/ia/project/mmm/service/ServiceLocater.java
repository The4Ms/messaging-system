package ia.project.mmm.service;

/**
 *
 * @author Mohamed Kamal
 */
public class ServiceLocater {
    private static IUserService userService = new UserService();
    
    public static IMessageService getIMessageService(){
        return null;
    }
    
    public static IUserService getUserService(){
        return userService;
    }
}
