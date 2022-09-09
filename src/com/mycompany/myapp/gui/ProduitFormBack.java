/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.myapp.gui;

import com.codename1.components.FloatingActionButton;
import com.codename1.components.MultiButton;
import com.codename1.components.ScaleImageLabel;
import com.codename1.components.SpanLabel;
import com.codename1.components.ToastBar;
import com.codename1.ui.Button;
import com.codename1.ui.ButtonGroup;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Component;
import static com.codename1.ui.Component.BOTTOM;
import static com.codename1.ui.Component.CENTER;
import static com.codename1.ui.Component.LEFT;
import static com.codename1.ui.Component.RIGHT;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.RadioButton;
import com.codename1.ui.Tabs;
import com.codename1.ui.TextArea;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.entities.Categorie;
import com.mycompany.myapp.entities.Task;
import com.mycompany.myapp.services.ServiceCat;
import com.mycompany.myapp.services.ServiceTask;
 
import java.util.ArrayList;
import java.util.Vector;

/**
 *
 * @author sarra
 */
public class ProduitFormBack extends BaseForm {

   
ArrayList<Categorie> listcat = new ArrayList<Categorie>();
  ArrayList<Categorie> cats;
    String catt;
    public ProduitFormBack(Resources res) {
        super("Blog", BoxLayout.y());

        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("Les blogs");
        getContentPane().setScrollVisible(false);

        super.addSideMenu(res); 
        tb.addSearchCommand(e -> {
        });

        Tabs swipe = new Tabs();
  
        Label spacer1 = new Label();
        Label spacer2 = new Label();
      //  addTab(swipe, res.getImage("n-routinesport.jpg"), spacer1, "15 Likes  ", "85 Comments", "Integer ut placerat purued non dignissim neque. ");
        addTab(swipe, res.getImage("shop.jpg"), spacer2, "100 Likes  ", "66 Comments", "Dogs are cute: story at 11");

        swipe.setUIID("Container");
        swipe.getContentPane().setUIID("Container");
        swipe.hideTabs();

        ButtonGroup bg = new ButtonGroup();
        int size = Display.getInstance().convertToPixels(1);
        Image unselectedWalkthru = Image.createImage(size, size, 0);
        Graphics g = unselectedWalkthru.getGraphics();
        g.setColor(0xffffff);
        g.setAlpha(100);
        g.setAntiAliased(true);
        g.fillArc(0, 0, size, size, 0, 360);
        Image selectedWalkthru = Image.createImage(size, size, 0);
        g = selectedWalkthru.getGraphics();
        g.setColor(0xffffff);
        g.setAntiAliased(true);
        g.fillArc(0, 0, size, size, 0, 360);
        RadioButton[] rbs = new RadioButton[swipe.getTabCount()];
        FlowLayout flow = new FlowLayout(CENTER);
        flow.setValign(BOTTOM);
        Container radioContainer = new Container(flow);
        for (int iter = 0; iter < rbs.length; iter++) {
            rbs[iter] = RadioButton.createToggle(unselectedWalkthru, bg);
            rbs[iter].setPressedIcon(selectedWalkthru);
            rbs[iter].setUIID("Label");
            radioContainer.add(rbs[iter]);
        }

        rbs[0].setSelected(true);
        swipe.addSelectionListener((i, ii) -> {
            if (!rbs[ii].isSelected()) {
                rbs[ii].setSelected(true);
            }
        });

        Component.setSameSize(radioContainer, spacer1, spacer2);
        add(LayeredLayout.encloseIn(swipe, radioContainer));

        ButtonGroup barGroup = new ButtonGroup();
        RadioButton all = RadioButton.createToggle("blog Back", barGroup);
        all.setUIID("SelectBar");

        Label arrow = new Label(res.getImage("news-tab-down-arrow.png"), "Container");

        add(LayeredLayout.encloseIn(
                GridLayout.encloseIn(1, all),
                FlowLayout.encloseBottom(arrow)
        ));


        all.setSelected(true);
        arrow.setVisible(false);
        addShowListener(e -> {
            arrow.setVisible(true);
            updateArrowPosition(all, arrow);
        });
        bindButtonSelection(all, arrow);
        //   bindButtonSelection(featured, arrow);
       
        // special case for rotation
        addOrientationListener(e -> {
            updateArrowPosition(barGroup.getRadioButton(barGroup.getSelectedIndex()), arrow);
        });

        ArrayList<Task> en = ServiceTask.getInstance().getAllTasks();

        Display.getInstance().scheduleBackgroundTask(() -> {

            Display.getInstance().callSerially(() -> {
                for (Task eyy : en) {
                    //MultiButton m = new MultiButton();
                    addButton(eyy.getImage(), eyy.getNom(), res, eyy.getDescri(), eyy.getId(),eyy,eyy.getIdcat());
                    //  contenu = eyy.getContenu();
                    //titre = eyy.getTitre();
                    //   System.out.println(eyy.getId());
                    
                }
            });
        });
 Container round = new Container();
        FloatingActionButton fab = FloatingActionButton.createFAB(FontImage.MATERIAL_ADD);
        FlowLayout flows = new FlowLayout(CENTER);
        flows.setValign(CENTER);
        round.setLayout(flows);
        round.add(fab);
        super.add(round);
        fab.addActionListener(e -> {
           new addProduitFormBack(res).show();
        });
        /*  addButton(res.getImage("news-item-2.jpg"), titre, true, 15, 21);
        addButton(res.getImage("news-item-3.jpg"), titre, false, 36, 15);
        addButton(res.getImage("news-item-4.jpg"), titre, false, 11, 9);*/
    }

    private void updateArrowPosition(Button b, Label arrow) {
        arrow.getUnselectedStyle().setMargin(LEFT, b.getX() + b.getWidth() / 2 - arrow.getWidth() / 2);
        arrow.getParent().repaint();

    }

    private void addTab(Tabs swipe, Image img, Label spacer, String likesStr, String commentsStr, String text) {
        int size = Math.min(Display.getInstance().getDisplayWidth(), Display.getInstance().getDisplayHeight());
        if (img.getHeight() < size) {
            img = img.scaledHeight(size);
        }
        

        Label comments = new Label(commentsStr);
        FontImage.setMaterialIcon(comments, FontImage.MATERIAL_CHAT);
        if (img.getHeight() > Display.getInstance().getDisplayHeight() / 2) {
            img = img.scaledHeight(Display.getInstance().getDisplayHeight() / 2);
        }
        ScaleImageLabel image = new ScaleImageLabel(img);
        image.setUIID("Container");
        image.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);
        Label overlay = new Label(" ", "ImageOverlay");

        Container page1
                = LayeredLayout.encloseIn(
                        image,
                        overlay,
                        BorderLayout.south(
                                BoxLayout.encloseY(
                                        spacer
                                )
                        )
                );

        swipe.addTab("", page1);
    }

      
    private void addButton(String imge, String nom, Resources res, String description, float id,Task t,int idcat) {
        
        
        /////caaattttttttttttttttttttttttttttttttttttttttttt
        cats= ServiceCat.getInstance().getAllCat();
            System.out.println(cats);
           
            System.out.println(ServiceCat.getInstance().taillecat());
            for(Categorie c: cats) {
                Label nomn=new Label(c.getNom());
              
                System.out.println(c.getNom());
                //of=c.getNom();
               listcat.add(c);
            }
          
            for(Categorie c: cats) {
                if(c.getId()==idcat){
                    catt=c.getNom();
                }
            }
             
            
         EncodedImage placeholder = EncodedImage.createFromImage(Image.createImage(300, 300, 0xffff0000), true);
URLImage img = URLImage.createToStorage(placeholder,imge, "");
       
        int height = Display.getInstance().convertToPixels(11.5f);
        int width = Display.getInstance().convertToPixels(14f);
        Button image = new Button(img.fill(width, height));
        image.setUIID("Label");
        Container cnt = BorderLayout.west(image);
        cnt.setLeadComponent(image);
       
         Label Nom = new Label("Nom de blog :"+nom);
        Nom.setUIID("NewsTopLine");
        Label Description = new Label("Description de blog :"+description);
        
        Description.setUIID("NewsTopLine");
        
          Label cat = new Label("La categorie :"+catt);
        
        cat.setUIID("NewsTopLine");
        cnt.add(BorderLayout.CENTER,
                BoxLayout.encloseY(
                        Nom,Description,cat
                ));
        add(cnt);
        image.addActionListener(e -> {

            UpdateProduitBack a = new UpdateProduitBack(res, nom, description, id,t);
               System.out.println(id);
            a.show();
        });
    }

    private void bindButtonSelection(Button b, Label arrow) {
        b.addActionListener(e -> {
            if (b.isSelected()) {
                updateArrowPosition(b, arrow);
            }
        });
    }

}