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

import eu.mamakis.conceptualflow.Document;
import eu.mamakis.conceptualflow.SentenceCreator;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;

/**
 * Summarization servlet
 *
 * @author Yorgos Mamakis
 */
public class SummarizeServlet extends HttpServlet {

    private final static String HEADER = "<html><head><meta http-equiv='Content-Type' content='text/html; charset=UTF-8' /><title>Document Summary</title>";
    private final static String MIDDLE = "</td></tr><tr><td><h3>Summary</h3></td></tr><tr><td>";
    private final static String FOOTER = "</td></tr></table><br /> Summarization took ";
    private final static String FOOTER2 = " <br /><br /><a href='/conceptualflow/IndexServlet'>Back</a></body></html>";

    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        request.setCharacterEncoding("UTF-8");
        Properties props = new Properties();
        props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("sum.properties"));
        String document = (String) request.getParameter(props.getProperty("textarea.id"));

        String percentage = (String) request.getParameter("percentage");
        String language = (String) request.getParameter("language");
        
        String[] languages = StringUtils.split(props.getProperty("languages"),",");
        String[] implementationClasses = StringUtils.split(props.getProperty("implementation.classes"),",");
        String implementationClass = "";
        for (int i=0;i<languages.length;i++){
            if(StringUtils.equals(languages[i],language)){
                implementationClass = implementationClasses[i];
                break;
            }
        }
        
        SentenceCreator sCreator=null;
        try {
            
                sCreator = (SentenceCreator)Class.forName(implementationClass).newInstance();
        } catch (IllegalAccessException ex) {
            Logger.getLogger(SummarizeServlet.class.getName()).log(Level.SEVERE, null, ex);    
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SummarizeServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(SummarizeServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
                
        Document doc = new Document(document, Float.parseFloat(percentage) / 100,sCreator);


        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        
        try {
             
            out.println(HEADER);
            if(props!=null&&props.getProperty("css")!=null){
            out.println("<link rel='stylesheet' type='text/css' href="+props.getProperty("css")+" />");
            }
            String body= "</head><body><div><img width='500px' height='50px' src='"+props.getProperty("header.image") +"' alt='GUTS system'/></div>"
                    + "<table id='"+props.getProperty("results") +"' name='"+props.getProperty("results")+"'><tr><td><h3>Original Document</h3></td><tr><tr><td>";
            out.println(body);
            out.println(document);
            out.println(MIDDLE);
            long startSummarization = new Date().getTime();
            out.println(doc.summarize());
            
            out.println(FOOTER);
            out.println((new Date().getTime() - startSummarization) + " ms");
            out.println("<br />");
            out.println("Original size "+doc.getOriginalSentencesSize());
            out.println("<br />");
            out.println("Summary size "+doc.getSummarySentencesSize());
            out.println("<br />");
            out.println(FOOTER2);
            
        } finally {
            out.close();
        }
    }

   
    /**
     * Handles the HTTP
     * <code>GET</code> method. Not supported in this context
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        throw new UnsupportedOperationException("GET is not supported by this servlet");
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
        return "Summarization endpoint servlet.  Only POST data are supported.";
    }
}
