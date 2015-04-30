import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class TestServlet
 */
public class UpdateEvaluationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public UpdateEvaluationServlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String nom=request.getParameter("nom");
		int id=Integer.valueOf(request.getParameter("id"));
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
		Date date=null;
		try {
			date=format.parse(request.getParameter("date"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String strComps=request.getParameter("listcomp");
		String[] strsComp=strComps.split("-");
		int[] compsId=new int [strsComp.length];
		for(int i=0;i<compsId.length;i++){
			compsId[i]=Integer.valueOf(strsComp[i]);
		}
		
		BDDConnection conn =BDDConnection.getInstance();
		boolean b=conn.updateEvaluation(id,nom,date,compsId);
		
		response.getOutputStream().println(b);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}
	


}
