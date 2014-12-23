/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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
@WebServlet(name = "SignUpController", urlPatterns = {"/signup"})
public class SignUpController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String fullname = req.getParameter("fullname");
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        
        if(ServiceLocater.getUserService().addNewUser(username, password, fullname)){
            req.getRequestDispatcher("WEB-INF/OperationSuccess.jsp").forward(req, resp);
        }else{
            req.setAttribute("errorMsg", "This username already exists");
            req.getRequestDispatcher("WEB-INF/OperationFail.jsp").forward(req, resp);
        }
    }

}
