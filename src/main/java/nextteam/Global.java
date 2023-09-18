/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nextteam;

import nextteam.utils.database.AuthenticationDAO;

/**
 *
 * @author vnitd
 */
public class Global {

    public static String server = "localhost";
    public static String database = "NextTeam";
    public static String user = "sa";
    public static String pass = "12345678";

    public static AuthenticationDAO authentication = new AuthenticationDAO(server, database, user, pass);

    public static void setDAOConnection() {
        authentication.setConnection();
    }

    public static void closeDAOConnection() {
        authentication.close();
    }
}
