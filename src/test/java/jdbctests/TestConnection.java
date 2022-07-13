package jdbctests;

import java.sql.*;

public class TestConnection {

    public static void main(String[] args) throws SQLException {
        String dbUrl = "jdbc:oracle:thin:@52.70.21.156:1521:XE";// buradaki ip adresi ec2 deki sanal bilgisaarım ip si
        String dbUsername = "hr";
        String dbPassword = "hr";
//3 TANE İNTERFACE KULLANIYORUZ
       //1- DATABASE İLE JAVA PROJEMİZİN BAĞLANTISINI SAĞLAMAK İÇİN.
        Connection connection = DriverManager.getConnection(dbUrl,dbUsername,dbPassword);// SQLException ÇIKIYOR GETcONNECTİONA YAZINCA
        //2-SQL QUERY (SORGULARI) LERİNİ YAZMAK VE YÜRÜTMEK İÇİN BİZE YARDIM EDER
        Statement statement = connection.createStatement();
        //3- DATA YAPISIDIR. DATABASEDEN GELEN DATALARI DEPOLAMAK İÇİN KULLANIRIZ
        ResultSet resultSet = statement.executeQuery("SELECT * FROM regions");

   /*     //once you set up connection default pointer looks for 0
        //next() --> move pointer to first row
        resultSet.next();
//2 YOLU VAR
        //1-getting information with column name
        System.out.println(resultSet.getString("region_name"));
                                    //GETSTRİNG KULLANDIK ÇÜNKÜ STRİNG BİR DATA İSTİYORUZ TABLODAN
        //2-getting information with column index(starts 1)
        System.out.println(resultSet.getString(2));

        // 1 - Europe
        // 2 - Americas
        System.out.println(resultSet.getInt(1)+ " - " +resultSet.getString(2) );

        //move to second row
        resultSet.next();
        System.out.println(resultSet.getInt(1)+ " - " +resultSet.getString(2) );
        //move to third row
        resultSet.next();
        System.out.println(resultSet.getInt(1)+ " - " +resultSet.getString(2) );
//(resultSet.next()  METODU BOOLEAN DÖNDÜRÜR. EĞER SONRASINDA SIRA VARSA TRUE YOKSA FALSE DÖNDÜRÜR */
//YUKARIDA AYRI AYRI YAPTIĞIMIZ İŞLEMİ WHİLE DÖNGÜSÜ İLE YAPTIK. NEXT() METOD BOOLEAN DEĞER DÖNDÜRÜR
        while (resultSet.next()){

            System.out.println(resultSet.getInt(1)+ " - " +resultSet.getString(2) );

        }


        //close connection
        resultSet.close();
        statement.close();
        connection.close();


    }
}
