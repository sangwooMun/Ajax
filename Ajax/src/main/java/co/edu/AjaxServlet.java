package co.edu;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@WebServlet({ "/AjaxServlet", "/ajax.do" })
public class AjaxServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AjaxServlet() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		
		String job = request.getParameter("job");
		PrintWriter out = response.getWriter(); //출력스트림
		
		if(job.equals("html")) {
			
			out.print("<h3>html페이지입니다</h3>");
			out.print("<a href= 'index.html'>첫페이지로</a>");
			
		}
		else if(job.equals("json")) {
			
//			String json = "[";
			EmpDAO dao = new EmpDAO();
			List<Employee> list = dao.empList();
			
			Gson gson = new GsonBuilder().create();
			out.print(gson.toJson(list));
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
					request.setCharacterEncoding("utf-8");
					
					response.setCharacterEncoding("utf-8");
					response.setContentType("text/html;charset=utf-8");
					
					String cmd = request.getParameter("cmd");
					String fname =request.getParameter("fname");
					String lname =request.getParameter("lname");
					String email =request.getParameter("email");
					String job =request.getParameter("job");
					String hdate =request.getParameter("hdate");
					String empId = request.getParameter("empId");
					
					Employee emp = new Employee();
					emp.setFirstName(fname);
					emp.setLastName(lname);
					emp.setEmail(email);
					emp.setJobId(job);
					emp.setHireDate(hdate);
					
					
					//등록
				if(cmd.equals("insert")) {
					
	 				EmpDAO dao = new EmpDAO();
	 				dao.insertEmp(emp);
					}
				
				//수정
				else if(cmd.equals("update")) {
					emp.setEmployeeId(Integer.parseInt(empId));
					EmpDAO dao = new EmpDAO();
				if(	dao.updateEmp(emp) == null) {
					System.out.println("error");
					
				}else {
					System.out.println("success");
				}
					
					
					}
				//삭제
				else if(cmd.equals("delete")) {
					emp.setEmployeeId(Integer.parseInt(empId));
					EmpDAO dao = new EmpDAO();
					dao.delEmp(emp);
					
				}
				
				Gson gson = new GsonBuilder().create();
				response.getWriter().print(gson.toJson(emp));
	}

}
