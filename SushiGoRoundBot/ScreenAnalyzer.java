import java.awt.image.BufferedImage;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.Point;

import java.io.File;

import javax.imageio.ImageIO;

public class ScreenAnalyzer
{
    public ScreenGrabber m_screenGrabber;
    private Point m_sushiOrderUniquePixelCoords = new Point(55, 60); 
    private int[] m_blueColorOfUniquePixel = new int[] { 220, 182, 0, 96, 0, 23}; //Shrimp also has got 0 blue
    private int[] m_redColorOfUniquePixel = new int[] { 230, 255, 255, 255, 0, 67};
    private int[] m_greenColorOfUniquePixel = new int[] { 234, 204, 123, 143, 0, 62};

    public ScreenAnalyzer(Robot p_robot)
    {
        m_screenGrabber = new ScreenGrabber(p_robot);
    }

    int checkBlueColorOfUniquePixel(int p_clientNumber)
    {
        Point l_clientOffset = new Point(101 * (p_clientNumber - 1), 0);
        Point l_uniquePixelCoords = new Point(m_sushiOrderUniquePixelCoords.x + l_clientOffset.x, m_sushiOrderUniquePixelCoords.y);
        m_screenGrabber.grabTrimmedImage();
        return m_screenGrabber.getPixelBlueValue( l_uniquePixelCoords );
    }

    int checkRedColorOfUniquePixel(int p_clientNumber)
    {
        Point l_clientOffset = new Point(101 * (p_clientNumber - 1), 0);
        Point l_uniquePixelCoords = new Point(m_sushiOrderUniquePixelCoords.x + l_clientOffset.x, m_sushiOrderUniquePixelCoords.y);
        m_screenGrabber.grabTrimmedImage();
        return m_screenGrabber.getPixelRedValue( l_uniquePixelCoords );
    }

    int checkGreenColorOfUniquePixel(int p_clientNumber)
    {
        Point l_clientOffset = new Point(101 * (p_clientNumber - 1), 0);
        Point l_uniquePixelCoords = new Point(m_sushiOrderUniquePixelCoords.x + l_clientOffset.x, m_sushiOrderUniquePixelCoords.y);
        m_screenGrabber.grabTrimmedImage();
        return m_screenGrabber.getPixelGreenValue( l_uniquePixelCoords );
    }

    public SushiMaker.SushiType whatSushiToMakeForClient(int p_clientNumber)
    {
        int i = 0;
        int l_seekedValueOfGreen = checkGreenColorOfUniquePixel( p_clientNumber );
        for( i = 0 ; i < m_greenColorOfUniquePixel.length ; i++ )
            if( m_greenColorOfUniquePixel[i] == l_seekedValueOfGreen )
                return SushiMaker.SushiType.values()[i];
        return SushiMaker.SushiType.values()[0]; //Exception would be better
    }

    public boolean isClientPresent(int p_clientNumber)
    {
        int i = 0;
        int l_seekedValueOfGreen = checkGreenColorOfUniquePixel( p_clientNumber );
        for( i = 0 ; i < m_greenColorOfUniquePixel.length ; i++ )
            if( m_greenColorOfUniquePixel[i] == l_seekedValueOfGreen )
                return true;
        return false;
    }

}