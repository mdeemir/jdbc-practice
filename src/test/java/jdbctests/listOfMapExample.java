package jdbctests;

import org.junit.jupiter.api.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class listOfMapExample {
    String dbUrl = "jdbc:oracle:thin:@44.195.19.167:1521:XE";
    String dbUsername = "hr";
    String dbPassword = "hr";

    @Test
    public void test1(){

        //creating list for keeping all the rows maps
        //OBJECT YAZDIK ÇÜNKÜ INT STRİNG HEPSİNİ KAPSIYOR
        List<Map<String,Object>> queryData = new ArrayList<>();

        Map<String,Object> row1 = new HashMap<>();

        row1.put("first_name","Steven");
        row1.put("last_name","King");
        row1.put("salary",24000);
        row1.put("job_id","AD_PRES");

        System.out.println(row1.toString());//{job_id=AD_PRES, last_name=King, salary=24000, first_name=Steven}

        Map<String,Object> row2 = new HashMap<>();

        row2.put("first_name","Neena");
        row2.put("last_name","Kochhar");
        row2.put("salary",17000);
        row2.put("job_id","AD_VP");

        System.out.println(row2.toString());//{job_id=AD_VP, last_name=Kochhar, salary=17000, first_name=Neena}

        //adding rows one by one to my list
        queryData.add(row1);
        queryData.add(row2);

        //get the steven lastname directly from the list
        System.out.println(queryData.get(0).get("last_name"));//King



    }

    @Test
    public void test2() throws SQLException {

        Connection connection = DriverManager.getConnection(dbUrl,dbUsername,dbPassword);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT first_name,last_name,salary,job_id\n" +
                "from employees\n" +
                "where rownum <6");

        //in order to get column names we need resultsetmetadata
        ResultSetMetaData rsmd = resultSet.getMetaData();

        //move to first row
        resultSet.next();

        //creating list for keeping all the rows maps
        List<Map<String,Object>> queryData = new ArrayList<>();

        Map<String,Object> row1 = new HashMap<>();
//rsmd.getColumnName(1)--->first_name  resultSet.getString(1)--> Steven
        row1.put(rsmd.getColumnName(1),resultSet.getString(1));
        row1.put(rsmd.getColumnName(2),resultSet.getString(2));
        row1.put(rsmd.getColumnName(3),resultSet.getString(3));
        row1.put(rsmd.getColumnName(4),resultSet.getString(4));

        System.out.println(row1.toString()); //{JOB_ID=AD_PRES, SALARY=24000, LAST_NAME=King, FIRST_NAME=Steven}

        //move to second row
        resultSet.next();

        Map<String,Object> row2 = new HashMap<>();

        row2.put(rsmd.getColumnName(1),resultSet.getString(1));
        row2.put(rsmd.getColumnName(2),resultSet.getString(2));
        row2.put(rsmd.getColumnName(3),resultSet.getString(3));
        row2.put(rsmd.getColumnName(4),resultSet.getString(4));

        System.out.println(row2.toString());//{JOB_ID=AD_VP, SALARY=17000, LAST_NAME=Kochhar, FIRST_NAME=Neena}


        //adding rows one by one to my list
        queryData.add(row1);
        queryData.add(row2);

        //close connection
        resultSet.close();
        statement.close();
        connection.close();
    }
}
