/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.services;

import com.codename1.components.ToastBar;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import com.mycompany.myapp.entities.Task;
import com.mycompany.myapp.utils.Statics;
import com.mycompany.myapp.utils.Toaster;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author bhk
 */
public class ServiceTask {
 ToastBar.Status popup;
    public ArrayList<Task> tasks;
    
    public static ServiceTask instance=null;
    public boolean resultOK;
    private ConnectionRequest req;

    private ServiceTask() {
         req = new ConnectionRequest();
    }

    public static ServiceTask getInstance() {
        if (instance == null) {
            instance = new ServiceTask();
        }
        return instance;
    }

    public boolean addTask(Task t) {
       String url = Statics.BASE_URL+"ajouter/";
       req.setUrl(url);
       req.setPost(false);
       req.addArgument("nom", t.getNom());
        req.addArgument("image", t.getImage());
         req.addArgument("descri", t.getDescri());
         req.addArgument("idcat" ,t.getIdcat()+"");
       //req.addArgument("status", t.getStatus()+"");
       req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }

    public ArrayList<Task> parseTasks(String jsonText){
        try {
            tasks=new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String,Object> tasksListJson = 
               j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            
            List<Map<String,Object>> list = (List<Map<String,Object>>)tasksListJson.get("root");
            for(Map<String,Object> obj : list){
                Task t = new Task();
                float id = Float.parseFloat(obj.get("id").toString());
                t.setId((int)id);
               // t.setStatus(((int)Float.parseFloat(obj.get("status").toString())));
                if (obj.get("nom")==null)
              t.setNom("null");
                else
                    t.setNom(obj.get("nom").toString());
                
                t.setDescri(obj.get("descri").toString());
                t.setImage(obj.get("image").toString());
                
                t.setIdcat(((int)Float.parseFloat(obj.get("idcat").toString())));
                tasks.add(t);
            }
            
            
        } catch (IOException ex) {
            
        }
        return tasks;
    }
    
    public ArrayList<Task> getAllTasks(){
        req = new ConnectionRequest();
        //String url = Statics.BASE_URL+"/tasks/";
        String url = Statics.BASE_URL;
        //System.out.println("===>"+url);
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                tasks = parseTasks(new String(req.getResponseData()));
                req.removeResponseListener(this);
                
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return tasks;
    }
    
    public boolean modifier(Task t,int id) {
         
         String url = Statics.BASE_URL +"modifier/"+id+"/";
	       req.setUrl(url);
	       req.setPost(false);
	       req.addArgument("nom", t.getNom()+"");
	       
	       req.addArgument("descri", t.getDescri()+"");
               req.addArgument("image", t.getImage()+"");
               
	       req.addResponseListener(new ActionListener<NetworkEvent>() {
	            @Override
	            public void actionPerformed(NetworkEvent evt) {
	                resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
	                req.removeResponseListener(this);
	               
	            }
	        });
	        NetworkManager.getInstance().addToQueueAndWait(req);
	        return resultOK;
    }
     public boolean delete(int id) {

        popup = Toaster.showLoading();
        String url = Statics.BASE_URL + id;
        ConnectionRequest request = new ConnectionRequest(url);
       
        request.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = request.getResponseCode() == 200;
                popup.clear();
                request.removeResponseListener((ActionListener<NetworkEvent>) this);
            }
        });

        NetworkManager.getInstance().addToQueueAndWait(request);
        return resultOK;
    }
}
