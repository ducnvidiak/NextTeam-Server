package nextteam.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import java.util.List;
import nextteam.Global;
import nextteam.models.Event;
import nextteam.utils.database.EventDAO;

@WebServlet(name = "EventServlet", urlPatterns = {"/events"})
public class EventServlet extends HttpServlet {

    private final Gson gson = new Gson();
    private final EventDAO eventDAO;

    public EventServlet() {
        // Khởi tạo EventDAO với kết nối cơ sở dữ liệu
        this.eventDAO = Global.eventDao;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("!!!");
        // Xử lý yêu cầu GET, Lấy danh sách sự kiện
        List<Event> events = eventDAO.getAllEvents();
        System.out.println("events" + events);
        System.out.println("nextteam.controllers.EventServlet.doGet()" + events);
        // Chuyển danh sách sự kiện thành JSON
        String eventsJsonString = gson.toJson(events);

        // Thiết lập loại nội dung và ký tự mã hóa cho phản hồi
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Gửi danh sách sự kiện dưới dạng chuỗi JSON về client
        PrintWriter out = response.getWriter();
        out.print(eventsJsonString);
        out.flush();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Xử lý yêu cầu POST, ví dụ: Thêm một sự kiện mới
        // Đọc dữ liệu JSON từ request body và chuyển thành đối tượng Event
        // Ví dụ:
        // BufferedReader reader = request.getReader();
        // Event newEvent = gson.fromJson(reader, Event.class);

        // Gọi eventDAO để thêm sự kiện mới vào cơ sở dữ liệu
        // Ví dụ:
        // boolean success = eventDAO.addEvent(newEvent);
        // Sau khi thêm sự kiện, bạn có thể gửi phản hồi JSON về client để thông báo kết quả
        // Ví dụ:
        // response.setContentType("application/json");
        // PrintWriter out = response.getWriter();
        // String resultJsonString = gson.toJson(success ? "Thêm sự kiện thành công" : "Thêm sự kiện thất bại");
        // out.print(resultJsonString);
        // out.flush();
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Xử lý yêu cầu PUT, ví dụ: Cập nhật thông tin một sự kiện
        // Tương tự như doPost, bạn cần đọc dữ liệu JSON từ request body và chuyển thành đối tượng Event
        // Sau đó, gọi eventDAO để cập nhật thông tin sự kiện trong cơ sở dữ liệu
        // Cuối cùng, gửi phản hồi JSON về client để thông báo kết quả
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
