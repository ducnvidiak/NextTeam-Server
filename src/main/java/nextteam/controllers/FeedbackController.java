package nextteam.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import java.io.BufferedReader;
import java.util.List;
import nextteam.Global;
import nextteam.models.Event;
import nextteam.models.Feedback;
import nextteam.models.response.EventResponse;
import nextteam.utils.database.EventDAO;

@WebServlet(name = "FeedbackController", urlPatterns = {"/feedbacks"})
public class FeedbackController extends HttpServlet {

    private final Gson gson = new Gson();
    private final EventDAO eventDAO;

    public FeedbackController() {
        // Khởi tạo EventDAO với kết nối cơ sở dữ liệu
        this.eventDAO = Global.eventDao;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String command = request.getParameter("cmd");
        String eventId = request.getParameter("eventId");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String userId = request.getParameter("userId");
        // Xử lý yêu cầu GET, Lấy danh sách sự kiện
        if (command.equals("list")) {
            List<Feedback> feedbacks = Global.feedback.getAllFeedBacksByEventId(eventId);
            String eventsJsonString = gson.toJson(feedbacks);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            response.getWriter().write(eventsJsonString);
//            out.print(events.toString());
            out.flush();
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String cmd = request.getParameter("cmd");
        String eventId = request.getParameter("eventId");
        String userId = request.getParameter("userId");

        switch (cmd) {
            case "create" -> {
                try {
                    // Đọc dữ liệu JSON từ request

                    BufferedReader reader = request.getReader();
                    StringBuilder jsonInput = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        jsonInput.append(line);
                    }

//                    Event event = gson.fromJson(jsonInput.toString(), Event.class);
                    Feedback feedback = gson.fromJson(jsonInput.toString(), Feedback.class);
                    System.out.println("!!! "+feedback.getEventId());
                    Global.feedback.createFeedback(feedback);
                    response.setContentType("application/json");
                    response.getWriter().write(gson.toJson(feedback));
                } catch (JsonSyntaxException e) {
                    // Xử lý ngoại lệ khi có lỗi cú pháp JSON
                    System.out.println("????");
                    System.out.println(e);
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    response.getWriter().write("Invalid JSON data");
                } catch (Exception e) {
                    // Xử lý ngoại lệ chung
                    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    response.getWriter().write("Internal Server Error");
                    e.printStackTrace(); // Ghi log ngoại lệ
                }
            }
            
            default -> {
            }
        }

    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Xử lý yêu cầu DELETE, ví dụ: Xóa một sự kiện
        // Đọc ID sự kiện cần xóa từ request và gọi eventDAO để xóa sự kiện tương ứng
        // Cuối cùng, gửi phản hồi JSON về client để thông báo kết quả
    }

    @Override
    public String getServletInfo() {
        return "Event Servlet";
    }
}
