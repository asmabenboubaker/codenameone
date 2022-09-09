

/*
 * Copyright (c) 2016, Codename One
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software,
 * and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions
 * of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.mycompany.myapp.gui;

import com.codename1.capture.Capture;
import com.codename1.components.CheckBoxList;
import com.codename1.components.FloatingHint;
import com.codename1.components.InfiniteProgress;
import com.codename1.components.ScaleImageLabel;
import com.codename1.components.SwitchList;
import com.codename1.io.FileSystemStorage;
import com.codename1.io.Log;
import com.codename1.ui.Button;
import com.codename1.ui.CheckBox;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.list.DefaultListModel;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.ImageIO;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.entities.Categorie;
import com.mycompany.myapp.entities.Task;
import com.mycompany.myapp.services.ServiceCat;
import com.mycompany.myapp.services.ServiceTask;

import utils.FileChooser.FileChooser;


import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import utils.FileChooser.FileChooser;

/**
 * The user profile form
 *
 * @author sarra
 */
public class addProduitFormBack1 extends BaseForm {
    int indice;
    ArrayList<Categorie> cats;
ArrayList<Categorie> listcat = new ArrayList<Categorie>();
    private static String i;
private String imageName,of;
    public addProduitFormBack1(Resources res) {
        super("add blog", BoxLayout.y());
        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("Add blog");
        getContentPane().setScrollVisible(false);
        Form previous = Display.getInstance().getCurrent();
        tb.setBackCommand("", e -> previous.showBack());
        super.addSideMenu(res);

        Image img = res.getImage("profile-background.jpg");
        if (img.getHeight() > Display.getInstance().getDisplayHeight() / 3) {
            img = img.scaledHeight(Display.getInstance().getDisplayHeight() / 3);
        }
        ScaleImageLabel sl = new ScaleImageLabel(img);
        sl.setUIID("BottomPad");
        sl.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);

        Button btnValider = new Button("Valider");
//Label pp= new Label(ServiceUser.UriImage(SessionManager.getPhoto()),"PictureWhiteBackground");
        add(LayeredLayout.encloseIn(sl, BorderLayout.south(GridLayout.encloseIn(3, FlowLayout.encloseCenter()))));

        TextField nom = new TextField();
        nom.setUIID("TextFieldBlack");
        addStringValue("Nom", nom);

        
          btnValider.setUIID("Valider");
        addStringValue("", btnValider);
      
/* annuler.addActionListener((ActionEvent e)->{
      new MainUI(res).show();
 });*/
  

        btnValider.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                if ((nom.getText().length() == 0) ) {
                    Dialog.show("Alert", "Please fill all the fields", new Command("OK"));
                } else {
                    try {
                    // float p = Float.parseFloat(prix.getText());
                    // float s = Float.parseFloat(stock.getText());
 
                        Categorie t = new Categorie(nom.getText());
                         
                        if (ServiceCat.getInstance().addCat(t)) {
                            Dialog.show("Success", "Connection accepted", new Command("OK"));
                           new ProduitFormBack1(res).show();
                            refreshTheme();

                        } else {
                            Dialog.show("ERROR", "Server error", new Command("OK"));
                        }
                    } catch (NumberFormatException e) {
                        Dialog.show("ERROR", "Status must be a number", new Command("OK"));
                    }

                }

            }
        });
         
 
  
    }
 protected String saveFileToDevice(String hi) throws IOException {
        int index = hi.lastIndexOf("/");
        hi = hi.substring(index + 1);
        return hi;

    }
    private void addStringValue(String s, Component v) {
        add(BorderLayout.west(new Label(s, "PaddedLabel")).
                add(BorderLayout.CENTER, v));
        add(createLineSeparator(0xeeeeee));
        
    }
}