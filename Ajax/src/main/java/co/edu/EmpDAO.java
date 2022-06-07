package co.edu;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmpDAO extends DAO {
	// 스케줄 리스트
	public List<Schedule> scheduleList(){
		connect();
		System.out.println("리스트");
		List<Schedule> list = new ArrayList<Schedule>();
		String sql = "select * from schedules";
		try {
			psmt = conn.prepareStatement(sql);
			rs = psmt.executeQuery();
			while (rs.next()) {
				Schedule sch = new Schedule();
				sch.setTitle(rs.getString("title"));
				sch.setStartDate(rs.getString("start_date"));
				sch.setEndDate(rs.getString("end_date"));

				list.add(sch);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return list;
	}
	
	// 스케줄 등록
	public Schedule insertSchedule(Schedule sched) {
		System.out.println("등록");
		String sql = "insert into schedules(title, start_date, end_date)"
				+ "values(?, ?, ?)";
		
		connect();
		try {

			psmt = conn.prepareStatement(sql);
			psmt.setString(1, sched.getTitle());
			psmt.setString(2, sched.getStartDate());
			psmt.setString(3, sched.getEndDate());
			psmt.executeUpdate();


		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return sched;
	}
	
	// 스케줄 삭제
	public Schedule deleteSchedule(Schedule sched) {
		System.out.println("삭제");
		String sql = "delete from schedules where title=?";
		
		try {

			psmt = conn.prepareStatement(sql);
			psmt.setString(1, sched.getTitle());
			psmt.executeUpdate();


		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return sched;
	}
	
	// 부서별 인원(차트 만들기)  반환타입: 부서명 = 인원. Map<String, Integer>
	public Map<String, Integer> getMemberByDept(){
		Map<String, Integer> map = new HashMap<String, Integer>();
		connect();
		String sql = "select d.department_name, count(1) as cnt "
				+ "from employees e, departments d "
				+ "where e.department_id = d.department_id "
				+ "group by d.department_name";
		try {
			psmt = conn.prepareStatement(sql);
			rs = psmt.executeQuery();
			while(rs.next()) {	// key와 val 형식 
				map.put(rs.getString("department_name"), rs.getInt("cnt")); // 데이터 추가
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return map;
	}

	// 리스트 가져오기
	public List<Employee> empList() {
		connect();
		List<Employee> list = new ArrayList<Employee>();
		try {
			psmt = conn.prepareStatement("select * from emp order by 1");
			rs = psmt.executeQuery();
			while (rs.next()) {
				Employee emp = new Employee();
				emp.setEmployeeId(rs.getInt("employee_id"));
				emp.setFirstName(rs.getString("first_name"));
				emp.setLastName(rs.getString("last_name"));
				emp.setEmail(rs.getString("email"));
				emp.setJobId(rs.getString("job_id"));
				emp.setHireDate(rs.getString("hire_date").substring(0, 10));

				list.add(emp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return list;
	}

	// 입력하기
	public Employee insertEmp(Employee emp) {
		String sql = "insert into emp (employee_id, first_name, last_name, email, hire_date, job_id)"
				+ "values(?,?,?,?,?,?)";
		String seqSql = "select employees_seq.nextval from dual";

		connect();
		int nextSeq = -1;
		try {
			psmt = conn.prepareStatement(seqSql);
			rs = psmt.executeQuery();
			if (rs.next()) {
				nextSeq = rs.getInt(1);
			}

			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, nextSeq);
			psmt.setString(2, emp.getFirstName());
			psmt.setString(3, emp.getLastName());
			psmt.setString(4, emp.getEmail());
			psmt.setString(5, emp.getHireDate());
			psmt.setString(6, emp.getJobId());
			int r = psmt.executeUpdate();
			System.out.println(r + "건 입력완료");

			emp.setEmployeeId(nextSeq);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return emp;
	}

	// 수정하기
	public Employee updateEmp(Employee emp) {
		connect();
		String sql = "update emp set first_name=?, last_name=?, email=?, hire_date=?, job_id=? where employee_id=?";
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, emp.getFirstName());
			psmt.setString(2, emp.getLastName());
			psmt.setString(3, emp.getEmail());
			psmt.setString(4, emp.getHireDate());
			psmt.setString(5, emp.getJobId());
			psmt.setInt(6, emp.getEmployeeId());
			
			int r = psmt.executeUpdate();
			System.out.println(r + "건 수정");
			if(r > 0) {
				return emp;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
		disconnect();
		}
		return null;
	}
		
	
	// 삭제하기
	public Employee delEmp(Employee emp) {
		connect();
		String sql = "delete from emp where employee_id=?";
		
		try {

			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, emp.getEmployeeId());
			psmt.executeUpdate();


		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return emp;
	}
	
	
	
	// 한건조회
	
}
