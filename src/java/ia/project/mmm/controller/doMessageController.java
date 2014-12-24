/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ia.project.mmm.controller;

import ia.project.mmm.model.Message;
import ia.project.mmm.model.UserInfo;
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
@WebServlet(name = "doMessageController", urlPatterns = {"/doMessage"})
public class doMessageController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse response)
            throws ServletException, IOException {
        String username = (String)req.getSession(false).getAttribute("username");
        
        String type = req.getParameter("type");
        String id = req.getParameter("id");
        Integer msgId = null;
        try {
            msgId = Integer.parseInt(id);
        } catch (Exception e) {
        }
        if(type == null || msgId == null){
            response.sendRedirect("index.jsp");
            return;
        }
        
        Message message = ServiceLocater.getMessageService().getMessageById(msgId);
        
        if(message == null){
            response.sendRedirect("index.jsp");
            return;
        }
        
        if(type.equals("delete")){
            ServiceLocater.getMessageService().deleteMessageForever(username, msgId);
        }
        else if(type.equals("trash")){
            ServiceLocater.getMessageService().trashMessage(username, msgId);
        }
        else if(type.equals("reply")){
            req.setAttribute("to", message.getReceiversUsernames());
            req.setAttribute("subject", "RE:" + message.getSubject());
            req.setAttribute("body", "\n\n\n\nRE:\n" + message.toString());
            req.getRequestDispatcher("compose.jsp").forward(req, response);
        }
        else if(type.equals("forward")){
            req.setAttribute("to", "");
            req.setAttribute("subject", "FW:\n" + message.getSubject());
            req.setAttribute("body", "\n\n\n\nFW:\n" + message.toString());
            req.getRequestDispatcher("compose.jsp").forward(req, response);
            
        }
    }
}
