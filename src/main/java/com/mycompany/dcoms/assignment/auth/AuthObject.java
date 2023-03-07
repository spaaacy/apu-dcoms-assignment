/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.dcoms.assignment.auth;

import static com.mycompany.dcoms.assignment.auth.NonUniqueDetailsExeception.SQL_PRIMARY_KEY_ERROR_CODE;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import static com.mycompany.dcoms.assignment.Server.SOCKET_PORT;

/**
 *
 * @author aakif
 */
public class AuthObject extends UnicastRemoteObject implements AuthInterface {

    static final String DB_URL = "jdbc:derby://localhost:1527/KGF";
    static final String DB_USERNAME = "kgf";
    static final String DB_PASSWORD = "kgf";

    static final String USER_TABLE_NAME = "TBLUSER";
    static final String USERNAME_COLUMN_NAME = "username";
    static final String PASSWORD_COLUMN_NAME = "password";

    public AuthObject() throws RemoteException {
        super();
    }

    /**
     * Returns true if registration successful, and false if username/ic already
     * exists
     */
    @Override
    public void register() throws RemoteException, NonUniqueDetailsExeception {

        Runnable register = new Runnable() {
            @Override
            public void run() {
            boolean success = false;
                ServerSocket ss = null;
                Socket socket = null;

                try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);) {
                    ss = new ServerSocket(SOCKET_PORT);
                    socket = ss.accept();
                    ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                    User user = (User) ois.readObject();

                    PreparedStatement statement = conn.prepareStatement("INSERT INTO " + USER_TABLE_NAME + " VALUES (?, ?, ?, ?, ?)");
                    statement.setString(1, user.getUsername());
                    statement.setString(2, user.getPassword());
                    statement.setString(3, user.getFirstName());
                    statement.setString(4, user.getLastName());
                    statement.setString(5, user.getIcNumber());
                    statement.executeUpdate();

                    success = true;
                } catch (SQLException ex) {
                    // SQLState 23505 represents instance where primary key pre-exists in table
                    System.out.println(ex.getSQLState());
                    if (ex.getSQLState().equals(SQL_PRIMARY_KEY_ERROR_CODE)) {
//                        throw new NonUniqueDetailsExeception(ex); // TODO: Change this
                    }
                } catch (IOException ex) {
                    System.out.println("IOException");
                } catch (ClassNotFoundException ex) {
                    System.out.println("ClassNotFoundException");
                } finally {

                    try {
                        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                        dos.writeBoolean(success);
                        dos.flush();
                        dos.close();
                        ss.close();
                    } catch (IOException ex) {
                        System.out.println("IOException");
                    }
                }
            }
        };
        
        new Thread(register).start();

    }

    /**
     * Returns true if login successful, and false if credentials are invalid
     */
    @Override
    public void login() {

        Runnable login = new Runnable() {

            @Override
            public void run() {
                boolean success = false;
                ServerSocket ss = null;
                Socket socket = null;

                try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);) {

                    ss = new ServerSocket(SOCKET_PORT);
                    socket = ss.accept();
                    DataInputStream dis = new DataInputStream(socket.getInputStream());
                    String username = (String) dis.readUTF();
                    String password = (String) dis.readUTF();

                    PreparedStatement statement = conn.prepareStatement("SELECT * FROM " + USER_TABLE_NAME + " WHERE "
                            + USERNAME_COLUMN_NAME + " = ? AND " + PASSWORD_COLUMN_NAME + " = ?");
                    statement.setString(1, username);
                    statement.setString(2, password);
                    ResultSet results = statement.executeQuery();

                    if (results.next()) {
                        success = true;
                    }

                } catch (SQLException ex) {
                    System.out.println("SQLException: " + ex.getSQLState());
                } catch (IOException ex) {
                    System.out.println("IOException");
                } finally {

                    try {
                        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                        dos.writeBoolean(success);
                        dos.flush();
                        dos.close();
                        ss.close();
                    } catch (IOException ex) {
                        System.out.println("IOException");
                    }

                }
            }
        };
        new Thread(login).start();
    }
}
