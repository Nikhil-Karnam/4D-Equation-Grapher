import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;
import java.util.Scanner;

public class Main extends Canvas implements Runnable{
	
	private static final long serialVersionUID = 2L;

    public static final int WIDTH = 1400, HEIGHT = 1050;
    private Thread thread;
    private boolean running = false;
    
    public static String PFunctionx;
    public static String PFunctiony;
    public static String PFunctionz;
    public static String PFunctiont;
    
    public boolean BGCheck = false;
    
    private int graphSpeed = 1000;
    
    Plot plot = new Plot();
    
    Scanner sc = new Scanner(System.in);
    
    public Main()
    {
    	plot.settracestep(.02);
    	plot.setWindow(2);
    	
    	
    	plot.setuMin(-3);
    	plot.setuMax(3);
    	
    	plot.setvMin(-3);
    	plot.setvMax(3);

    	plot.setwMin(-3);
    	plot.setwMax(3);
    	
    	System.out.println("Enter equation");
    	
    	System.out.print("x = ");
		PFunctionx = sc.nextLine();
        
        System.out.print("y = ");
        PFunctiony = sc.nextLine();
        
        System.out.print("z = ");
        PFunctionz = sc.nextLine();
        
        System.out.print("t = ");
        PFunctiont = sc.nextLine();
        
        
        new Window(WIDTH, HEIGHT, "3D Equation Grapher", this);
    }
    
    
    public synchronized void start()
    {
        thread = new Thread(this);
        thread.start();
        running = true;
    }

    public synchronized void stop()
    {
        try
        {
            thread.join();
            running = false;
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void run()
    {
        this.requestFocus();
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        while(running)
        {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while(delta >=1)
            {
                tick();
                delta--;
            }
            if(running)
                render();
            frames++;

            if(System.currentTimeMillis() - timer > 1000)
            {
                timer += 1000;
                //System.out.println("FPS: "+ frames);
                frames = 0;
            }
        }
        stop();
    }
    
    public void tick() 
    {
    	
    }
    
    public void render() 
    {
    	BufferStrategy bs = this.getBufferStrategy();
        if(bs == null)
        {
            this.createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();
        
        //actual code starts here
        
        if(!BGCheck)
        {
        	g.setColor(Color.black);
            g.fillRect(0,0, WIDTH, HEIGHT);
            plot.renderBG(g);
            
            BGCheck = true;
        }
        
        for(int i = 0; i < graphSpeed; i++)
        	plot.renderAnimatedGraph(g);
                
        //ends code
        g.dispose();
        bs.show();
    }
    
    
    public static void main(String[] args) 
    {
    	new Main();
    }
}
