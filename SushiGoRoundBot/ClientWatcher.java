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
import java.lang.Runnable;

public class ClientWatcher implements Runnable
{
    private int m_waitingPeriodForFirstClient = 7000;
    private int m_waitingPeriodBetweenClients = 3200;
    private int m_waitingPeriodForNewClient = 40; //2000
    private int m_waitingPeriodForNormalDelivery = 5000;
    private int m_idlePeriod = 40;

    private ScreenAnalyzer m_screenAnalyzer;
    private int m_watchedClientNumber;
    private ClientWatcherStates m_currentState;
    private SushiBot m_sushiBot;

    public ClientWatcher(int p_watchedClientNumber, ScreenAnalyzer p_screenAnalyzer, SushiBot p_sushiBot)
    {
        m_watchedClientNumber = p_watchedClientNumber;
        m_screenAnalyzer = p_screenAnalyzer;
        m_sushiBot = p_sushiBot;
        m_currentState = ClientWatcherStates.Watching;
    }

    public void run()
    {
        while( true )
        {
            try
            {
                Thread.sleep( m_idlePeriod );
            } catch(Exception e){}
            switch( m_currentState )
            {
                case Watching :
                    stateWatching();
                    break;
                case PlacingOrder :
                    statePlacingOrder();
                    break;
                case WaitingForSushi :
                    stateWaitingForSushi();
                    break;
                case WaitingForDelivery :
                    stateWaitingForDelivery();
                    break;
            }
        }
    }

    private void stateWatching()
    {
        System.out.println(  "Watching " + m_watchedClientNumber );
        try
        {
            Thread.sleep( m_waitingPeriodForNewClient );    
        } catch(Exception e){}
        
        if( m_screenAnalyzer.isClientPresent( m_watchedClientNumber ) )
        {
            System.out.println(  "Client present " + m_watchedClientNumber );
            m_currentState = ClientWatcherStates.PlacingOrder;
        }
        else
        {
            System.out.println(  "Client NOT present " + m_watchedClientNumber );
        }
    }

    private void statePlacingOrder()
    {
        m_sushiBot.makeSushiForClient( m_watchedClientNumber );
        m_currentState = ClientWatcherStates.WaitingForSushi;
    }

    private void stateWaitingForSushi()
    {
        try
        {
            Thread.sleep( m_waitingPeriodForFirstClient + ( m_watchedClientNumber - 1 ) * m_waitingPeriodBetweenClients + 3500);
        } catch(Exception e){}
        m_sushiBot.clearAllPlates();
        m_currentState = ClientWatcherStates.Watching;
    }

    private void stateWaitingForDelivery()
    {
        try
        {
            Thread.sleep( m_waitingPeriodForNormalDelivery + 2000 );
        } catch(Exception e){}
        m_currentState = ClientWatcherStates.Watching;
    }

    public void switchStateToWaitingForDelivery()
    {
        m_currentState = ClientWatcherStates.WaitingForDelivery;
    }

    private enum ClientWatcherStates
    {
        Watching,
        PlacingOrder,
        WaitingForSushi,
        WaitingForDelivery
    }
}