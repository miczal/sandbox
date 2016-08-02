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

public class MouseOperator
{
    private Robot m_robot;
    private final int m_sleepTime = 50;
    public MouseOperator()
    {
        try
        {
            m_robot = new Robot();
        } catch(Exception e){}
    }
    public MouseOperator(Robot p_robot)
    {
        m_robot = p_robot;
    }

    public void moveMouse( Point p_destinationPoint ) 
    {
        m_robot.mouseMove(p_destinationPoint.x, p_destinationPoint.y);
    }

    public void leftClick()
    {
        try
        {
            m_robot.mousePress(InputEvent.BUTTON1_MASK);
            Thread.sleep( m_sleepTime );
            m_robot.mouseRelease(InputEvent.BUTTON1_MASK);
            Thread.sleep( m_sleepTime );
            System.out.println( "Click!" ); 
        } catch(Exception e){}
    }

    public void getMouseCoordinates()
    {
        Point l_position =  new Point( MouseInfo.getPointerInfo().getLocation() );

        System.out.println(  "X: " + l_position.x );
        System.out.println(  "Y: " + l_position.y );
    }

}