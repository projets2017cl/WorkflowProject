package dynamicview;

import ConnectionManager.DatabaseBuilder;
import ConnectionManager.dbTablesObjectManager;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowTables extends HttpServlet{
private static final long serialVersionUID = 1L;
final ArrayList<String> table_name_list = new ArrayList<String>();

@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    final ArrayList<String> table_name_list = new ArrayList<String>();
Hashtable<String, String> hashTablesExistngTables= new Hashtable<String,String>();
   ResultSet rs= DatabaseBuilder.getInstance().getDatabase("sysDb").SelectStatement("SELECT * from PUBLIC.cstables") ;
   
// ResultSet rs= DatabaseBuilder.getInstance().getDatabase("sysDb").SelectStatement("SELECT table_schema,table_name FROM information_schema.tables where table_schema ='public' and table_name Not IN (SELECT tablename FROM PUBLIC.cstables);");
    
    try {
        while (rs.next()) {
            String table_name = rs.getString("tablename");
            String db_name = rs.getString("dbname");
       //     table_name_list.add(table_name);
       hashTablesExistngTables.put(table_name+""+db_name,table_name);
       
        }
        
    } catch (SQLException ex) {
        Logger.getLogger(GetRecord.class.getName()).log(Level.SEVERE, null, ex);
    }
    for (String dbName : DatabaseBuilder.getInstance().getAllDatabases().keySet()) 
			{
				Hashtable< String , String> hashTableNames= dbTablesObjectManager.getTablesOfSchema(dbName);
		for (String tableName : hashTableNames.keySet()) {
			//System.out.println("tablename"+ tableName);
                        String tablename_dbname=tableName+""+dbName;
                        if (!hashTablesExistngTables.containsKey(tablename_dbname)){
                            table_name_list.add(tableName);
                        }
		}
    request.setAttribute("ShowTables",table_name_list);
    request.getRequestDispatcher("/DbTableShow.ftl").forward(request, response);
    
                        
   }
    }
@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {  
   
        String[] values = request.getParameterValues("ALL");
        response.setContentType("text/html");
      PrintWriter out = response.getWriter();
    for(int i=0;i<values.length;i++){
      
           Hashtable<String, String> hashTablesInformationxml=dbTablesObjectManager.getTablesSchemaXml("sysDb",values[i]);
     
           String Query = "INSERT INTO public.cstables(dbname, tablename, xmlfield) VALUES ('sysDb','"+values[i]+"','"+hashTablesInformationxml.get(values[i])+"')";
      out.println(Query);
           DatabaseBuilder.getInstance().getDatabase("sysDb").InsertStatement(Query);
 
    } 

     /*  doGet(request, response);*/
    }
    
}