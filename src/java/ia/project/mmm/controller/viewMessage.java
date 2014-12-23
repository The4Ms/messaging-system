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
@WebServlet(name = "viewMessage", urlPatterns = {"/viewMessage"})
public class viewMessage extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer msgId = null;
        try{
            msgId = Integer.parseInt(req.getParameter("id"));
        }
        catch(Exception ex){
            resp.sendRedirect("index.jsp");
            return;
        }
        
        Message msg = ServiceLocater.getMessageService().getMessageById(msgId);
        
        // should check if the sender can view this message 
        // because someone might try to put an id in the url
        
        req.setAttribute("message", msg);
        req.getRequestDispatcher("WEB-INF/viewMessage.jsp").forward(req, resp);
    }
}