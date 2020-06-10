package kr.or.ddit.basic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

/*
 * SELECT 예제
 */
public class T02_JdbcTest {
	/*
	 * 문제1) 사용자로부터 lprod_id값을 입력받아 입력한 값보다 lprod_id가 큰 자료들을 출력하세요
	 * 
	 * 문제2) lprod_id값을 2개 입력받아서 두 값 중 작은 값부터 큰값 사이의 자료를 출력하시오.
	 */
	
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		System.out.println("lprod_id : ");
		String input_lprod_id = sc.nextLine();
		System.out.println("lprod_id2 : ");
		String input_lprod_id2 = sc.nextLine();
		sc.close();
		
		// DB작업에 필요한 객체변수 선언
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null; // 쿼리문이 SELECT인 경우에 필요
				
		try {
			// 1. 드라이버 로딩
			Class.forName("oracle.jdbc.driver.OracleDriver");
					
			// 2. DB에 접속 (Connection객체 생성)
			String url = "jdbc:oracle:thin:@localhost:1521/xe";
			String userId = "osong";
			String password = "java";
					
			conn = DriverManager.getConnection(url, userId, password);
			
			// 3. Statement 객체 생성 => Connection객체를 이용한다.
			stmt = conn.createStatement();
			
			// 4. SQL문을 Statement객체를 이용하여 실행하고 실행결과를 ResultSet에 저장한다.
			// String sql = "SELECT * FROM lprod WHERE lprod_id > " + input_lprod_id; // 실행할 SQL문, 문제1
			String sql = "";
			if( Integer.parseInt(input_lprod_id) < Integer.parseInt(input_lprod_id2)) {
				sql = "SELECT * FROM lprod WHERE lprod_id > " + input_lprod_id + "AND lprod_id <" + input_lprod_id2; // 실행할 SQL문, 문제2
			} else {
				sql = "SELECT * FROM lprod WHERE lprod_id > " + input_lprod_id2 + "AND lprod_id <" + input_lprod_id; // 실행할 SQL문, 문제2
			}
			// SQL문이 SELECT일 경우에는 excuteQuery()메서드 이용하고,
			// INSERT, UPDATE, DELETE일 경우에는 executeUpdate()메서드 이용한다.
			rs = stmt.executeQuery(sql); 
			
			// 5. ResultSet객체에 저장되어 있는 자료를 반복문과 next()메서드를 이용하여 차례로 읽어와 처리한다.
			System.out.println("실행한 쿼리문 : " + sql);
			System.out.println("=== 쿼리문 실행결과 ===");
			
			// rs.next() => ResultSet객체의 데이터를 가리키는 포인터를 다음 레코드로
			// 				이동시키고, 그 곳에 자료가 있으면 true, 없으면 false를 리턴한다.
			while(rs.next()) {
				// 컬럼의 자료를 가져오는 방법
				// 방법1) rs.get자료형이름("컬럼명")
				// 방법2) rs.get자료형이름(컬럼번호) => 컬럼번호는 1부터 시작
				System.out.println("lprod_id : " + rs.getInt("lprod_id"));
				System.out.println("lprod_gu : " + rs.getString("lprod_gu"));
				System.out.println("lprod_nm : " + rs.getString("lprod_nm"));
				System.out.println("-------------------------------------");
				}
			System.out.println("출력 끝...");
			}catch(SQLException e){
				e.printStackTrace();
			}catch(ClassNotFoundException e) {
				e.printStackTrace();
			}finally {
				// 6. 종료(사용했던 자원을 모두 반납한다.)
				if(rs != null) try {rs.close();}catch(SQLException e) {}
				if(stmt != null) try {stmt.close();}catch(SQLException e) {}
				if(conn != null) try {conn.close();}catch(SQLException e) {}
			}	
	}
}
