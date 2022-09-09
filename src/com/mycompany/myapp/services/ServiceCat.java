/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.myapp.services;

import com.codename1.components.ToastBar;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
 
import com.mycompany.myapp.entities.Categorie;
import com.mycompany.myapp.entities.Task;
import com.mycompany.myapp.utils.Statics;
import com.mycompany.myapp.utils.Toaster;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

 

/**
 *
 * @author asmab
 */
public class ServiceCat {
   ToastBar.Status popup;
   public ArrayList<Categorie> cats;
   public ConnectionRequest req;
   private static ServiceCat instance;
   public boolean resultOK;
   private ServiceCat(){
       req=new ConnectionRequest();
       
   }
   public static ServiceCat getInstance(){
       if(instance==null)
           instance=new ServiceCat();
       return instance;
   }
   public ArrayList<Categorie> getAllCat(){
       String url =Statics.BASE_URLcat;
		req.setUrl(url);
		req.setPost(false);
                req.addResponseListener(new ActionListener<NetworkEvent>()
				{
			@Override
            public void actionPerformed(NetworkEvent evt) {
                            try {
                                cats = parseCat(new String(req.getResponseData()));
                            } catch (IOException ex) {
                            }


                req.removeResponseListener(this);
            }
		});
		NetworkManager.getInstance().addToQueueAndWait(req);
	
	return cats;
   }
   public int taillecat(){
       return getAllCat().size();
   }
   private ArrayList<Categorie> parseCat(String jsonText) throws IOException{
      cats=new ArrayList<>();
      JSONParser j =new JSONParser();
      try {
	Map<String ,Object> EvntListJdon =j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
List<Map<String ,Object>> list= (List<Map<String, Object>>) EvntListJdon.get("root");
for(Map<String ,Object> obj :list) {
    Categorie t=new Categorie();
       float id= Float.parseFloat(obj.get("id").toString());

    String nom=obj.get("nom").toString();
    t.setNom(nom);
   t.setId((int)id);
    cats.add(t);
    
}
} catch (IOException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
      return cats;
   }
   
   
   
    public boolean addCat(Categorie t) {
       String url = Statics.BASE_URLcat+"ajouter/";
       req.setUrl(url);
       req.setPost(false);
       req.addArgument("nom", t.getNom());
       
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
        String url = Statics.BASE_URLcat+ id;
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
