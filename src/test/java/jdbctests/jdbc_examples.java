package jdbctests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.*;

public class jdbc_examples {

    String dbUrl = "jdbc:oracle:thin:@52.70.21.156:1521:XE";
    String dbUsername = "hr";
    String dbPassword = "hr";
// MAİN METODU OLMADAN YÜRÜTMEK İÇİN TESTLERİ @TEST KULLANIYORUZ. BUNUN İÇİN POM XML E JUNİT DEPENDENCİES EKLEDİK
    @Test
    public void test1() throws SQLException {

        Connection connection = DriverManager.getConnection(dbUrl,dbUsername,dbPassword);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM departments");

//        //move to first row
//        resultSet.next();
//
//        System.out.println(resultSet.getString(2));
//        //display departments table in 10 - Administration - 200 - 1700 format


        //code for iterating for each row
        while(resultSet.next()){

            System.out.println(resultSet.getInt(1)+" - "+resultSet.getString(2)
            +" - "+resultSet.getString(3)+" - "+resultSet.getInt(4));
        }
        //==============================

        resultSet = statement.executeQuery("SELECT * FROM regions");
        while(resultSet.next()) {

            System.out.println(resultSet.getInt(1) + " - " + resultSet.getString(2));

        }
        //BREAK UNTIL 11:16
        //close connection
        resultSet.close();
        statement.close();
        connection.close();
    }

    @DisplayName("ResultSet Methods")
    @Test
    public void test2() throws SQLException {
        Connection connection = DriverManager.getConnection(dbUrl,dbUsername,dbPassword);
        Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
        //ResultSet.TYPE_SCROLL_INSENSITIVE  BU POİNTER IN AŞAĞI YUKARI SERBESTÇE HAREKET ETMESİNİ SAĞLIYOR LAST METODU FİLAN KULLANAİBLMEK İÇİN
        //ResultSet.CONCUR_READ_ONLY   BU DA DATABASE ÜZERİNDE SADECE OKUMA YAPMAMIZI SAĞLIYOR. ÇÜNKÜ BİZİM DEĞİŞTİRME İLE İŞİMİZ YOK. DEVELOPERLAR DEĞİŞTİRİR VS.
        ResultSet resultSet = statement.executeQuery("SELECT * FROM departments");

        //how to find how many rows we have for the query
        //move to last row
        //test
        resultSet.last();

        //get the row count
        int rowCount = resultSet.getRow();
        System.out.println(rowCount);
//POİNTER LAST METODTAN SONRA EN SONDA KALDI TEKRAR BAŞA DÖNDÜRMEMİZ LAZIM
        //to move before first row after we use .last method
        resultSet.beforeFirst();

        //print all second column information
        while (resultSet.next()){
            System.out.println(resultSet.getString(2));
        }


        //close connection
        resultSet.close();
        statement.close();
        connection.close();
    }


    @Test
    public void test3() throws SQLException {
        Connection connection = DriverManager.getConnection(dbUrl,dbUsername,dbPassword);
        Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
        ResultSet resultSet = statement.executeQuery("SELECT * FROM departments");

        //get the database related data inside the dbMetedata object
        DatabaseMetaData dbMetadata = connection.getMetaData();
// DATABASE HAKKINDAKİ GENEL BİLGİLERE ULAŞMAK İÇİN KULLANIYORUZ. DatabaseMetaData İNTERFACEDEN GELİYOR
        System.out.println("dbMetadata.getUserName() = " + dbMetadata.getUserName());//dbMetadata.getUserName() = HR
        System.out.println("dbMetadata.getDatabaseProductName() = " + dbMetadata.getDatabaseProductName());//dbMetadata.getDatabaseProductName() = Oracle
        System.out.println("dbMetadata.getDatabaseProductVersion() = " + dbMetadata.getDatabaseProductVersion());//dbMetadata.getDatabaseProductVersion() = Oracle Database 11g Express Edition Release 11.2.0.2.0 - 64bit Production
        System.out.println("dbMetadata.getDriverName() = " + dbMetadata.getDriverName());//dbMetadata.getDriverName() = Oracle JDBC driver
        System.out.println("dbMetadata.getDriverVersion() = " + dbMetadata.getDriverVersion());//dbMetadata.getDriverVersion() = 19.3.0.0.0

        //get the resultsetmetadata //rsmd--> KISALTMASI BİYERDE GÖRÜRSEN ŞAŞORMA
        ResultSetMetaData rsMetadata = resultSet.getMetaData();

        //how many columns we have ?
        int colCount = rsMetadata.getColumnCount();
        System.out.println(colCount); //4

        //getting column names
        System.out.println(rsMetadata.getColumnName(1));//DEPARTMENT_ID
        System.out.println(rsMetadata.getColumnName(2));//DEPARTMENT_NAME

        //rsMetadata.getColumnName(i) --> gets column name
        //rsMetadata.getColumnCount() --> total number of columns
        //print all the column names dynamically
        for (int i = 1; i <= colCount; i++) {

            System.out.println(rsMetadata.getColumnName(i));
       /*
        DEPARTMENT_ID
        DEPARTMENT_NAME
        MANAGER_ID
        LOCATION_ID
        */
        }



        //close connection
        resultSet.close();
        statement.close();
        connection.close();
    }

}
