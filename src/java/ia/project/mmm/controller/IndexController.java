/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ia.project.mmm.controller;

import ia.project.mmm.model.Message;
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
@WebServlet(name = "IndexController", urlPatterns = {"/index.jsp"})
public class IndexController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = (String)req.getSession(false).getAttribute("username");
        
        String type = req.getParameter("view");
        if(type == null)
            type = "inbox";
        
        Message[] messages = null;
        
        if(type.equals("sent")){
            messages = ServiceLocater.getMessageService().getSentOf(username);
        }
        else if(type.equals("drafts")){
            messages = ServiceLocater.getMessageService().getDraftsOf(username);
        }
        else if(type.equals("trash")){
            messages = ServiceLocater.getMessageService().getTrashOf(username);
        }
        else{
            // go to inbox
            type = "inbox";
            messages = ServiceLocater.getMessageService().getInboxOf(username);
        }
        
        req.setAttribute("title", type);
        req.setAttribute("messages", messages);
        req.getRequestDispatcher("WEB-INF/showMessages.jsp").forward(req, resp);
    }
}
