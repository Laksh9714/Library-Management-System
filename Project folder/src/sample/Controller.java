package sample;

import com.mysql.cj.TransactionEventHandler;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;

public class Controller implements Initializable{
    //Includes all the components in the ui design used for binding
    public TextArea destxt;
    public CheckBox fictioncheck;
    public TextField isbntxt;
    public TextField lcctxt;
    public TextField noptxt;
    public TextField sectxt;
    public TextField subtxt;
    public TextField anametxt;
    public TextField ddstxt;
    public TextField libidtxt;
    public TextField staffinctxt;
    public TextField cattxt;
    public DatePicker pdatedate;
    public TextField btitletxt;
    public Label statuslb;
    public TextField maxfinetxt;
    public TextField noctxt;
    public TextField edittxt;
    public RadioButton ficy;
    public RadioButton ficn;
    public ToggleGroup fictog;
    public ToggleGroup ebooktog;
    public RadioButton ebooky;
    public RadioButton ebookn;
    public ChoiceBox pubchoice;
    public Button save;
    public Button updatebtn;


    String dbName="library_system";
    String userName="root";
    String password="";
    String libid="",staffinc="",section="",publisher="",lcc="",dds="",b_title="",cat="",desc="",sub="";
    String []anamelist=new String[100];
    LocalDate pdate;
    String edition,maxfine,noc,isbn,nop;//for int type data
    String fiction="",ebook="";
    boolean anameflag;
    CallableStatement st;

    //to add data to our tableview in ui
    public TableView <Modeltable> tablev;
    public TableColumn <Modeltable,String> isbnt;
    public TableColumn <Modeltable,String> libidt;
    public TableColumn <Modeltable,String> titlet;
    public TableColumn <Modeltable,String> subt;
    public TableColumn <Modeltable,String> anamet;
    public TableColumn <Modeltable,String> editt;
    public TableColumn <Modeltable,String> ebookt;
    public TableColumn <Modeltable,String> pdatet;
    public TableColumn <Modeltable,String> fictiont;
    public TableColumn <Modeltable,String> ddst;
    public TableColumn <Modeltable,String> lcct;
    public TableColumn <Modeltable,String> nopt;
    public TableColumn <Modeltable,String> pubt;
    public TableColumn <Modeltable,String> sect;
    public TableColumn <Modeltable,String> catt;
    public TableColumn <Modeltable,String> staffinct;
    public TableColumn <Modeltable,String> noct;
    public TableColumn <Modeltable,String> maxt;
    public TableColumn <Modeltable,String> desct;

    ObservableList<Modeltable> ob= FXCollections.observableArrayList();//Creating object for fetching and displaying data in table

    //Will perform search operation with data in fields or provide the whole database if no fields are added
    public void search(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        tablev.getItems().clear();

        isbnt.setCellValueFactory(new PropertyValueFactory<>("isbnv"));
        libidt.setCellValueFactory(new PropertyValueFactory<>("libidv"));
        titlet.setCellValueFactory(new PropertyValueFactory<>("titlev"));
        subt.setCellValueFactory(new PropertyValueFactory<>("subv"));
        anamet.setCellValueFactory(new PropertyValueFactory<>("anamev"));
        editt.setCellValueFactory(new PropertyValueFactory<>("editv"));
        ebookt.setCellValueFactory(new PropertyValueFactory<>("ebookv"));
        pdatet.setCellValueFactory(new PropertyValueFactory<>("pdatev"));
        fictiont.setCellValueFactory(new PropertyValueFactory<>("fictionv"));
        ddst.setCellValueFactory(new PropertyValueFactory<>("ddsv"));
        lcct.setCellValueFactory(new PropertyValueFactory<>("lccv"));
        nopt.setCellValueFactory(new PropertyValueFactory<>("nopv"));
        pubt.setCellValueFactory(new PropertyValueFactory<>("pubv"));
        sect.setCellValueFactory(new PropertyValueFactory<>("secv"));
        catt.setCellValueFactory(new PropertyValueFactory<>("catv"));
        staffinct.setCellValueFactory(new PropertyValueFactory<>("staffincv"));
        noct.setCellValueFactory(new PropertyValueFactory<>("nocv"));
        maxt.setCellValueFactory(new PropertyValueFactory<>("maxv"));
        desct.setCellValueFactory(new PropertyValueFactory<>("descv"));


        //ISBN
        isbn = isbntxt.getText();

        //FICTION
        ficy.setToggleGroup(fictog);
        ficn.setToggleGroup(fictog);
        if (ficy.isSelected()) {
            fiction = "1";
        }
        else if(ficn.isSelected())
        {
            fiction = "0";
        }
        else
        {
            fiction="%%";//for no change
        }

        //DDS
        dds = ddstxt.getText();

        //NOP
        nop=noptxt.getText();

        //NOC
        noc=noctxt.getText();

        //MAX FINE
        maxfine=maxfinetxt.getText();

        //DATE
        pdate=pdatedate.getValue();

        //Edition
        edition=edittxt.getText();

        //LIB ID
        libid = libidtxt.getText();

        //BOOK TITLE
        b_title=btitletxt.getText();

        //SUBJECT
        sub=subtxt.getText();

        //AUTHOR NAME
        String aname=anametxt.getText();
        anamelist = aname.split(";", 10);//fetching all authors from field

        //LCC
        lcc=lcctxt.getText();

        //PUBLISHER
        publisher=pubchoice.getValue().toString();

        //SECTION
        section = sectxt.getText();

        //CATEGORY
        cat=cattxt.getText();

        //STAFF INC
        staffinc=staffinctxt.getText();

        //DESCRIPTION
        desc=destxt.getText();


        //EBOOK
        ebooky.setToggleGroup(ebooktog);
        ebookn.setToggleGroup(ebooktog);
        if(ebooky.isSelected())
        {
            ebook="1";
        }
        else if(ebookn.isSelected()) {
            ebook="0";
        }
        else
        {
            ebook="%%";
        }

        //for clearing dds incase of fictional book
        if(fiction=="1")
        {
            dds="%%";
        }
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection= DriverManager.getConnection("jdbc:mysql://localhost:3306/"+dbName,userName,password);
        try
        {
            if(pdate==null)//as the date cannot be set to empty, so either of two queries will be executed
            {
                st=connection.prepareCall("Select i.lib_id,i.no_of_copies,i.section,i.category,i.staff_inc,i.max_fine,b.isbn,b.b_title,b.b_description,b.subject,b.b_pub_date,b.b_publisher_name,b.edition,b.isfiction,b.dds,b.isebook,b.no_of_pages,b.lcc,a.b_author_name from books b,inventory i,b_author a where a.fk_isbn=b.isbn and i.lib_id=b.fk_lib_id and b.isbn like '%"+isbn+"%' and i.lib_id like '%"+libid+"%' and b.edition like '%"+edition+"%' and b.b_title like '%"+b_title+"%' and b.subject like '%"+sub+"%' and b.no_of_pages like '%"+nop+"%'  and b.lcc like '%"+lcc+"%' and a.b_author_name like '%"+aname+"%' and i.no_of_copies like '%"+noc+"%' and i.section like '%"+section+"%' and i.category like '%"+cat+"%' and i.staff_inc like '%"+staffinc+"%' and i.max_fine like '%"+maxfine+"%'and b.dds like '%"+dds+"%' and b.isfiction like '%"+fiction+"%' and b.isebook like '%"+ebook+"%'");
            }
            else {
                st=connection.prepareCall("Select i.lib_id,i.no_of_copies,i.section,i.category,i.staff_inc,i.max_fine,b.isbn,b.b_title,b.b_description,b.subject,b.b_pub_date,b.b_publisher_name,b.edition,b.isfiction,b.dds,b.isebook,b.no_of_pages,b.lcc,a.b_author_name from books b,inventory i,b_author a where a.fk_isbn=b.isbn and i.lib_id=b.fk_lib_id and b.isbn like '%" + isbn + "%' and i.lib_id like '%" + libid + "%' and b.edition like '%" + edition + "%' and b.b_title like '%" + b_title + "%' and b.subject like '%" + sub + "%' and b.b_pub_date like '%" + pdate + "%' and b.no_of_pages like '%" + nop + "%'  and b.lcc like '%" + lcc + "%' and a.b_author_name like '%" + aname + "%' and i.no_of_copies like '%" + noc + "%' and i.section like '%" + section + "%' and i.category like '%" + cat + "%' and i.staff_inc like '%" + staffinc + "%' and i.max_fine like '%" + maxfine + "%'and b.dds like '%" + dds + "%' and b.isfiction like '%" + fiction + "%' and b.isebook like '%"+ebook+"%'");
            }
            ResultSet rs=st.executeQuery();
            while(rs.next())
            {
                ob.add(new Modeltable(rs.getString("isbn"),rs.getString("lib_id"),rs.getString("b_title"),rs.getString("subject"),rs.getString("b_author_name"),rs.getString("edition"),rs.getString("isebook"),rs.getString("b_pub_date"),rs.getString("isfiction"),rs.getString("dds"),rs.getString("lcc"),rs.getString("no_of_pages"),rs.getString("b_publisher_name"),rs.getString("section"),rs.getString("category"),rs.getString("staff_inc"),rs.getString("no_of_copies"),rs.getString("max_fine"),rs.getString("b_description")));
            }
            tablev.setItems(ob);//populating table
            st.close();
            rs.close();
        }
        catch (Exception e)
        {
            statuslb.setText("Error detected");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("SQL error!");
            alert.setContentText(e.toString());
            alert.showAndWait();
        }
        statuslb.setText("Search applied");
        tablev.setEditable(true);
        //To update the entry in database, a editable table is made
        tablev.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(mouseEvent.getClickCount()==2)
                {
                    updatebtn.setDisable(false);
                    TablePosition pos=tablev.getSelectionModel().getSelectedCells().get(0);
                    int row=pos.getRow();
                    Modeltable tablerow=tablev.getItems().get(row);
                    isbntxt.setText(tablerow.isbnv);
                    libidtxt.setText(tablerow.libidv);
                    btitletxt.setText(tablerow.titlev);
                    subtxt.setText(tablerow.subv);
                    edittxt.setText(tablerow.editv);
                    ddstxt.setText(tablerow.ddsv);
                    if(tablerow.fictionv.equals("1"))
                    {
                        fictog.selectToggle(ficy);
                        ddstxt.setDisable(true);
                    }
                    else
                    {
                        fictog.selectToggle(ficn);
                    }
                    if(tablerow.ebookv.equals("1"))
                    {
                        ebooktog.selectToggle(ebooky);
                    }
                    else
                    {
                        ebooktog.selectToggle(ebookn);
                    }
                    pdatedate.setValue(LocalDate.parse(tablerow.pdatev));
                    lcctxt.setText(tablerow.lccv);
                    noptxt.setText(tablerow.nopv);
                    pubchoice.setValue(tablerow.pubv);
                    sectxt.setText(tablerow.secv);
                    cattxt.setText(tablerow.catv);
                    staffinctxt.setText(tablerow.staffincv);
                    noctxt.setText(tablerow.nocv);
                    destxt.setText(tablerow.descv);
                    maxfinetxt.setText(tablerow.maxv);
                    anametxt.setText(tablerow.anamev);

                    //to record any change in author name I added a listener in case any edition occurs
                    anametxt.textProperty().addListener(new ChangeListener<String>() {
                        @Override
                        public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                            anameflag=true;
                        }
                    });
                }
              //save.setDisable(true);//disable save button incase of update

            }
        });
    }


//This method will clear all the data entries and the table
    public void clear(ActionEvent actionEvent) {

        save.setDisable(false);
        updatebtn.setDisable(true);
        isbntxt.setText("");
        libidtxt.setText("");
        btitletxt.setText("");
        subtxt.setText("");
        anametxt.setText("");
        edittxt.setText("");
        ficy.setSelected(false);
        ficn.setSelected(false);
        ddstxt.setDisable(false);
        ebooky.setSelected(false);
        ebookn.setSelected(false);
        pdatedate.setValue(null);
        ddstxt.setText("");
        lcctxt.setText("");
        noptxt.setText("");
        pubchoice.setValue("SUNRISE");
        sectxt.setText("");
        cattxt.setText("");
        staffinctxt.setText("");
        maxfinetxt.setText("");
        destxt.setText("");
        noctxt.setText("");
        tablev.getItems().clear();

        statuslb.setText("Fields have been cleared");
    }
//This method will insert data into our database according to the entries provided.It will be deactivated incase of update operation
    public void add(ActionEvent actionEvent) throws ClassNotFoundException, SQLException {

        String errormsg = "";
        Boolean flag = true;//to detect any data insertion error

        //ISBN
        if (isbntxt.getText().isBlank()) {
            errormsg = errormsg.concat("isbn cannot be empty.\n");
            flag = false;
        }
        else {
            if (!(isbntxt.getText()).matches("[0-9]+") || isbntxt.getLength()!=13) {
                errormsg = errormsg.concat("isbn cannot contain characters and must be 13 digits.\n");
                flag = false;
            } else {
                 isbn =(isbntxt.getText());

            }
        }

        //FICTION
        if (ficy.isSelected()) {
             fiction = "1";

        } else if(ficn.isSelected()) {
             fiction = "0";
        }

        //DDS
        if (ficn.isSelected()) {
            if (ddstxt.getText().isBlank()) {
                errormsg = errormsg.concat("dds cannot be empty if book dsn't belong to fiction.\n");
                flag = false;
            } else {
                if (!(ddstxt.getText()).matches("[0-9,.]+")) {
                    errormsg = errormsg.concat("dds cannot contain characters.\n");
                    flag = false;
                } else {
                     dds = ddstxt.getText();
                }
            }
        }
        else
        {
            dds="NULL";
        }

        //NOP
        if(noptxt.getText().isBlank())
        {
            errormsg = errormsg.concat("number of pages cannot be empty.\n");
            flag=false;
        }
        else{
            if(!(noptxt.getText()).matches("[0-9]+"))
            {
                errormsg = errormsg.concat("number of pages cannot contain characters.\n");
                flag=false;
            }
            else{
                 nop=(noptxt.getText());
            }
        }

        //NOC
        if(noctxt.getText().isBlank())
        {
            errormsg = errormsg.concat("number of copies cannot be empty.\n");
            flag=false;
        }
        else{
            if(!(noctxt.getText()).matches("[0-9]+"))
            {
                errormsg = errormsg.concat("number of copies cannot contain characters.\n");
                flag=false;
            }
            else{
                noc=(noctxt.getText());
            }
        }

        //MAX FINE
        if(maxfinetxt.getText().isBlank())
        {
            errormsg = errormsg.concat("Fine  cannot be empty.\n");
            flag=false;
        }
        else{
            if(!(maxfinetxt.getText()).matches("[0-9]+"))
            {
                errormsg = errormsg.concat("fines cannot contain characters.\n");
                flag=false;
            }
            else{
                 maxfine=maxfinetxt.getText();
            }
        }

        //DATE
        LocalDate current= LocalDate.now();
        LocalDate doi=pdatedate.getValue();
        if(pdatedate.getValue()==null)
        {
            errormsg=errormsg.concat("Publication date cannot be empty.\n");
            flag=false;
        }
        else
        {
            if(doi.compareTo(current)>0)
            {
               errormsg= errormsg.concat("Publication date cannot be in the future.\n");
               flag=false;
            }
            else{
                 pdate=pdatedate.getValue();
            }
        }

        //Edition
        if(edittxt.getText().isBlank())
        {
            errormsg = errormsg.concat("edition  cannot be empty.\n");
            flag=false;
        }
        else{
            if(!(edittxt.getText()).matches("[0-9]+"))
            {
                errormsg = errormsg.concat("edition cannot contain characters.\n");
                flag=false;
            }
            else{
                edition=edittxt.getText();
            }
        }

        //LIB ID
        if(libidtxt.getText().isBlank())
        {
            errormsg=errormsg.concat("Library id cannot be empty.\n");
            flag=false;
        }
        else {
             libid = libidtxt.getText();
        }

        //BOOK TITLE
        if(btitletxt.getText().isBlank())
        {
            errormsg=errormsg.concat("Book title cannot be empty.\n");
            flag=false;
        }
        else {
             b_title=btitletxt.getText();
        }



        //SUBJECT
        if(subtxt.getText().isBlank())
        {
            errormsg=errormsg.concat("Subject of the book cannot be empty.\n");
            flag=false;
        }
        else {
            sub=subtxt.getText();
        }


        //AUTHOR NAME
        if(anametxt.getText().isBlank())
        {
            errormsg=errormsg.concat("Author name cannot be empty.\n");
            flag=false;
        }
        else {
            String aname=anametxt.getText();
            anamelist = aname.split(";", 10);
        }


        //LCC
        if(lcctxt.getText().isBlank())
        {
            errormsg=errormsg.concat("LCC cannot be empty.\n");
            flag=false;
        }
        else {
             lcc=lcctxt.getText();
        }


        //PUBLISHER
        publisher=pubchoice.getValue().toString();


        //SECTION
        if(sectxt.getText().isBlank())
        {
            errormsg = errormsg.concat("Section of the book cannot be empty.\n");
            flag=false;
        }
        else{
            if(  !(sectxt.getText()).matches("[A-Z]+")  )
            {
                errormsg = errormsg.concat("Section cannot contain numbers or characters from a-z.\n");
                flag=false;
            }
            else{
                if(( (sectxt.getText()).length()!=1) )
                {
                    errormsg=errormsg.concat("Section of a book cannot be more than one character.\n");
                    flag=false;
                }
                else {
                     section = sectxt.getText();
                }
            }
        }

        //CATEGORY
        if(cattxt.getText().isBlank())
        {
            errormsg = errormsg.concat("Category of the book cannot be empty.\n");
            flag=false;
        }
        else{
            if(cattxt.getText().matches("[0-9]+"))
            {
                errormsg = errormsg.concat("Category of book cannot contain numbers.\n");
                flag=false;
            }
            else{
                 cat=cattxt.getText();
            }
        }

        //STAFF INC
        if(staffinctxt.getText().isBlank())
        {
            errormsg = errormsg.concat("Staff incharge of that category must exist.\n");
            flag=false;
        }
        else{
            if(staffinctxt.getText().matches("[0-9]+"))
            {
                errormsg = errormsg.concat("Staff incharge name cannot contain numbers.\n");
                flag=false;
            }
            else{
                staffinc=staffinctxt.getText();
            }
        }

        //DESCRIPTION
         desc=destxt.getText();


        //EBOOK
        if(ebooky.isSelected())
        {
             ebook="1";
        }
        else if(ebookn.isSelected()){
             ebook="0";
        }

        //For any data insertion errors
        if(flag==false) {
            statuslb.setText("Error detected");
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Data insertion error!");
            alert.setContentText(errormsg);
            alert.showAndWait();
        }
        else
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection= DriverManager.getConnection("jdbc:mysql://localhost:3306/"+dbName,userName,password);
            try {
                connection.setAutoCommit(false);
                st =  connection.prepareCall("CALL insertinvent('" + libid.toUpperCase() + "'," + noc + ",'" + section.toUpperCase() + "','" + cat.toUpperCase() + "','" + staffinc.toUpperCase() + "'," + maxfine + ")");
                st.executeUpdate();
                st.close();

                st=connection.prepareCall("CALL insertbook('" + isbn + "','" + b_title.toUpperCase() + "','" + desc.toLowerCase() + "','" + sub.toUpperCase() + "','" + pdate + "','" + publisher.toUpperCase() + "'," + edition + "," + fiction + ",'" + dds + "'," + ebook + "," + nop + ",'" + lcc.toUpperCase() + "','" + libid.toUpperCase() + "')");
                st.executeUpdate();
                st.close();


                for (String anames : anamelist) {
                    st = connection.prepareCall("CALL insertauthor('" + isbn + "','" + anames.toUpperCase() + "')");
                    st.executeUpdate();
                    ob.add(new Modeltable(isbn,libid.toUpperCase(),b_title.toUpperCase(),sub.toUpperCase(),anames.toUpperCase(),edition,ebook,pdate.toString(),fiction,dds,lcc.toUpperCase(),nop,publisher.toUpperCase(),section,cat.toUpperCase(),staffinc.toUpperCase(),noc,maxfine,desc.toLowerCase()));
                    st.close();
                }
                connection.commit();
                statuslb.setText("Entry has been added");

            }
            catch (Exception e)
            {
                connection.rollback();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("SQL error!");
                alert.setContentText(e.toString());
                alert.showAndWait();
                statuslb.setText("Error detected");
            }

        }


    }

    //To listen Fiction Selection and disable/enable dds
    public void ddsn(ActionEvent actionEvent) {
        if(ficy.isSelected())
            ddstxt.setDisable(true);
        else if(ficn.isSelected())
            ddstxt.setDisable(false);
    }

    public void ddsy(ActionEvent actionEvent) {
        if(ficy.isSelected())
            ddstxt.setDisable(true);
        else if(ficn.isSelected())
            ddstxt.setDisable(false);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updatebtn.setDisable(true);
        pubchoice.setItems(FXCollections.observableArrayList("SUNRISE","JK","FAIRY","JPANIME","THAKUR","WISEMAN","GOODWILL"));
        pubchoice.setValue("SUNRISE");
        try {
            search(null);//will display the current data in database as no search parameters are passed
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        statuslb.setText("Database Loaded");
    }

//Once you choose an entry in the table it will appear in fields and can be altered according to update in the database
    public void update(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        //ISBN
        isbn = isbntxt.getText();

        //FICTION
         if (ficy.isSelected()) {
            fiction = "1";
        }
        else if(ficn.isSelected())
        {
            fiction = "0";

        }

        //DDS
        dds = ddstxt.getText();

        //NOP
        nop=noptxt.getText();

        //NOC
        noc=noctxt.getText();

        //MAX FINE
        maxfine=maxfinetxt.getText();

        //DATE
        pdate=pdatedate.getValue();

        //Edition
        edition=edittxt.getText();

        //LIB ID
        libid = libidtxt.getText();

        //BOOK TITLE
        b_title=btitletxt.getText();

        //SUBJECT
        sub=subtxt.getText();

        //AUTHOR NAME
        String aname=anametxt.getText();
        anamelist = aname.split(";", 10);

        //LCC
        lcc=lcctxt.getText();

        //PUBLISHER
        publisher=pubchoice.getValue().toString();

        //SECTION
        section = sectxt.getText();

        //Category
        cat=cattxt.getText();

        //STAFF INC
        staffinc=staffinctxt.getText();

        //DESCRIPTION
        desc=destxt.getText();

        //EBOOK
        if(ebooky.isSelected())
        {
            ebook="1";
        }
        else if (ebookn.isSelected()) {
            ebook="0";
        }

        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection= DriverManager.getConnection("jdbc:mysql://localhost:3306/"+dbName,userName,password);
        connection.setAutoCommit(false);
        try
        {

            st=connection.prepareCall("CALL updateinvent( '"+libid+"',"+noc+",'"+section+"','"+cat+"','"+staffinc+"',"+maxfine+")");
            st.executeUpdate();
            st.close();

            st=connection.prepareCall("CALL updatebooks('"+isbn+"', '"+b_title+"','"+desc+"','"+sub+"','"+pdate+"','"+publisher+"',"+edition+","+fiction+",'"+dds+"',"+ebook+","+nop+",'"+lcc+"','"+libid+"')");
            st.executeUpdate();
            st.close();

            if(anameflag) {//Incase authors of a book need to be changed
                st = connection.prepareCall("CALL deleteauthor('" + isbn + "')");
                st.executeUpdate();
                st.close();
                for (String anamef : anamelist) {
                    st = connection.prepareCall("CALL insertauthor('" + isbn + "','" + anamef.toUpperCase() + "')");
                    st.executeUpdate();
                    st.close();
                }
            }
            connection.commit();
        }
        catch (Exception e)
        {
            connection.rollback();
            statuslb.setText("Error detected");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("SQL error!");
            alert.setContentText(e.toString());
            alert.showAndWait();
        }
        statuslb.setText("Update applied. Clear fields");
    }
}



