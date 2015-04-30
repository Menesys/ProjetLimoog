import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class TestServlet
 */
public class GetNoteByCompEvalUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public GetNoteByCompEvalUserServlet() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		BDDConnection conn = BDDConnection.getInstance();
		int c = -1, ev = -1, u = -1;
		String comp = request.getParameter("comp");
		String eval = request.getParameter("eval");
		String user = request.getParameter("user");
		if (comp != null) {
			try {
				c = Integer.valueOf(comp);
			} catch (NumberFormatException e) {
			}
		}
		if (eval != null) {
			try {
				ev = Integer.valueOf(eval);
			} catch (NumberFormatException e) {
			}
		}

		if (user != null) {
			try {
				u = Integer.valueOf(user);
			} catch (NumberFormatException e) {

			}
		}

		if (c != -1 && u != -1 && ev != -1) {
			response.getOutputStream().println(
					conn.getNoteByCompEvalUser(c, ev, u));
		} else if (u != -1 && ev != -1) {
			response.getOutputStream().println(conn.getNoteByEvalUser(ev, u));
		} else if (c != -1 && ev != -1) {
			response.getOutputStream().println(conn.getNoteByCompEval(c, ev));
		} else if (u != -1 && c != -1) {
			response.getOutputStream().println(conn.getNoteByCompUser(c, u));
		} else if (u != -1) {
			response.getOutputStream().println(conn.getNoteByUser(u));
		} else if (c != -1) {
			response.getOutputStream().println(conn.getNoteByComp(c));
		} else if (ev != -1) {
			response.getOutputStream().println(conn.getNoteByEval(ev));
		} else {

		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
