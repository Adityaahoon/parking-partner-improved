import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/booking")
public class BookingServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public BookingServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String location = request.getParameter("location");
        String vehicleType = request.getParameter("vehicleType");
        String date = request.getParameter("date");
        String entryTime = request.getParameter("entryTime");
        String exitTime = request.getParameter("exitTime");
        int hours = Integer.parseInt(request.getParameter("hours"));

        // Assume this method processes the booking logic
        processBooking(location, vehicleType, date, entryTime, exitTime, hours);

        response.getWriter().println("Booking Successful!");
    }

    private void processBooking(String location, String vehicleType, String date, String entryTime, String exitTime, int hours) {
        // Booking logic implementation
        System.out.println("Booking details: " + location + ", " + vehicleType + ", " + date + ", " + hours + " hours.");
    }
}
