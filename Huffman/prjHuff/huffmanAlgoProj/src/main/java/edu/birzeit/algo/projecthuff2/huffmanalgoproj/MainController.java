package edu.birzeit.algo.projecthuff2.huffmanalgoproj;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController  implements Initializable {

    private double x = 0;
    private double y = 0;
    @FXML
    private TextField filePathTextField;

    @FXML
    private TableColumn<Code, Integer> position;

    @FXML
    private TableColumn<Code, String> character;
    @FXML
    private TableColumn<Code, Integer> charfreq;

    @FXML
    private TableColumn<Code, String> huffmancode;

    @FXML
    private TableColumn<Code, Integer> codelength;
    @FXML
    private TableView<Code> huffmanTable;

    @FXML
    private Button fileToCompressID;

    @FXML
    private Button compressBtnID;

    @FXML
    private Button decompressBtnID;

    @FXML
    private ImageView statisticBtnID;
    public static DialogBox alertDialog = new DialogBox();

    static int[] rep = new int[256];
    // static String outCode;
    static String fileName;
    static String fileEnd;
    static String filePath;
    static File in;
    static WriteData write1;
    private ObservableList<Code> statsList ;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        position.setCellValueFactory(new PropertyValueFactory<>("position"));
        position.setCellValueFactory(column -> new ReadOnlyObjectWrapper<>(huffmanTable.getItems().indexOf(column.getValue()) + 1));
        character.setCellValueFactory(new PropertyValueFactory<>("character"));
        charfreq.setCellValueFactory(new PropertyValueFactory<>("charFreq"));
        huffmancode.setCellValueFactory(new PropertyValueFactory<>("huffmanCode"));
        codelength.setCellValueFactory(new PropertyValueFactory<>("codeLength"));
        statsList = FXCollections.observableArrayList();

    }

    @FXML
    void statusBtn(MouseEvent event) {

        Stage statStage = new Stage();
        VBox statusBox = new VBox();
        VBox viewHolder = new VBox();
        Label CompressionStatsLabel = new Label("Compression Statistics");
        CompressionStatsLabel.setFont(Font.font("System", FontWeight.BOLD,25));
        VBox.setMargin(CompressionStatsLabel,new Insets(5,0,0,0));
        viewHolder.setSpacing(5);
        viewHolder.setAlignment(Pos.TOP_CENTER);

        if (write1 != null) {
            statusBox.getChildren().add(new Label("1. Size Before Compression : "+write1.statistics.get(0)  +" Bits"));
            statusBox.getChildren().add(new Label("2. Header Size : "+write1.statistics.get(1) +" Bits"));
            statusBox.getChildren().add(new Label("3. Size After Compression : "+write1.statistics.get(2)  +" Bits"));
            statusBox.getChildren().add(new Label("4. Compression Ratio : "+(write1.statistics.get(3) * 100.0) + " % "));

            statusBox.setAlignment(Pos.TOP_CENTER);
            statusBox.setPadding(new Insets(5));
            statusBox.setSpacing(10);


            viewHolder.getChildren().addAll(CompressionStatsLabel,statusBox,write1.area);
            statStage.setScene(new Scene(viewHolder,500,350));
            statStage.initModality(Modality.APPLICATION_MODAL);
            statStage.setTitle("Statistics");
            statStage.show();

        }else
            alertDialog.displayPopUp("Sorry but no file is selected to plot statistics","Warning !",2);

    }

    @FXML
    void draggedScene(MouseEvent event) {

        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();

        stage.setX(event.getScreenX() - x);
        stage.setY(event.getScreenY() - y);

    }

    @FXML
    void pressedOnScene(MouseEvent event) {

        x = event.getSceneX();
        y = event.getSceneY();
    }

    @FXML
    void exitBtnClicked(ActionEvent event) {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
    }

    @FXML
    void chooseFileBtnClicked(ActionEvent event) {

        readOriginFileName();

    }


    @FXML
    void compressFileBtnClicked(ActionEvent event) {

        decompressBtnID.setDisable(false);
        startCompress();
    }

    @FXML
    void deCompressFileBtnClicked(ActionEvent event) {
        fileToCompressID.setDisable(true);
        startDecompress();
    }


     void readOriginFileName() {
        FileChooser fileChooser = new FileChooser();
         fileChooser.setTitle("Open Resource File");

        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Files", "*.txt","*.png",
                                                                                "*.jpg","*.jpeg","*.java","*.pdf","*.rar",
                                                                                "*.zip","*.iso","*.mp4","*.mp3","*.pptx","*.docx",
                                                                                "*.csv","*.xlsx","*.svg","*.exe","*.unitypackage","*.blend",
                                                                                "*.dll","*.js","*.class","*.html","*.css","*.r","*.lua",
                                                                                "*.dll.a",".sh","*.ico","*.bat","*.c","*.cmd","*.ini",
                                                                                "*.md","*.php","*.xml","*.fxml","*.py","*.pyc","*.sys","*.log"));



         in = fileChooser.showOpenDialog(null);
        if (in != null) {
            fileName = in.getName();
            int ind = in.getPath().lastIndexOf(".");
            filePath = in.getPath().substring(0, ind) + ".huff";
            int index = fileName.lastIndexOf(".");

            fileEnd = fileName.substring(index + 1);
            filePathTextField.setText(in.getPath());

            compressBtnID.setDisable(false);
            statisticBtnID.setDisable(false);

        }
        else
            alertDialog.displayPopUp("Please Choose a file ","File Not Found",2);

    }

     void startCompress() {
        try {
            ReadFile read = new ReadFile();
            if (in != null) {
                read.readFile(rep, in);
                HuffmanCode encode = new HuffmanCode();
                encode.generateCodes(rep);
                write1 = new WriteData();
                statsList = write1.compress(encode.codes, rep, in, filePath, fileEnd);
                huffmanTable.setItems(statsList);
            }else {
                alertDialog.displayPopUp("Please Choose a File First", "Warning !", 2);
                filePathTextField.setText("");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    void startDecompress() {
        try {
            readCompressedFile();
            WriteData write = new WriteData();
            if (in !=null )
                write.decompress(in, filePath, fileName);
            else
                alertDialog.displayPopUp("Please Choose a compressed File to Decompress","Warning !",2);

        } catch (Exception e) {
            e.printStackTrace();        }
    }

     void readCompressedFile() {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
         fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Files", "*.huff"));
         in = fileChooser.showOpenDialog(null);
         System.out.println(in);
        if (in != null) {
            filePathTextField.setText(in.getPath());
            fileName = in.getName();
            int ind = fileName.lastIndexOf(".");
            fileName = fileName.substring(0, ind);
            int index = in.getPath().lastIndexOf(".");
            filePath = in.getPath().substring(0, index) + "-new.";
            filePathTextField.setText(in.getPath());
        }else{
            alertDialog.displayPopUp("Please Choose File to Decompress","File Not Located",2);
            fileToCompressID.setDisable(false);
            filePathTextField.setText("");

        }

    }


}