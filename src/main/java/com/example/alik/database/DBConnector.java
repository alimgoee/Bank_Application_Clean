package com.example.alik.database;

import com.example.alik.entities.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class DBConnector {
    private static Connection connection;

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/alimzhan?useUnicode=true&serverTimezone=UTC",
                    "root",
                    ""
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean addNewUser(User user) {
        boolean result = false;
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO users (login, name, password, totalBalance, email) VALUES (?, ?, ?, ?, ?)"
            );
            statement.setInt(1, user.getLogin());  // Если login — строка, иначе setInt()
            statement.setString(2, user.getName());
            statement.setString(3, user.getPassword());
            statement.setInt(4, user.getTotalBalance());
            statement.setString(5, user.getEmail());

            int rows = statement.executeUpdate();
            result = rows > 0; // Проверяем, добавилась ли запись

            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static boolean ondaiAdamBarMA(User user) {
        boolean result = false;
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM users WHERE email = ? or login = ?"
            );
            statement.setString(1, user.getEmail());
            statement.setInt(2, user.getLogin());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                result = true;
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static User getCurrentUser(String email, String password) {
        User user = null;

        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM users WHERE email = ? AND password = ? LIMIT 1"
            );
            statement.setString(1, email);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = new User(); // Создаем объект перед использованием
                user.setId(resultSet.getInt("id"));
                user.setLogin(resultSet.getInt("login")); // Если login — строка
                user.setName(resultSet.getString("name"));
                user.setPassword(resultSet.getString("password"));
                user.setTotalBalance(resultSet.getInt("totalBalance"));
                user.setEmail(resultSet.getString("email"));
            }

            resultSet.close();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return user;
    }

    public static ArrayList<User> getAllUsers(User userbek) {
        ArrayList<User> users = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM users WHERE id != ?"
            );
            statement.setInt(1, userbek.getId());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setLogin(resultSet.getInt("login"));
                user.setName(resultSet.getString("name"));
                user.setPassword(resultSet.getString("password"));
                user.setTotalBalance(resultSet.getInt("totalBalance"));
                user.setEmail(resultSet.getString("email"));

                users.add(user);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }



    public static boolean perevodJasa(User currentUser, User user_to, Integer summa) {
        boolean result = false;
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO transfers(id, user_from, user_to, transferBalance, transferDate) " +
                            "VALUES (NULL, ?, ?, ?, now())"
            );
            statement.setInt(1, currentUser.getId());
            statement.setInt(2, user_to.getId());
            statement.setInt(3, summa);
            int rows = statement.executeUpdate();
            result = rows > 0;
            statement.close();

            if (result == true){
                PreparedStatement statement2 = connection.prepareStatement(
                        "UPDATE users SET totalBalance = ? WHERE id = ?"
                );
                statement2.setInt(1, currentUser.getTotalBalance() - summa);
                statement2.setInt(2, currentUser.getId());
                statement2.executeUpdate();
                statement2.close();

                PreparedStatement statement3 = connection.prepareStatement(
                        "UPDATE users SET totalBalance = ? WHERE id = ?"
                );
                statement3.setInt(1, user_to.getTotalBalance() + summa);
                statement3.setInt(2, user_to.getId());
                statement3.executeUpdate();
                statement3.close();
            }

        }catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static User getUserByEmail(String email) {
        User user = null;
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM users WHERE email = ?"
            );
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = new User();
                user.setId(resultSet.getInt("id"));
                user.setLogin(resultSet.getInt("login"));
                user.setName(resultSet.getString("name"));
                user.setPassword(resultSet.getString("password"));
                user.setTotalBalance(resultSet.getInt("totalBalance"));
                user.setEmail(resultSet.getString("email"));
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }
}
