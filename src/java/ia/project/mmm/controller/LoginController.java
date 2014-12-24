package ia.project.mmm.controller;

import ia.project.mmm.service.ServiceLocater;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Mohamed Kamal
 */
@WebServlet(name = "Login", urlPatterns = {"/login"})
public class LoginController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        if(ServiceLocater.getUserService().isValidUser(username, password)){
            HttpSession session = request.getSession(true);
            session.setAttribute("username", username);
            session.setAttribute("fullname", ServiceLocater.getUserService().getUserByUsername(username).getFullname());
            
            response.sendRedirect("index.jsp");
        }
        else{
            response.sendRedirect("login.html");
        }
    }

}
