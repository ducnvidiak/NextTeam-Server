package nextteam.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import java.io.BufferedReader;
import java.util.List;
import javax.servlet.annotation.MultipartConfig;
import nextteam.Global;
import nextteam.models.Event;
import nextteam.models.response.EventResponse;
import nextteam.utils.database.EventDAO;

@WebServlet(name = "ReviewEventServlet", urlPatterns = {"/review-event-servlet"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 10,
        maxRequestSize = 1024 * 1024 * 50
)
public class ReviewEventServlet extends HttpServlet {

    private final Gson gson = new Gson();
    private final EventDAO eventDAO;

    public ReviewEventServlet() {
        // Khởi tạo EventDAO với kết nối cơ sở dữ liệu
        this.eventDAO = Global.eventDao;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String command = request.getParameter("cmd");
        String clubId = request.getParameter("clubId");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String userId = request.getParameter("userId");
        if (command.equals("list")) {
            List<EventResponse> events = eventDAO.getAllEventsDetailForAdminReview();

            String eventsJsonString = gson.toJson(events);
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
        String clubId = request.getParameter("clubId");

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
                    Event event = gson.fromJson(jsonInput.toString(), Event.class);
                    System.out.println(event.getName());
                    Global.eventDao.createEventForAdmin(event);
                    List<EventResponse> events = eventDAO.getAllEventsDetailForAdmin();
                    String eventsJsonString = gson.toJson(events);
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    PrintWriter out = response.getWriter();
                    response.getWriter().write(eventsJsonString);
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
            case "update" -> {
                try {
                    // Đọc dữ liệu JSON từ request
                    BufferedReader reader = request.getReader();
                    StringBuilder jsonInput = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        jsonInput.append(line);
                    }
                    Event event = gson.fromJson(jsonInput.toString(), Event.class);
                    int rs = Global.eventDao.updateEventByEventId(eventId, event);
                    List<EventResponse> events = eventDAO.getAllEventsDetailForAdmin();
                    String eventsJsonString = gson.toJson(events);
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    PrintWriter out = response.getWriter();
                    response.getWriter().write(eventsJsonString);
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

            case "delete" -> {
                int rs = Global.eventDao.deleteEventByEventId(eventId);
                List<EventResponse> events = eventDAO.getAllEventsDetailForAdmin();
                String eventsJsonString = gson.toJson(events);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                PrintWriter out = response.getWriter();
                response.getWriter().write(eventsJsonString);
            }
            default -> {
            }
        }

    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

       response.setContentType("application/json");
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String eventId = request.getParameter("id");
        String status = request.getParameter("status");
        String feedback = request.getParameter("feedback");

        System.out.println("feedback: " + feedback + "  " + status);

        int result = eventDAO.updateEventStatus(eventId, status, feedback);
        System.out.println("Received update status request: " + status + "  " + eventId);

        JsonObject jsonRes = new JsonObject();
        jsonRes.addProperty("status", (result == 1 ? "success" : "failure"));

        String resJsonString = this.gson.toJson(jsonRes);
        PrintWriter out = response.getWriter();
        out.print(resJsonString);
        out.flush();

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
