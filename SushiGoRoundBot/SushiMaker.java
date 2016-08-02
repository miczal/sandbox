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

public class SushiMaker
{
    private MouseOperator m_mouseOperator;
    private int m_originX = 39;
    private int m_originY = 245;//288;//245;
    private int m_distanceBetweenIngredients = 55;
    private Point[] m_ingredientsCoords;
    private static boolean firstRessupply = true;
    
    private Point[] m_equipmentCoords = new Point[]
    {
        new Point(m_originX + 75, m_originY + 210),
        new Point(m_originX + 200, m_originY + 400),
        new Point(m_originX + 600, m_originY + 350)
    };

    private Point[] m_phoneBookCoords = new Point[]
    {
        new Point(m_originX + 540, m_originY + 270), //Toppings
        new Point(m_originX + 540, m_originY + 270 + 21), //Rice
        new Point(m_originX + 540, m_originY + 270 + ( 2 * 21 ) ), //Sake
        new Point(m_originX + 585, m_originY + 340), //Exit appropriate for every screen
        new Point(m_originX + 485, m_originY + 295) //Delivery normal
    };

    private Point[] m_phoneBookIngredientsCoords = new Point[]
    {
        new Point(m_originX + 490, m_originY + 220),//X+82 Y+55
        new Point(m_originX + 545, m_originY + 280),
        new Point(m_originX + 490, m_originY + 220 + 55),
        new Point(m_originX + 490 + 82, m_originY + 220 + 55),
        new Point(m_originX + 490, m_originY + 220 + ( 2 * 55 ) ),
        new Point(m_originX + 490 + 82, m_originY + 220)
    };

    private Ingredient[][] m_sushiRecipes = new Ingredient[][] 
    {
        {Ingredient.Rice, Ingredient.Rice, Ingredient.Nori},
        {Ingredient.Rice, Ingredient.Nori, Ingredient.Roe},
        {Ingredient.Rice, Ingredient.Nori, Ingredient.Roe, Ingredient.Roe},
        {Ingredient.Rice, Ingredient.Nori, Ingredient.Salmon, Ingredient.Salmon},
        {Ingredient.Rice, Ingredient.Nori, Ingredient.Shrimp, Ingredient.Shrimp},
        {Ingredient.Rice, Ingredient.Nori, Ingredient.Unagi, Ingredient.Unagi}
    };

    private int[] m_ingredientsQuantity = new int[] 
    {
        5, 10, 10, 10, 5, 5
        //5, 6, 8, 8, 5, 5
    };

    SushiMaker(Robot p_robot)
    {
        m_mouseOperator = new MouseOperator(p_robot);
        initIngredientsCoords();
    }

    public void clickStart()
    {
        m_mouseOperator.moveMouse( new Point( 350, 450 ) ); //Zmienić na origin
        m_mouseOperator.leftClick();
    }

    public void clickContinue()
    {
        m_mouseOperator.moveMouse( new Point( 350, 630 ) );
        m_mouseOperator.leftClick();
    }

    public void clickSkip()
    {
        m_mouseOperator.moveMouse( new Point( 620, 695 ) );
        m_mouseOperator.leftClick();
    }

    public void startGame()
    {
        clickStart();
        clickContinue();
        clickSkip();
        clickContinue();
    }

    private void initIngredientsCoords()
    {
        int l_centerOfIngredientX = 35;
        int l_centerOfIngredientY = 330;
        m_ingredientsCoords = new Point[Ingredient.values().length];

        for(int i = 0 ; i < Ingredient.values().length ; i++)
        {
            int l_xCoord = m_originX + l_centerOfIngredientX + ( m_distanceBetweenIngredients * ( i % 2 ) );
            int l_yCoord = m_originY + l_centerOfIngredientY + ( m_distanceBetweenIngredients * ( i / 2 ) );
            m_ingredientsCoords[i] = new Point( l_xCoord, l_yCoord );
        }
    }

    void clickIngredient(final Ingredient p_ingredient)
    {
        m_mouseOperator.moveMouse( m_ingredientsCoords[p_ingredient.ordinal()] );
        m_mouseOperator.leftClick();
    }

    void makeSushi(final SushiType p_sushiType)
    {
        synchronized( this )
        {
            for ( Ingredient ingredient : m_sushiRecipes[p_sushiType.ordinal()] ) 
            {
                clickIngredient( ingredient );
                --m_ingredientsQuantity[ ingredient.ordinal() ]; 
            }
            foldMat();
            try
            {
                Thread.sleep( 1500 );
            } catch(Exception e){}
        }
    }

    synchronized void clearPlate(final int p_plateNumber)
    {
        //Very ugly
        //Refactor all enums to enumMap
        final int l_plateOffset = 101;//Strange offset, but correct
        Point l_plateCoords = new Point( m_equipmentCoords[Equipment.Plate.ordinal()] );
        l_plateCoords.x += ( (p_plateNumber - 1) * l_plateOffset ) ;
        m_mouseOperator.moveMouse( l_plateCoords );
        m_mouseOperator.leftClick();
    }

    void foldMat()
    {
        m_mouseOperator.moveMouse( m_equipmentCoords[Equipment.Mat.ordinal()] );
        m_mouseOperator.leftClick();

    }

    public synchronized boolean checkIfInNeedOfRessuply()
    {
        boolean ressupply = false;
        for( int i = 0 ; i < m_ingredientsQuantity.length ; i++ )
        {
            if( m_ingredientsQuantity[i] < 2 )
            {
                if( ! ( firstRessupply && i == 0 && i == 4 && i == 5 ) )
                {
                    if( firstRessupply ){
                        try
                        {
                            Thread.sleep( 7000 );
                        } catch(Exception e){}
                    }
                    orderIngredient( Ingredient.values()[i] );
                    updateIngredientCounter( Ingredient.values()[i] );
                    ressupply = true;
                }
            }
        }
        if (ressupply)
        {
            if( firstRessupply == false )
            {
                checkRessuplyInAdvance();
            }
            firstRessupply = false;
            for( int i = 1 ; i <= 6 ; i++ )
            {
                clearPlate(i);
            }
            try
            {
                Thread.sleep( 6000 );
            } catch(Exception e){}
        }
        return ressupply;
    }

    public synchronized void checkRessuplyInAdvance()
    {
        for( int i = 0 ; i < m_ingredientsQuantity.length ; i++ )
        {
            if( ( i != 0 && i != 4 && i != 5 ) && ( m_ingredientsQuantity[i] < 4 )  )
            {
                orderIngredient( Ingredient.values()[i] );
                updateIngredientCounter( Ingredient.values()[i] );
            }
        }
    }

    synchronized void updateIngredientCounter(Ingredient p_ingredientToOrder)
    {
        synchronized( this )
        {
            if( p_ingredientToOrder == Ingredient.Shrimp || p_ingredientToOrder == Ingredient.Salmon || p_ingredientToOrder == Ingredient.Unagi )
            {
                m_ingredientsQuantity[p_ingredientToOrder.ordinal()]+=5;
            }
            else
            {
                m_ingredientsQuantity[p_ingredientToOrder.ordinal()]+=10;
            }
        }
    }

    void orderIngredient(Ingredient p_ingredientToOrder)
    {
        synchronized( this )
        {
            m_mouseOperator.moveMouse( m_equipmentCoords[Equipment.Phone.ordinal()] );
            m_mouseOperator.leftClick();
            if( p_ingredientToOrder == Ingredient.Rice)
            {
                m_mouseOperator.moveMouse( m_phoneBookCoords[PhoneBook.Rice.ordinal()] );
            }
            else
            {
                m_mouseOperator.moveMouse( m_phoneBookCoords[PhoneBook.Toppings.ordinal()] );
            }
            m_mouseOperator.leftClick();
            m_mouseOperator.moveMouse( m_phoneBookIngredientsCoords[p_ingredientToOrder.ordinal()] );
            m_mouseOperator.leftClick();
            m_mouseOperator.moveMouse( m_phoneBookCoords[PhoneBook.NormalDelivery.ordinal()] );
            m_mouseOperator.leftClick();
        }
    }

    public enum SushiType 
    { 
        Onigiri, 
        CaliforniaRoll, 
        Gunkan,
        SalmonRoll,
        ShrimpSushi,
        UnagiRoll
    }

    public enum Ingredient
    {
        Shrimp,
        Rice,
        Nori,
        Roe,
        Salmon,
        Unagi
    }

    public enum Equipment
    {
        Plate,
        Mat,
        Phone
    }

    public enum PhoneBook
    {
        Toppings,
        Rice,
        Sake,
        Exit,
        NormalDelivery
    }
}