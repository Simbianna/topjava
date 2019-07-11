package ru.javawebinar.topjava;

public class Profiles {

    public static final String
            JDBC = "jdbc",
            JPA = "jpa",
            DATAJPA = "datajpa";

    public static final String REPOSITORY_IMPLEMENTATION = DATAJPA;

    public static final String
            POSTGRES_DB = "postgres",
            HSQL_DB = "hsqldb";

    //  Get DB profile depending of DB driver in classpath
    public static String getActiveDbProfile() {
        try {
            Class.forName("org.postgresql.Driver");
            return POSTGRES_DB;
        } catch (ClassNotFoundException ex) {
            try {
                Class.forName("org.hsqldb.jdbcDriver");
                return Profiles.HSQL_DB;
            } catch (ClassNotFoundException e) {
                throw new IllegalStateException("Could not find DB driver");
            }
        }
    }

    public static String[] getActiveSpringProfile() {
        String[] result = new String[2];
        result[0] = getActiveDbProfile();
        try {
            Class.forName("ru.javawebinar.topjava.repository.datajpa.DataJpaMealRepository");
            result[1] = DATAJPA;
        } catch (ClassNotFoundException ex) {
        }
        try {
            Class.forName("ru.javawebinar.topjava.repository.jdbc.JdbcMealRepository");
            result[1] = JDBC;
        } catch (ClassNotFoundException ex) {
        }
        try {
            Class.forName("ru.javawebinar.topjava.repository.jpa.JpaMealRepository");
            result[1] = JPA;
        } catch (ClassNotFoundException ex) {
            throw new IllegalStateException("Could not find Spring driver");
        }

        return result;
    }
}
