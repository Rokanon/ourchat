/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.ourchat.profile.logic;

/**
 *
 * @author dark
 */
public interface Sessionable {

    boolean login(String mail, String password);

    void logout();
    
    boolean validate(String realPassword, String loginPassword);
}
