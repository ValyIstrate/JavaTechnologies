package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import servlet.utils.CustomLogger;

import java.io.IOException;

@WebServlet("/page-select")
public class ControllerServlet extends HttpServlet {

    private final CustomLogger log = new CustomLogger();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String selectedPage = request.getQueryString().split("=")[1];
        String userAgent = request.getHeader("User-Agent").split("/")[0];
        log.info(request.getMethod(), request.getRemoteAddr(), userAgent,
                request.getHeader("Accept-Language"), selectedPage);
        boolean isBrowser = "mozilla".equalsIgnoreCase(userAgent);

        if (isBrowser) {
            if ("1".equals(selectedPage)) {
                request.getRequestDispatcher("/page1.html").forward(request, response);
            } else {
                request.getRequestDispatcher("/page2.html").forward(request, response);
            }
        } else {
            response.setContentType("text/plain");
            String res = String.format("You selected page: %s", selectedPage);
            response.getWriter().println(res);
        }
    }

}
