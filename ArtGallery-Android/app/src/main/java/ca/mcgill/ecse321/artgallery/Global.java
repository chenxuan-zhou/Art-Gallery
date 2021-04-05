package ca.mcgill.ecse321.artgallery;

import android.app.Application;

/**
 * A class to store global variables : customer emails, customer password and customer name.
 * These variables will persist throughout the app session.
 * @author Tracy
 */
public class Global extends Application {

    public String email = "";
    public String password = "";
    public String name = "";

    /**
     * Set email
     * @param email An email to be saved as session email.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Set password
     * @param password An password to be saved as session password.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Set name
     * @param name An name to be saved as session name.
     */
    public void setName(String name) {
        this.name = name;
    }
}
