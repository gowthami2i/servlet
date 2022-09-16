package com.ideas2it.controller;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
@WebServlet("/demo")
public class HelloForm extends HttpServlet {

        public void doGet(HttpServletRequest request, HttpServletResponse response)
                {

            // Set response content type
            response.setContentType("text/html");
          try {
              PrintWriter out = response.getWriter();
              String title = "Using GET Method to Read Form Data";
              String docType =
                      "<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n";

              out.println(docType +
                      "<html>\n" +
                      "<head><title>" + title + "</title></head>\n" +
                      "<body bgcolor = \"#f0f0f0\">\n" +
                      "<h1 align = \"center\">" + title + "</h1>\n" +
                      "<ul>\n" +
                      "  <li><b>First Name</b>: "
                      + request.getParameter("first_name") + "\n" +
                      "  <li><b>Last Name</b>: "
                      + request.getParameter("last_name") + "\n" +
                      "</ul>\n" +
                      "</body>" +
                      "</html>"
              );
          } catch (Exception e){

            }
        }
}

