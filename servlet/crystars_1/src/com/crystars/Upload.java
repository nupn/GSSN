package com.crystars;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.RequestDispatcher;
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
import org.apache.commons.io.IOExceptionWithCause;
import org.json.JSONException;
import org.json.JSONObject;

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
    
    public String writeFile(){
    	/*
    
    	BufferedReader br = null;
    	  try{
    	   int start = 3;
    	   int end   = 10; 
    	//   저장된 파일 읽기 
    	   br = new BufferedReader(new FileReader("c:/aaa.txt"));
    	   String viewStr = "";
    	   String line = null;
    	   int i = 0; // 제목 부분 카운트
    	   int subCnt = 1; // 총 제목 라인 수
    	   int tmpEnd = 0; 
    	   while((line = br.readLine()) != null){ // 문자열의 한라인씩 읽는다
    	    tmpEnd += line.length();
    	    if( subCnt > i++  ) continue; // 제목 걸러내기 
    	    else {                        // 파일에서 읽은 한라인에 대한 문자열을 붙인다. 
    	     viewStr += line;    // end 의 길이 보다 끄면 빠져나온다.
    	     if(tmpEnd > end ) break;
    	    }
    	   }
    	   // tmpEnd가 시작과 끝의 입력값의 조건 체크해서 아래와 같이 한다. 아래는 시작위치 체크안함 
    	   if(tmpEnd > end) System.out.println(viewStr.substring(start ,end));
    	   else System.out.println(viewStr.substring(start , tmpEnd));
    	  }catch(Exception e){
    	   
    	  }
    	  */
    	  //---------------------
    	  String filePath = getServletContext().getRealPath("upload")+"/imgCount.txt";
    	  int count=1;
    	  String data="";
    	  FileWriter fw=null;
    	  BufferedReader br=null;
    	  
    	  try{

    	  File f = new File(filePath); // 파일객체생성
    	 
    	  FileReader fr = new FileReader(filePath); //파일읽기객체생성
    	  br = new BufferedReader(fr); //버퍼리더객체생성

    	  String line = null; 
    	  while((line=br.readLine())!=null){ //라인단위 읽기
    		  count = Integer.parseInt(line); 
    		  //out.print(count);
    	  }
    	  
    	  count++;

    	  // 파일쓰기
    	  fw = new FileWriter(filePath); //파일쓰기객체생성, true = 파일이 존재하면 생성 안함
    	  data = String.valueOf( count );
    	  fw.write(data); //파일에다 작성
    	  fw.close();
    	  br.close();
    	  }catch (IOException e) {
    		  try{
    		  if(fw!=null)
    		  fw.close();
    		  if(br!=null)
        	  br.close();
    		  }catch(IOException ee){
    			  ee.printStackTrace();
    		  }
    		  e.printStackTrace();
    	  }
    	  
    	  return data;
    }
    
    public void moveToPerserve(String fileName){
    	 String filePath = getServletContext().getRealPath("upload")+"/"+fileName;
   	  	String newFilePath = getServletContext().getRealPath("upload")+"/perserve/"+fileName;
//   	  try{
      File n = new File(newFilePath);
   	  File f = new File(filePath);
   	  f.renameTo(n);
//   	  }catch(IOException e)
//   	  {
//   		  
//   	  }
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/json");
		
		PrintWriter out = response.getWriter();
		String delframe=null;
		
		try{
		String delFile = request.getParameter("filename");
		delframe = request.getParameter("delframe");
		String filePath   = getServletContext().getRealPath("upload"); //업로드 경로
		       File up1 = new File(filePath + "/" + delFile);
		   
		       if(up1.exists()){
		            boolean rslt = up1.delete();
		       }
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		//out.close();
		/*
		JSONObject item=null;
		try{
		item= new JSONObject();
		item.put("delframe", delframe);
		item.put("delfile", "defaultSmall.jpg");
		out.print(item.toString());
		}catch(JSONException e)
		{
			e.printStackTrace(out);
		}
		*/
		out.close();
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String path = getServletContext().getRealPath("upload");
		DiskFileItemFactory factory= new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		
		factory.setSizeThreshold(1024);
		upload.setSizeMax(-1);
		upload.setHeaderEncoding("Windows-31J");
		String temp="";
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
						temp = fileName.split("\\.")[1];
						temp = writeFile()+"."+temp;
						fileName = temp;
						request.setAttribute("filename", fileName);
						//out.print(fileName);
						fileName = (new File(fileName)).getName();
						fitem.write(new File(path+"/"+fileName));
						
						//moveToPerserve(fileName);
					}
					
				}
				
			}
			
		}catch(FileUploadException e){
			e.printStackTrace();
		
		}catch(Exception e){
		
			e.printStackTrace();
		}
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("imgRegEnd.jsp");
		  dispatcher.forward(request,  response);
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
