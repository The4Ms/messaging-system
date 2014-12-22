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
@WebServlet(name = "SendMessage", urlPatterns = {"/sendMessage"})
public class SendMessageController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String username = (String)request.getSession(false).getAttribute("username"); 
        String[] to = ((String)request.getParameter("to")).split(",");
        
        for(int i=0; i<to.length; i++)
            to[i] = to[i].trim();
        
        String subject = (String)request.getParameter("subject");
        String body = (String)request.getParameter("body");
        
        String[] failedUsers = ServiceLocater.getMessageService().sendMessage(username, to, subject, body);
        
        request.setAttribute("failedUsers", failedUsers);
        request.getRequestDispatcher("WEB-INF/messageSendingStatus.jsp").forward(request, response);
    }

}
