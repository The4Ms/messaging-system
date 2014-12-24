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
@WebServlet(name = "viewMessage", urlPatterns = {"/viewMessage"})
public class viewMessage extends HttpServlet {

    private boolean find(Message[] msgs, Message m){
        for(Message ms : msgs){
            if(ms.getId() == m.getId())
                return true;
        }
        return false;
    }
    private boolean isUserInMessage(String username, Message msg){
        if(msg.getSender().getUsername().equals(username)){
            return find(ServiceLocater.getMessageService().getSentOf(username), msg)|| find(ServiceLocater.getMessageService().getTrashOf(username), msg);
        }
        
        for(UserInfo rec : msg.getReceivers()){
            if(rec.getUsername().equals(username)){
                return find(ServiceLocater.getMessageService().getInboxOf(username), msg)|| find(ServiceLocater.getMessageService().getTrashOf(username), msg);
            }
        }
        
        return false;
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = (String) req.getSession(false).getAttribute("username");
        
        Integer msgId = null;
        try{
            msgId = Integer.parseInt(req.getParameter("id"));
        }
        catch(Exception ex){
            resp.sendRedirect("index.jsp");
            return;
        }
        
        Message msg = ServiceLocater.getMessageService().getMessageById(msgId);
        
        if(msg == null || !isUserInMessage(username, msg))
        {
            resp.sendRedirect("index.jsp");
            return;
        }
        
        // should check if the sender can view this message 
        // because someone might try to put an id in the url
        
        req.setAttribute("message", msg);
        req.getRequestDispatcher("WEB-INF/viewMessage.jsp").forward(req, resp);
    }
}
