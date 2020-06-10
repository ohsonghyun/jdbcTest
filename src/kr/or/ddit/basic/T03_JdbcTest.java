package kr.or.ddit.basic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * insert 예제
 */
public class T03_JdbcTest {
   /*
    * lprod_id : 101, lprod_gu : N101, lprod_nm : 농산물
    * lprod_id : 102, lprod_gu : N102, lprod_nm : 수산물
    * lprod_id : 103, lprod_gu : N103, lprod_nm : 축산물
    * 
    * 위 3개의 자료를 추가하기
    */
   public static void main(String[] args) {
      Connection conn = null;
      Statement stmt = null;
      PreparedStatement pstmt = null;
      
      try {
         // 1. 드라이버 로딩
         Class.forName("oracle.jdbc.driver.OracleDriver");
         
         // 2. DB에 접속(Connection객체 생성)
         String ur1 = "jdbc:oracle:thin:@localhost:1521/xe";
         String userId = "osong";
         String password = "java";
         
         conn = DriverManager.getConnection(ur1, userId, password);
         
         // 3. Statement객체 생성 => Connection객체를 이용한다.
//         stmt = conn.createStatement();
//         
//         String sql = " insert into lprod " + " (lprod_id, lprod_gu, lprod_nm) " + " values(101, 'N101', '농산물') ";
//         int cnt = stmt.executeUpdate(sql);
//         System.out.println("첫번째 반환값 : " + cnt);
//         
//         sql = " insert into lprod " + " (lprod_id, lprod_gu, lprod_nm) " + " values(102, 'N102', '수산물') ";
//         cnt = stmt.executeUpdate(sql);
//         System.out.println("두번째 반환값 : " + cnt);
//         
//         sql = " insert into lprod " + " (lprod_id, lprod_gu, lprod_nm) " + " values(103, 'N103', '축산물') ";
//         cnt = stmt.executeUpdate(sql);
//         System.out.println("세번째 반환값 : " + cnt);
         
         // 3. PreparedStatement객체 생성 => Connection객체를 이용한다.
         String sql = " insert into lprod " + " (lprod_id, lprod_gu, lprod_nm) " + " values(?, ?, ?) ";
         // PreparedStatement객체를 생성할 때 SQL문을 넣어서 생성한다.
         pstmt = conn.prepareStatement(sql);
         
         // 쿼리문의 물음표(?)자리에 들어가 데이터를 셋팅한다.
         // 형식) pstmt.set자료형이름(물음표순번, 데이터);
         //      물음표 순번은 1번부터 시작한다.
         pstmt.setInt(1, 101);
         pstmt.setString(2, "N101");
         pstmt.setString(3, "농산물");
         
         // 데이터를 세팅한 후 쿼리문을 실행한다.
         int cnt = pstmt.executeUpdate();
         System.out.println("첫번째 반환값 : " + cnt);
         //-------------------------------------
         pstmt.setInt(1, 102);
         pstmt.setString(2, "N102");
         pstmt.setString(3, "수산물");
         
         cnt = pstmt.executeUpdate();
         System.out.println("두번째 반환값 : " + cnt);
         //-------------------------------------
         pstmt.setInt(1, 103);
         pstmt.setString(2, "N103");
         pstmt.setString(3, "축산물");
         
         cnt = pstmt.executeUpdate();
         System.out.println("세번째 반환값 : " + cnt);
         
      }catch(ClassNotFoundException e) {
         System.out.println("드라이버 로딩 실패!!!");
         e.printStackTrace();
      }catch(SQLException e) {
         e.printStackTrace();
      }finally {
         // 6. 종료 (사용ㄷ했던 자원을 모두 반납한다.)
         if(stmt != null) try {stmt.close();}catch(SQLException e) {}
         if(conn != null) try {conn.close();}catch(SQLException e) {}
      }
   }
}