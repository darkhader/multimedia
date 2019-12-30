package sample;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.ImageObserver;
import java.awt.image.Kernel;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;

public class CodeLoadAnh {
    public static void main(String args[]) {
        new MainInter();
    }
}

class ImageBox extends Canvas implements ImageObserver {
    private Image img;
    double radian = 0.0;
    Dimension ds;
    int iw;
    int ih;
    int x;
    int y;
    int adjX;
    int adjY;
    int adjW;
    int adjH;
    int mX;
    int mY;
    int orWidth;
    int orHeight;
    String filename;
    int curImgIndex;

    public ImageBox() {
    }

    {
        ds = getToolkit().getScreenSize();
        mX = (int) ds.getWidth() / 2;
        mY = (int) ds.getHeight() / 2;
        filename = null;
        adjX = 0;
        adjY = 0;
        adjW = 0;
        adjH = 0;

    }

    public void paint(Graphics g) {
        showImage(g);

    }

    public void showImage(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;
        g2d.translate(x, y);
        g2d.drawImage(img, 0, 0, iw, ih, this);
        g2d.dispose();

    }

    public void readImage(String filename) {
        try {
            //read the photo file so it is ready to display
            img = ImageIO.read(new File(filename));
            //get the original width and height of the photo
            orWidth = img.getWidth(this);
            orHeight = img.getHeight(this);
            //Make sure the photo is not too large to fit the screen
            if (mX < orWidth / 2)
                orWidth = mX * 2;
            if (mY < orHeight / 2)
                orHeight = mY * 2;
            setImageBounds();

        } catch (IOException e) {
        }
        ;
    }

    public void setImageBounds() {
        try {
            //location to place the photo
            x = mX - orWidth / 2 + adjX - 300;
            y = mY - orHeight / 2 + adjY - 100;
            //photo size
            iw = orWidth + adjW;
            ih = orHeight + adjH;

        } catch (NullPointerException npe) {
            System.exit(-1);
        }
    }

}


class MainInter extends JFrame implements ActionListener, ListSelectionListener {
    JFrame frame;
    JList imglist;
    JButton themanh;
    JButton thunho;
    JButton thunho1;
    JButton xoayanh;
    JButton bluranh;
    JButton catanh;
    DefaultListModel listmodel;
    JLabel lbl;
    JLabel lbl1;
    JScrollPane scr;
    JPanel panel;
    ImageBox imgb;

    MainInter() {
        frame = new JFrame("hien thi anh");
        imgb = new ImageBox();
        listmodel = new DefaultListModel();
        themanh = new JButton("them anh");
        themanh.addActionListener(this);
        themanh.setBounds(350, 650, 100, 30);
        themanh.setBackground(Color.red);

        thunho = new JButton("thu nho");
        thunho.addActionListener(this);
        thunho.setBounds(470, 650, 100, 30);
        thunho.setBackground(Color.red);

        thunho1 = new JButton("thu nho 1");
        thunho1.addActionListener(this);
        thunho1.setBounds(590, 650, 100, 30);
        thunho1.setBackground(Color.red);

        xoayanh = new JButton("xoay anh");
        xoayanh.addActionListener(this);
        xoayanh.setBounds(710, 650, 100, 30);
        xoayanh.setBackground(Color.red);

        bluranh = new JButton("lam mo");
        bluranh.addActionListener(this);
        bluranh.setBounds(830, 650, 100, 30);
        bluranh.setBackground(Color.red);

        catanh = new JButton("cat anh");
        catanh.addActionListener(this);
        catanh.setBounds(950, 650, 100, 30);
        catanh.setBackground(Color.red);

        lbl = new JLabel("<html><font color='blue'>Danh Sach Anh</font></html>");
        lbl.setBounds(10, 3, 100, 30);
        lbl1 = new JLabel("<html><font color='Green'>Bai Tap Lon Lap Trinh Java</font></html>");
        lbl1.setBounds(300, 3, 200, 40);
        addToList(listmodel);
        imglist = new JList(listmodel);
        imglist.setVisibleRowCount(20);
        imglist.addListSelectionListener(this);
        frame.setLayout(null);
        panel = new JPanel();
        panel.setBounds(10, 33, 270, 600);
        scr = new JScrollPane(imglist);
        panel.add(scr);
        frame.add(panel); //hien thi ten file anh ve
        imgb.setBounds(300, 33, 800, 600);
        frame.add(imgb); //hien thi anh ve
        frame.add(themanh);
        frame.add(thunho);
        frame.add(thunho1);
        frame.add(xoayanh);
        frame.add(bluranh);
        frame.add(catanh);
        frame.add(lbl);
        frame.add(lbl1);
        frame.setSize(1150, 750);//850 350
        frame.setResizable(false);
        frame.addWindowListener(new Listener());
        frame.setVisible(true);
        imglist.setSelectedIndex(0);
        panel = new JPanel();

    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == themanh) showFileDialog(frame);
        if (e.getSource() == thunho) resize();
        if (e.getSource() == bluranh) blur();

    }

    public void valueChanged(ListSelectionEvent e) {
        loadImage(imglist.getSelectedValue().toString());

    }

    public void loadImage(String filename) {
        imgb.readImage(filename);
        imgb.repaint();
    }

    static class Listener extends WindowAdapter {

        public void windowClosing(WindowEvent event) {

            System.exit(0);

        }

    }

    public void addToList(DefaultListModel listmodel) {

        try { //bat loi
            String text;
            FileReader fr = new FileReader("imagefiles.txt");
            BufferedReader br = new BufferedReader(fr);
            while ((text = br.readLine()) != null) {
                //doc dong ki tu
                listmodel.addElement(text);

            }
            br.close();//dong thuoc tinh
            fr.close();
        } catch (Exception ie) {
            System.out.println("IO problem!");
            ie.printStackTrace();
        }

    }

    public static void saveToFile(String filename, String text) {
        try {
            FileWriter fw = new FileWriter(filename, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(text);
            bw.newLine();
            bw.flush();
            bw.close();
        } catch (IOException ie) {
            System.out.println("Error in writing to file...");
        }
    }

    public void showFileDialog(Frame frame) {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG & GIF Images", "jpg", "gif");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(frame);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            saveToFile("imagefiles.txt", chooser.getSelectedFile().toString());
            listmodel.addElement(chooser.getSelectedFile().toString());

        }
    }

    public void resize() {
        File inputFile = new File(imglist.getSelectedValue().toString());
        try {
            // resize to a fixed width (not proportional)
            double percent = 0.1;
            String outputImagePath = "D:/Photo/Puppy_Fixed.jpg";
            BufferedImage inputImage = ImageIO.read(inputFile);
            int scaledWidth = (int) (inputImage.getWidth() * percent);
            int scaledHeight = (int) (inputImage.getHeight() * percent);

            BufferedImage outputImage = new BufferedImage(scaledWidth,
                    scaledHeight, inputImage.getType());

            // scales the input image to the output image
            Graphics2D g2d = outputImage.createGraphics();
            g2d.drawImage(inputImage, 0, 0, scaledWidth, scaledHeight, null);
            g2d.dispose();

            // extracts extension of output file
            String formatName = outputImagePath.substring(outputImagePath
                    .lastIndexOf(".") + 1);

            // writes to output file
            ImageIO.write(outputImage, formatName, new File(outputImagePath));

        } catch (IOException ex) {
            System.out.println("Error resizing the image.");
            ex.printStackTrace();
        }

    }
    public void resize1() {
        File inputFile = new File(imglist.getSelectedValue().toString());
        try {
            // resize to a fixed width (not proportional)
            double percent = 0.2;
            String outputImagePath = "D:/Photo/Puppy_Fixed.jpg";
            BufferedImage inputImage = ImageIO.read(inputFile);
            int scaledWidth = (int) (inputImage.getWidth() * percent);
            int scaledHeight = (int) (inputImage.getHeight() * percent);

            BufferedImage outputImage = new BufferedImage(scaledWidth,
                    scaledHeight, inputImage.getType());

            // scales the input image to the output image
            Graphics2D g2d = outputImage.createGraphics();
            g2d.drawImage(inputImage, 0, 0, scaledWidth, scaledHeight, null);
            g2d.dispose();

            // extracts extension of output file
            String formatName = outputImagePath.substring(outputImagePath
                    .lastIndexOf(".") + 1);

            // writes to output file
            ImageIO.write(outputImage, formatName, new File(outputImagePath));

        } catch (IOException ex) {
            System.out.println("Error resizing the image.");
            ex.printStackTrace();
        }

    }
    public void blur() {
        File inputFile = new File(imglist.getSelectedValue().toString());
        try {
            // resize to a fixed width (not proportional)
            BufferedImage inputImage = ImageIO.read(inputFile);



            int height = inputImage.getHeight();
            int width = inputImage.getWidth();
            // result is transposed, so the width/height are swapped
            BufferedImage temp =  new BufferedImage(width,height, BufferedImage.TYPE_INT_RGB);
            double[] k = new double[] { 0.00598, 0.060626, 0.241843, 0.383103, 0.241843, 0.060626, 0.00598 };
            // horizontal blur, transpose result
            for (int y = 0; y < height; y++) {
                for (int x = 3; x < width - 3; x++) {
                    float r = 0, g = 0, b = 0;
                    for (int i = 0; i < 7; i++) {
                        int pixel = inputImage.getRGB(x + i - 3, y);
                        b += (pixel & 0xFF) * k[i];
                        g += ((pixel >> 8) & 0xFF) * k[i];
                        r += ((pixel >> 16) & 0xFF) * k[i];
                    }
                    int p = (int)b + ((int)g << 8) + ((int)r << 16);
                    // transpose result!
                    temp.setRGB(x, y, p);
                }
            }

            BufferedImage outputImage = temp;




            String outputImagePath = "D:/Photo/Puppy_Fixed.jpg";



            // scales the input image to the output image
            Graphics2D g2d = outputImage.createGraphics();
            g2d.dispose();

            // extracts extension of output file
            String formatName = outputImagePath.substring(outputImagePath
                    .lastIndexOf(".") + 1);

            // writes to output file
            ImageIO.write(outputImage, formatName, new File(outputImagePath));

        } catch (IOException ex) {
            System.out.println("Error resizing the image.");
            ex.printStackTrace();
        }

    }
}
