package co.edu;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@WebServlet("/ScheduleServlet")
public class ScheduleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ScheduleServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/json;charset=utf-8");
		
		EmpDAO dao = new EmpDAO();
		List<Schedule> list = dao.scheduleList();
		Gson gson = new GsonBuilder().create();
		response.getWriter().print(gson.toJson(list));
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String job = request.getParameter("job");
		String title = request.getParameter("title");
		String sd = request.getParameter("startDate");
		String ed = request.getParameter("endDate");
		Schedule sch = new Schedule();
		sch.setTitle(title);
		sch.setStartDate(sd);
		sch.setEndDate(ed);
		
		EmpDAO dao = new EmpDAO();
		// 등록이면
		if(job.equals("add")) {
			dao.insertSchedule(sch);
			// {"retCode":"Success"}
			response.getWriter().print("{\"retCode\":\"Success\"}");
			
		// 수정이면
		}else if(job.equals("del")) {
			dao.deleteSchedule(sch);
			response.getWriter().print("{\"retCode\":\"Success\"}");
			
		// 둘다 아니면
		}else {
			response.getWriter().print("{\"retCode\":\"No Success\"}");
		}
	}

}
