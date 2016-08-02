import java.awt.image.BufferedImage;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.Point;
import java.awt.Color;

import java.io.File;

import javax.imageio.ImageIO;

public class ScreenGrabber 
{
    private Rectangle m_screenSize;
    private String m_quality;
    private BufferedImage m_image;
    private ScreenTrimmer m_trimmer;
    private String m_fileName;
    private Robot m_robot;

    public ScreenGrabber(Robot p_robot) 
    {
        m_screenSize = new Rectangle( Toolkit.getDefaultToolkit().getScreenSize() );
        m_quality = "png";
        m_trimmer = new ScreenTrimmer();
        m_fileName = "OutputFile";
        m_robot = p_robot;
    }

    public void saveScreenImage()
    {
        grabScreenImage();
        writeImageToFile();
    }

    public void saveTrimmedImage()
    {
        grabScreenImage();
        m_image = m_trimmer.trimScreenImage( m_image );
        writeImageToFile();
    }

    private void grabScreenImage()
    {
        m_image = m_robot.createScreenCapture( m_screenSize );   
    }

    public void grabTrimmedImage()
    {
        grabScreenImage();
        m_image = m_trimmer.trimScreenImage( m_image );   
    }

    private void writeImageToFile()
    {
        try
        {
            ImageIO.write( m_image, m_quality, new File( m_fileName + "." + m_quality ) );
        } catch(Exception e){}
    }

    private Color getPixelRgbValue(Point p_coordinates)
    {
        return new Color( m_image.getRGB( p_coordinates.x, p_coordinates.y ) );
    }

    public int getPixelBlueValue(Point p_coordinates)
    {
        return getPixelRgbValue(p_coordinates).getBlue();
    }

    public int getPixelRedValue(Point p_coordinates)
    {
        return getPixelRgbValue(p_coordinates).getRed();
    }

    public int getPixelGreenValue(Point p_coordinates)
    {
        return getPixelRgbValue(p_coordinates).getGreen();
    }

    private class ScreenTrimmer
    {
        private Point m_topLeft;
        private int m_width;
        private int m_height;

        ScreenTrimmer()
        {
            //TODO: change that to something generic
            //m_topLeft = new Point( 317, 221 );
            m_topLeft = new Point( 39, 245);//288);//245 );

            m_width = 640;
            m_height = 480;
        }

        public BufferedImage trimScreenImage(BufferedImage p_imageToTrim)
        {
            return p_imageToTrim.getSubimage(m_topLeft.x, m_topLeft.y, m_width, m_height);
        }
    }
}