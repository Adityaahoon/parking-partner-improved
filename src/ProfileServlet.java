import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.ResultSet;

@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = (String) request.getSession().getAttribute("user");
        Connection conn = App.connect();
        
        ResultSet profileData = App.viewProfile(conn, username);
        response.setContentType("application/json");
        
        try {
            if (profileData != null && profileData.next()) {
                String json = "{ \"username\": \"" + profileData.getString("User_Name") + "\", " +
                              "\"email\": \"" + profileData.getString("Email") + "\", " +
                              "\"contact\": \"" + profileData.getString("Contact_No") + "\" }";
                response.getWriter().write(json);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
