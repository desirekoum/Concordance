package com.desire;

import com.desire.ConcordanceDataManager;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;

import javax.swing.*;
import java.io.File;
import java.util.Scanner;

/**
 * This is the main UI of the Arithmetic notation application
 * It provides the interface for interacts between users and the system.
 * it collects data form user and displays results.
 * @author Ngantchu
 * @param <ButtonListener>
 *
 */
public class GuiFX extends BorderPane{

    private Button btnCreateConcordance, btnSelectInputFile, btnSelectOutputFile,  btnClear, btnExit;
    private TextArea txtText;
    private HBox bottomBox;
    private RadioButton fromFile, fromText;
    private final ToggleGroup radioGroup;
    private File inputFile, outputFile;


    public GuiFX() {
        // TODO Auto-generated constructor stub
        ButtonListener btnlistener = new ButtonListener();
        Insets inset = new Insets(10);
        HBox vBox = new HBox(10);
        fromFile = new RadioButton("Create Concordance for File");
        fromText = new RadioButton("Create Concordance for Text");
        radioGroup = new ToggleGroup();
        vBox.getChildren().addAll(fromFile, fromText);

        fromFile.setToggleGroup(radioGroup);
        fromText.setToggleGroup(radioGroup);

        //Manage toggle group of radio
        radioGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
            public void changed(ObservableValue<? extends Toggle> ov,
                                Toggle old_toggle, Toggle new_toggle) {
                if (radioGroup.getSelectedToggle() == fromFile)
                {
                    //
                    txtText.setVisible(true);
                    btnSelectInputFile.setDisable(false);
                }
                if (radioGroup.getSelectedToggle() == fromText)
                {
                    //
                    txtText.setVisible(true);
                    btnCreateConcordance.setDisable(false);
                }
            }
        });

        TitledPane SelectionPane = new TitledPane();
        SelectionPane.setCollapsible(false);
        SelectionPane.setVisible(true);
        SelectionPane.setPrefHeight(70);
        SelectionPane.setText("Select an Option");
        //Adding controls inside the  Pane

        HBox SelectionForm = new HBox(15);
        SelectionForm.getChildren().addAll(vBox);
        SelectionPane.setContent(SelectionForm);
        setTop(SelectionPane);

        TitledPane TextPane = new TitledPane();
        TextPane.setCollapsible(false);
        TextPane.setVisible(true);
        TextPane.setPrefHeight(400);

        TextPane.setText("Enter text");
        //creating and Adding form's ui controls
        txtText = new TextArea();
        txtText.setPrefHeight(650);
        txtText.setPrefWidth(600);
        txtText.setVisible(false);


        HBox TextForm = new HBox(510);
        TextForm.getChildren().addAll(txtText);
        TextPane.setContent(TextForm);
        setCenter(TextPane);
        btnCreateConcordance = new Button("Create concordance");
        btnCreateConcordance.setOnAction(btnlistener);
        btnCreateConcordance.setDisable(true);

        btnSelectInputFile = new Button("Select Input File");
        btnSelectInputFile.setOnAction(btnlistener);
        btnSelectInputFile.setDisable(true);

        btnSelectOutputFile = new Button("Select Output File");
        btnSelectOutputFile.setOnAction(btnlistener);
        btnSelectOutputFile.setDisable(true);

        btnClear = new Button("Clear");
        btnClear.setOnAction(btnlistener);

        btnExit = new Button("Exit");
        btnExit.setOnAction(btnlistener);
        HBox bottomBox = new HBox(10);
        bottomBox.getChildren().addAll(btnCreateConcordance, btnSelectInputFile,  btnSelectOutputFile, btnClear, btnExit);
        setBottom(bottomBox);
        bottomBox.setAlignment(Pos.CENTER);


    }

    //Definition of inner class to handle radio button events
    private class ButtonListener implements EventHandler<ActionEvent>
    {
        @Override
        public void handle(ActionEvent event)
        {
            Object source = event.getTarget();
            if (source == btnCreateConcordance)
            {
                ConcordanceDataManager dm = new ConcordanceDataManager();
                if(txtText.getText()!=null || txtText.getText()!="")
                {
                    String inputText = txtText.getText();
                    String result = "";
                    for(String word: dm.createConcordanceArray(inputText)) {
                        result += word+"\n";
                    }
                    txtText.setText(result);//[ac,kj,jh,]
                }
                else
                    JOptionPane.showMessageDialog(null, "You must enter a text into the the text area");
                if(radioGroup.getSelectedToggle()==fromFile)
                {
                    try
                    {
                        if(dm.createConcordanceFile(inputFile, outputFile))
                        {
                            JOptionPane.showMessageDialog(null, "Concordance file was created");
                            Scanner sc = new Scanner(outputFile);
                            String result = "";
                            while(sc.hasNextLine())
                            {
                                String currentLine = sc.nextLine();
                                result +=currentLine+"\n";
                                System.out.println(currentLine);
                            }
                            txtText.setText(result);
                            sc.close();
                        }
                    }
                    catch(java.io.IOException fnfe)
                    {
                        fnfe.getMessage();
                    }
                }

            }
            else if (source == btnSelectInputFile)
            {
                FileChooser fileChooser = new FileChooser();
                File selectedFile = fileChooser.showOpenDialog(null);
                if (selectedFile == null)
                {
                    JOptionPane.showMessageDialog(null, "Empty field !!!  \nYou must select a file");
                }
                else
                {
                    btnSelectOutputFile.setDisable(false);
                    inputFile = selectedFile;
                }

            }

            else if (source == btnSelectOutputFile)
            {
                FileChooser fileChooser = new FileChooser();
                File selectedFile = fileChooser.showOpenDialog(null);
                if (selectedFile == null)
                {
                    JOptionPane.showMessageDialog(null, "Empty field !!!  \nYou must select a file");
                }
                else
                {
                    btnCreateConcordance.setDisable(false);
                    outputFile = selectedFile;
                }

            }

            else if (source == btnClear)
            {
                txtText.setText("");
            }
            else if(source == btnExit )//Quit the application
            {
                System.exit(0);
            }

        }
    }
}
