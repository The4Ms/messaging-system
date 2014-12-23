package ia.project.mmm.controller;

import ia.project.mmm.service.ServiceLocater;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Mohamed Kamal
 */
@WebServlet(name = "UpdateProfile", urlPatterns = {"/updateProfile"})
public class UpdateProfile extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse response)
            throws ServletException, IOException {
        String username = (String)req.getSession(false).getAttribute("username");
        
        String fullname = req.getParameter("fullname");
        String password = req.getParameter("password");
        
        ServiceLocater.getUserService().editUser(username, fullname, password);
        req.getRequestDispatcher("WEB-INF/OperationSuccess.jsp").forward(req, response);
    }
}
