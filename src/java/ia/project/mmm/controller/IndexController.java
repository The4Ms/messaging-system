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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
        
        String filterFrom = req.getParameter("filter.from");
        String filterTo = req.getParameter("filter.to");
        String filterSubject = req.getParameter("filter.subject");
        String filterStartDate = req.getParameter("filter.startDate");
        String filterEndDate = req.getParameter("filter.endDate");
        
        Date startDate = null;
        Date endDate = null;
        try {
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm a");
            startDate = (filterStartDate == null || filterStartDate.isEmpty()) ? new Date(0L) : format.parse(filterStartDate + " 00:00 AM");
            endDate = (null == filterEndDate || filterEndDate.isEmpty()) ? new Date(Long.MAX_VALUE) : format.parse(filterEndDate + " 11:59 PM");
        } catch (Exception ex) {
            Logger.getLogger(IndexController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        List<Message> messagesFiltered = new ArrayList<>();
        
        for(Message m : messages){
            //System.out.println(m.getSentDate().before(startDate)  + " " +  m.getSentDate().after(endDate) + " " + m.getSentDate().getTime() + " === " + endDate.getTime());
            
            if( (filterFrom == null || filterFrom.isEmpty() || m.getSender().getUsername().contains(filterFrom)) &&
                (filterTo == null || isInlist(filterTo, m.getReceivers())) &&
                (filterSubject == null || filterSubject.isEmpty() || m.getSubject().contains(filterSubject)) &&
                (startDate.getTime() <= m.getSentDate().getTime() &&  m.getSentDate().getTime() <= endDate.getTime() )  ){
                
                messagesFiltered.add(m);
            }
        }
        
        req.setAttribute("title", type);
        req.setAttribute("messages", messagesFiltered.toArray(new Message[0]));
        req.getRequestDispatcher("WEB-INF/showMessages.jsp").forward(req, resp);
    }
    private boolean isInlist(String username, UserInfo[] users){
        for(UserInfo rec : users){
            if(rec.getUsername().contains(username))
                return true;
        }
        return false;
    }
}
