/*Copyright 2012 Yorgos Mamakis
 *
 *Licensed under the Apache License, Version 2.0 (the "License");
 *you may not use this file except in compliance with the License.
 *You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 *distributed under the License is distributed on an "AS IS" BASIS,
 *WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *See the License for the specific language governing permissions and
 *limitations under the License.
 * 
 */
package eu.mamakis.conceptualflow.util.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;

/**
 * Index page
 * @author Yorgos Mamakis
 */
public class IndexServlet extends HttpServlet {

   
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
         Properties props = new Properties();
        
            props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("sum.properties"));
            
            out.println("<html>");
            out.println("<head>");
            out.println("<meta http-equiv='Content-Type' content='text/html; charset=UTF-8' />");
            out.println("<title>Document Summarization Start Page</title>");
            if(props!=null&&props.getProperty("css")!=null){
                out.println("<link rel='stylesheet' type='text/css' href='"+props.getProperty("css")+"' />");
            }
            out.println("</head>");
            out.println("<body>");
            out.println("<div><img width='500px' height='50px' src='"+props.getProperty("header.image") +"' alt='GUTS system'/></div>");
            out.println("<h1>Generic Unsupervised Text Summarization for Greek Language</h2>");
            out.println("<p>"+props.getProperty("text")+"</p>");
            out.println("<h2>Give Document</h2>");
            out.println("<form method=POST action='/conceptualflow/SummarizeServlet' accept-charset='UTF-8'>");
            out.println("<textarea id='"+props.getProperty("textarea.id") +"' name='"+props.getProperty("textarea.id") +"'></textarea><br />Percentage (e.g. 20 for 20%)<br /><input type='text' name='percentage' id='percentage'/><br />");
            out.println("<select name='language'>");
            String[] languages= StringUtils.split(props.getProperty("languages"), ",");
            for(String language:languages){
                out.println("<option name='"+language+"'>"+language+"</option>");
            }
            out.println("</select>");
            out.println("<input type='submit'/>");
            out.println("</form>");
            out.println("<br /><br />This application is distributed under the Apache Software Foundation License v2.0");
            out.println("available at  http://www.apache.org/licenses/LICENSE-2.0 . ");
            out.println("</body>");
            out.println("</html>");
        } finally {            
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
