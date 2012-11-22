package com.crystars;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 * Servlet implementation class Upload
 */
@WebServlet("/Upload")
public class Upload extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Upload() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		 
		
		
		PrintWriter out = response.getWriter();
		String path = getServletContext().getRealPath("upload");
		DiskFileItemFactory factory= new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		
		factory.setSizeThreshold(1024);
		upload.setSizeMax(-1);
		upload.setHeaderEncoding("Windows-31J");
		
		try{
			List list = upload.parseRequest(request);
			Iterator iterator = list.iterator();
			//FileItemIterator iterator =  upload.getItemIterator(request);
			while(iterator.hasNext())
			{
				FileItem fitem = (FileItem)iterator.next();
				if(!(fitem.isFormField()))
				{
					String fileName = fitem.getName();
					if((fileName!=null)&&(!fileName.equals("")))
					{
						fileName = (new File(fileName)).getName();
						fitem.write(new File(path+"/"+fileName));
						//fitem. (new File(path+"/"+fileName));
					}
					
				}
				
			}
			
		}catch(FileUploadException e){
			e.printStackTrace(out);
		
		}catch(Exception e){
		
			e.printStackTrace(out);
		}
		//*/
		
		/*1
		Collection<Part> parts = request.getParts();
        for(Part part : parts) {
        	
            System.out.println("Name:");
            System.out.println(part.getName());
            System.out.println("Header: ");
            for(String headerName : part.getHeaderNames()) {
                System.out.println(headerName);
                System.out.println(part.getHeader(headerName));
            }
            System.out.println("Size: ");
            System.out.println(part.getSize());
            part.write(part.getName() + "-down");
        }
        //*/
        //request.sendRedirect("/upload");
		
		
		/*
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);                   
		 if (isMultipart) {                                                                                           
		  File temporaryDir = new File("/home/ubuntu/ROOT/we");                                               
		  String realDir = getServletContext().getRealPath("/upload/");
		  
		  DiskFileItemFactory factory = new DiskFileItemFactory();                                   
		  factory.setSizeThreshold(1 * 1024 * 1024);                                                      
		  factory.setRepository(temporaryDir);                                                               
		  ServletFileUpload upload = new ServletFileUpload(factory);                               
		  upload.setSizeMax(10 * 1024 * 1024);       
		  List<FileItem> items;
		  Iterator iter;
		  try{
		  items = upload.parseRequest(request);                                      
		  iter=items.iterator();
		  
		                                                                             
		  while(iter.hasNext()){
		   FileItem fileItem = (FileItem) iter.next();                                                            
		   
		   if(fileItem.isFormField()){                                                                               
		                                                                                                                    
		    out.println("폼 파라미터: "+ fileItem.getFieldName()+"="+fileItem.getString("euc-kr")+"<br>");
		   }else{                                                                                                        
		    if(fileItem.getSize()>0){                                                                                 
		     String fieldName=fileItem.getFieldName();
		     String fileName=fileItem.getName();
		     String contentType=fileItem.getContentType();
		     boolean isInMemory=fileItem.isInMemory();
		     long sizeInBytes=fileItem.getSize();
		     out.println("파일 [fieldName] : "+ fieldName +"<br/>");
		     out.println("파일 [fileName] : "+ fileName +"<br/>");
		     out.println("파일 [contentType] : "+ contentType +"<br/>");
		     out.println("파일 [isInMemory] : "+ isInMemory +"<br/>");
		     out.println("파일 [sizeInBytes] : "+ sizeInBytes +"<br/>");
		     
		     try{
		      File uploadedFile=new File(realDir,fileName);                                                   
		      
		      fileItem.write(uploadedFile);
		      fileItem.delete();                                                                                            
		     }catch(Exception ex) {}
		     
		    }
		   }
		  }
		  
		  }catch(FileUploadException e){
			 	
		  }
		  
		 }else{
		  out.println("인코딩 타입이 multipart/form-data 가 아님.");
		 }
		 */
		
	}

}
