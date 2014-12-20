package ia.project.mmm.service;

/**
 *
 * @author Mohamed Kamal
 */
public class UsernameAlreadyExists extends Exception {

    /**
     * Creates a new instance of <code>UsernameAlreadyExists</code> without
     * detail message.
     */
    public UsernameAlreadyExists() {
    }

    /**
     * Constructs an instance of <code>UsernameAlreadyExists</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public UsernameAlreadyExists(String msg) {
        super(msg);
    }
}
