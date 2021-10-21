import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class GUI implements ActionListener {
    public GridLayout labyrintLayout;
    public JFrame frame;
    public Labyrint labyrint;
    public JPanel labyrintContainer;
    public JPanel labyrintSiden;
    public JLabel rad;
    public JLabel kolonne;
    public JLabel antallUtveier;
    public JLabel kortesteUtveiTittel;
    public JLabel utveiNummer;
    public JTextArea textArea;
    public RuteKnapp[][] alleKnappene;
    public ArrayList<Tuppel> kortesteUtvei;
    public ArrayList<ArrayList<Tuppel>> utveier;
    public Integer valgtVei;
    public Integer kortesteUtveiIndeks;
    public RuteKnapp startRute;

    public GUI(){        
        //Vanlige GUI greier
        frame = new JFrame();
        Dimension vindu = Toolkit.getDefaultToolkit().getScreenSize();
        ImageIcon img = new ImageIcon("ikon.jpg");
        BorderLayout borderLayout = new BorderLayout();

        //Setter spacing mellom componentsa
        borderLayout.setHgap(10);

        //JFrame standardgreier
        frame.setLayout(borderLayout);
        frame.setIconImage(img.getImage());
        frame.setTitle("Labyrintløser av Luka Momcilovic");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1280, 720);
        frame.setResizable(true);

        //Vi lager labyrintSiden
        labyrintSiden = new JPanel(new BorderLayout());

        //Vi lager innstillingSiden
        JPanel innstillingerSiden = new JPanel();
        GridLayout innstillingerGrid = new GridLayout(5,1);
        innstillingerSiden.setLayout(innstillingerGrid);
        innstillingerSiden.setBorder(BorderFactory.createCompoundBorder(new MatteBorder(0, 1, 0, 0, Color.decode("#BAD1D8")), new EmptyBorder(10,10,10,10)));
        innstillingerSiden.setPreferredSize(new Dimension(400, vindu.height));
            //Informasjonspanel
            JPanel informasjonsPanel = new JPanel();
            GridLayout informasjonsPanelLayout = new GridLayout(5,1);
            informasjonsPanelLayout.setHgap(10);
            informasjonsPanel.setLayout(informasjonsPanelLayout);
            informasjonsPanel.setBorder(new CompoundBorder(new EmptyBorder(5, 5, 5, 5), BorderFactory.createTitledBorder("Informasjon")));
                rad = new JLabel("Rad: ");
                kolonne = new JLabel("Kolonne: ");
                antallUtveier = new JLabel("Antall utveier: ");
                kortesteUtveiTittel = new JLabel("Korteste utvei: ");
                utveiNummer = new JLabel("Utvei nummer: ");
            informasjonsPanel.add(kolonne);
            informasjonsPanel.add(rad);
            informasjonsPanel.add(antallUtveier);
            informasjonsPanel.add(kortesteUtveiTittel);
            informasjonsPanel.add(utveiNummer);

            //Menyen
            JPanel meny = new JPanel(); 
                JButton loadKnapp = new JButton("Load en labyrint (.in-fil)");
                    loadKnapp.setActionCommand("loadKnapp");
                JButton visNesteUtveiKnapp = new JButton("Vis en ny utvei fra valgt rute");
                    visNesteUtveiKnapp.setActionCommand("visNesteUtveiKnapp");
                JButton visKortesteUtvei = new JButton("Vis korteste utvei fra valgt rute");
                    visKortesteUtvei.setActionCommand("visKortesteUtvei");
            meny.setLayout(new GridLayout(3,1));
            meny.setBorder(BorderFactory.createTitledBorder("Valg"));
            meny.add(loadKnapp);
            meny.add(visNesteUtveiKnapp);
            meny.add(visKortesteUtvei);

            //ActionPerformed for Meny
            loadKnapp.addActionListener(this);
            visNesteUtveiKnapp.addActionListener(this);
            visKortesteUtvei.addActionListener(this);

            //Log-området
            JPanel logArea = new JPanel();
            textArea = new JTextArea("");
            JScrollPane scrollPane = new JScrollPane(textArea);

            logArea.setBorder(new CompoundBorder(new EmptyBorder(5, 5, 5, 5), BorderFactory.createTitledBorder("Utvei koordinater (x, y)")));

            textArea.setLineWrap(true);
            textArea.setWrapStyleWord(true);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);  
            logArea.setLayout(new BorderLayout());
            logArea.add(scrollPane, BorderLayout.CENTER);  

        //Legger til JPanelene til JFramen
        innstillingerSiden.add(informasjonsPanel);
        innstillingerSiden.add(meny);
        innstillingerSiden.add(logArea);

        //Adder panelene til JFramen
        frame.add(labyrintSiden, BorderLayout.CENTER);
        frame.add(innstillingerSiden, BorderLayout.EAST);

        //Load fil
        this.loadFil();

        //Vis frame
        frame.setVisible(true);
    }

    //Resetter labyrinten med nyFil
    public void labyrintRePainter(Integer rader, Integer kolonner){
        labyrintContainer = new JPanel();
        labyrintLayout = new GridLayout(rader, kolonner);
        labyrintContainer.setLayout(labyrintLayout);
        labyrintSiden.removeAll();
        labyrintSiden.add(labyrintContainer, BorderLayout.CENTER);

        //Fjern informasjon før rePainting
        rad.setText("Valgt rad (y): ");
        kolonne.setText("Valgt kolonne (x): ");
        antallUtveier.setText("Antall utveier: ");
        kortesteUtveiTittel.setText("Korteste utvei: ");

        //Samler sammen alle rutene for så å printe de ut
        for(Integer y = 0; y < this.labyrint.hentRader(); y++){
            for(Integer x = 0; x < this.labyrint.hentKolonner(); x++){
                Rute rute = this.labyrint.hentRutenett()[y][x];
                RuteKnapp button;
                if(rute instanceof HvitRute) {
                    button = new RuteKnapp(rute, this.labyrint, "hvit");
                    button.addActionListener(this);
                    button.setActionCommand("hvitRute");
                    button.setBackground(Color.WHITE);
                }
                else {
                    button = new RuteKnapp(rute, this.labyrint, "svart");
                    button.addActionListener(this);
                    button.setActionCommand("svartRute");
                    button.setBackground(Color.DARK_GRAY);
                }
                alleKnappene[y][x] = button;
                labyrintContainer.add(button);
            }
        }
        frame.setVisible(true);
    }

    //Farger utveien baseert på ArrayList<Tuppel> den mottar som argument
    public void fargUtvei(ArrayList<Tuppel> utvei){
        //Reset utveiene
        for(RuteKnapp[] knappArray : this.alleKnappene){
            for(RuteKnapp knapp : knappArray){
                if(knapp.hentType().equals("hvit")) {
                    knapp.setBackground(Color.WHITE);
                }
                else {
                    knapp.setBackground(Color.DARK_GRAY);
                }
            }
        }

        //Farg utveien
        //Først fjerner vi det som er i logområdet
        textArea.setText("");
        for(Tuppel tuppel : utvei){
            RuteKnapp knapp = alleKnappene[tuppel.hentRad()][tuppel.hentKolonne()];
            textArea.append(tuppel.toString() + "\n");
            knapp.setBackground(Color.RED);
        }
    }

    //Loader en fil
    public void loadFil(){
        JFileChooser filVelger = new JFileChooser();
        Integer svar = filVelger.showOpenDialog(null);
        if(svar == JFileChooser.APPROVE_OPTION){
            File fil = new File(filVelger.getSelectedFile().getAbsolutePath());
            try {
                this.labyrint = new Labyrint(fil);
                this.alleKnappene = new RuteKnapp[this.labyrint.hentRader()][this.labyrint.hentKolonner()];
                this.labyrintRePainter(this.labyrint.hentRader(), this.labyrint.hentKolonner());
            } 
            catch (FileNotFoundException e1) {
                e1.printStackTrace();
            }
        }
    }

    //Når man trykker på en knapp
    public void actionPerformed(ActionEvent e) {
        switch(e.getActionCommand()) {
            case "loadKnapp":
                this.loadFil();
            break;
            case "visNesteUtveiKnapp":
                if(this.valgtVei != null){
                    if(this.valgtVei == this.utveier.size() - 1) this.valgtVei = 0;
                    else this.valgtVei++;
                    this.fargUtvei(this.utveier.get(this.valgtVei));
                    ArrayList<Tuppel> veien = this.utveier.get(this.valgtVei);
                    utveiNummer.setText(String.valueOf("Utvei nummer: " + this.valgtVei));
                    this.fargUtvei(veien);
                    this.startRute.setBackground(Color.GREEN);
                }
                else{
                    return;
                }
            break;
            case "visKortesteUtvei":
                if(this.kortesteUtvei != null){
                    this.utveiNummer.setText(String.valueOf("Utvei nummer: " + kortesteUtveiIndeks));
                    this.fargUtvei(this.kortesteUtvei);
                    this.startRute.setBackground(Color.GREEN);
                }
                else{
                    return;
                }
            break;
            case "hvitRute":
                utveier = ((RuteKnapp) e.getSource()).finnVei();
                rad.setText("Valgt rute rad(y): " + String.valueOf(((RuteKnapp) e.getSource()).hentRad()));
                kolonne.setText("Valgt rute kolonne(x): " + String.valueOf(((RuteKnapp) e.getSource()).hentKolonne()));
                antallUtveier.setText("Antall utveier: " + String.valueOf(utveier.size()));

                //Finner korteste utvei og farger
                if(utveier.size() > 0){
                    Integer lavest = utveier.get(0).size();
                    kortesteUtvei = utveier.get(0);
                    kortesteUtveiTittel.setText("Korteste utvei: " + lavest.toString() + " steg");
                    for(Integer i = 0; i < utveier.size(); i++){
                        ArrayList<Tuppel> listen = utveier.get(i);
                        if(listen.size() < lavest){
                            lavest = listen.size();
                            kortesteUtveiTittel.setText("Korteste utvei: " + lavest.toString() + " steg");
                            kortesteUtvei = listen;
                            kortesteUtveiIndeks = i;
                        }
                    }
                    this.valgtVei = 0;
                    utveiNummer.setText("Utvei nummer: " + String.valueOf(this.valgtVei));
                    this.fargUtvei(this.utveier.get(this.valgtVei));
                    this.startRute = ((RuteKnapp) e.getSource());
                    this.startRute.setBackground(Color.GREEN);
                }
            default:
            break;
        }
    }
}