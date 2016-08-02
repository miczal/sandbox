import java.awt.image.BufferedImage;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.Point;

import java.io.File;

import javax.imageio.ImageIO;

import java.awt.event.InputEvent;
import java.lang.Thread;
import java.awt.MouseInfo;
import java.util.concurrent.Executors;

public class SushiBot
{
    public SushiMaker m_sushiMaker;//At this time public because of non automated way bot behaves
    public ScreenAnalyzer m_screenAnalyzer; 
    private Robot m_robot;
    private Thread[] m_clientWatchers;
    private int NUMBER_OF_THREADS = 6;

    SushiBot()
    {
        try
        {
            m_robot = new Robot();
        } catch(Exception e){}

        m_sushiMaker = new SushiMaker(m_robot);
        m_screenAnalyzer = new ScreenAnalyzer(m_robot);
        m_clientWatchers = new Thread[NUMBER_OF_THREADS];
        for(int threadNumber = 0 ; threadNumber < NUMBER_OF_THREADS ; threadNumber++)
        {
            System.out.println(  "Thread no " + threadNumber );
            m_clientWatchers[threadNumber] = new Thread( new ClientWatcher( threadNumber + 1, m_screenAnalyzer, this ) );
            m_clientWatchers[threadNumber].start();
        }
    }

    public synchronized void makeSushiForClient(int p_clientNumber)
    {
            m_sushiMaker.makeSushi( m_screenAnalyzer.whatSushiToMakeForClient( p_clientNumber ) );
            m_sushiMaker.checkIfInNeedOfRessuply();
    }

    public synchronized void clearAllPlates()
    {
            for( int i = 1 ; i <= 6 ; i++ )
            {
                m_sushiMaker.clearPlate(i);
            }
    }
}