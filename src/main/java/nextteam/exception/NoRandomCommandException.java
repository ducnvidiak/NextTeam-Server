/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nextteam.exception;

/**
 *
 * @author vnitd
 */
public class NoRandomCommandException extends RuntimeException {

    public NoRandomCommandException() {
        super("There is no command random. Try using simpleRandString(length) function.");
    }

}
