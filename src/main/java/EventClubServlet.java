import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import nextteam.Global;

@WebServlet(urlPatterns = {"/events-club"})
public class EventClubServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        String clubId = request.getParameter("clubId");
        PrintWriter out = response.getWriter();
        String res = Global.eventDao.getAllEventsDetailForManager(clubId).toString();
        out.print(res);
        out.flush();
    }
}
