/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Helper;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;


public class ReadWriteHelper {
    
    
   private static File file;
   static String filePath = new File("").getAbsolutePath();
   

    
    public ReadWriteHelper() {
    }
    
   
   
   public  static void createFile(String listType) {

        Preferences prefs=Preferences.userNodeForPackage(ReadWriteHelper.class);
        
        //Preferences pref=Preferences.userNodeForPackage(OnlinePlayerController.class);
        
          CustomDate c=new CustomDate();

        if(listType.equals("local-mode")){
            prefs.put(c.getCurrentDateTime(), c.getCurrentDateTime()); 
              File dir = new File("record/savedLocalGame");
              dir.mkdirs();
              file = new File(dir,prefs.get(c.getCurrentDateTime(),""));
              
        }else if(listType.equals("online-mode")){
            File dir = new File("record/savedOnlineGame");
            dir.mkdirs();
           // pref.put(c.getCurrentDateTime(), c.getCurrentDateTime());
           // file = new File(dir,pref.get(c.getCurrentDateTime(),""));
            
        }  
           try {
               
               if(file.createNewFile())
                   System.out.println("file created");
                            
           } catch (IOException ex) {
               Logger.getLogger(ReadWriteHelper.class.getName()).log(Level.SEVERE, null, ex);
           }
   }

    public  static void writeFile(String s)
    {
        try {
           // file.createNewFile();
            System.out.println("file Created an writ into it");

            if(file.exists())
            {
                FileWriter writer=new FileWriter(file,true);
                writer.write(s);   
                writer.flush();
                writer.close();
                System.out.println("write in file");
               
            }
        } catch (IOException ex) {
            System.out.println("error during write file");
            Logger.getLogger(ReadWriteHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
              
    }
    
    public static String readFileAsString(String fileName) 
    {  String data = "";
       try {
          
           data = new String(Files.readAllBytes(Paths.get(fileName))); 
           
       } catch (IOException ex) {
         
           Logger.getLogger(ReadWriteHelper.class.getName()).log(Level.SEVERE, null, ex);
       }
     return data;   
    }
}

