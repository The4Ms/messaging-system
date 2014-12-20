/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ia.project.mmm.service;

/**
 *
 * @author Mohamed Kamal
 */
public class UsernameNotFound extends Exception {

    /**
     * Creates a new instance of <code>UsernameNotFound</code> without detail
     * message.
     */
    public UsernameNotFound() {
    }

    /**
     * Constructs an instance of <code>UsernameNotFound</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public UsernameNotFound(String msg) {
        super(msg);
    }
}
