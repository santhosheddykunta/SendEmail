package com.sendmail;


  import java.util.*;
	import java.sql.*;
	import java.util.Properties;
	import javax.mail.Message;
	import javax.mail.MessagingException;
	import javax.mail.PasswordAuthentication;
	import javax.mail.Session;
	import javax.mail.Transport;
	import javax.mail.internet.InternetAddress;
	import javax.mail.internet.MimeMessage;
	public class DbMail 
	{
		public static void main(String[] args) throws Exception 
		{
			String url="jdbc:mysql://localhost:3306/mydb";
			   String uname="root";
			   String password="1439";
			   String query="Select * from student";
			
			
			 Connection connect = null;
	   	     Statement statement = null;
	   	     PreparedStatement preparedStatement = null;
	   	     ResultSet resultSet = null;
	   	     String sendMessage=null;
	       try {
	           // This will load the MySQL driver, each DB has its own driver
	           Class.forName("com.mysql.cj.jdbc.Driver");
	           // Setup the connection with the DB
	           connect = DriverManager
	                   .getConnection(url, uname, password);

	           // Statements allow to issue SQL queries to the database
	           statement = connect.createStatement();
	           // Result set get the result of the SQL query
	           resultSet = statement
	                   .executeQuery(query);
	           sendMessage= writeResultSet(resultSet);
	          System.out.println("message"+sendMessage);

	           

	       } catch (Exception e) {
	           throw e;
	       } finally {
	          
	       }
	       
	       String to = "ss1667736@gmail.com";

	       // Sender's email ID needs to be mentioned
	       String from = "ss1667736@gmail.com";

	       // Assuming you are sending email from through gmails smtp
	       String host = "smtp.gmail.com";

	       // Get system properties
	       Properties properties = System.getProperties();

	       // Setup mail server
	       properties.put("mail.smtp.host", host);
	       properties.put("mail.smtp.port", "587");
	       properties.put("mail.smtp.starttls.enable", "true");
	       properties.put("mail.smtp.auth", "true");

	       // Get the Session object.// and pass username and password
	       Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

	           protected PasswordAuthentication getPasswordAuthentication() {

	               return new PasswordAuthentication("ss1667736@gmail.com", "uagndsvmhmsazzmq");

	           }

	       });

	       // Used to debug SMTP issues
	       session.setDebug(true);

	       try {
	           // Create a default MimeMessage object.
	           MimeMessage message = new MimeMessage(session);

	           // Set From: header field of the header.
	           message.setFrom(new InternetAddress(from));

	           // Set To: header field of the header.
	           message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

	           // Set Subject: header field
	           message.setSubject("This is the Subject Line!");

	           // Now set the actual message
	           //  message.setText(sendMessage);
	         
	           message.setContent(sendMessage,"text/html");

	           System.out.println("sending...");
	           // Send message
	           Transport.send(message);
	           System.out.println("Sent message successfully....");
	       } catch (MessagingException mex) {
	           mex.printStackTrace();
	       }

	   }


	   public static String writeResultSet(ResultSet resultSet) throws SQLException {
	       // ResultSet is initially before the first data set
	   	
	   	StringBuffer stBuffer=new StringBuffer();
	   	stBuffer.append("<table><tr><th>idStudent</th><th>sName</th><th>mobileNum</th><th>dob</th></tr>");
	       while (resultSet.next()) {
	           // It is possible to get the columns via name
	           // also possible to get the columns via the column number
	           // which starts at 1
	           // e.g. resultSet.getSTring(2);
	    	stBuffer.append("<tr>");
	       	stBuffer.append("<td>" +resultSet.getString("idStudent")+"</td>");
	       	stBuffer.append("<td>" +resultSet.getString("sName")+"</td>");
	       	stBuffer.append("<td>" +resultSet.getString("mobileNum")+"</td>");
	       	stBuffer.append("<td>" +resultSet.getString("dob")+"</td>");
	       	stBuffer.append("</tr>");
	       }
	       stBuffer.append("</table>");
	   	return stBuffer.toString();
	   }
	}



