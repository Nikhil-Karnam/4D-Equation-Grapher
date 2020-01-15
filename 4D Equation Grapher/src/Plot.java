import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;

public class Plot{
	
	private double tracestep;
	private double window;
	
	private double uMin;
	private double uMax;
	private double vMin;
	private double vMax;
	private double wMin;
	private double wMax;
	
	public double u = uMin;
	public double v = vMin;
	public double w = wMin;
						
	public int longitude = 0;
	public int latitude = 0;
	public int grid3 = 0;
	public boolean gridLine = false;
	
	public void renderBG(Graphics g)
	{
		newGrid(g);
		paintBG(g);
		writeEquationNew(g);
	}
	
	public Color makeColor(double x, double y, double z) 
	{
		int val = Math.abs((int)(x / 4))+40;
		int val2 = Math.abs((int)(y / 4)+40);
		int val3 = Math.abs((int)(z / 4)+40);
				
		while(val > 255)
			val = 500-val;
		
		while(val2 > 255)
			val2 = 500-val2;
		
		while(val3 > 255)
			val3 = 500-val3;

		return new Color(val3, val2, val);
	}
	
	public void renderAnimatedGraph(Graphics g) 
	{
		if(u <= uMax) 
		{
			 if(longitude % 10 == 0 || latitude % 10 == 0 || grid3 % 10 == 0)
			 	gridLine = true;
			 else
				gridLine = false;
			
			double x = yVal.calculateY(u, v, w, Main.PFunctionx);
			double y = yVal.calculateY(u, v, w, Main.PFunctiony);
			double z = yVal.calculateY(u, v, w, Main.PFunctionz);
			double t = yVal.calculateY(u, v, w, Main.PFunctiont);
			
			
			//System.out.println(x + "  " + y + "  " + z + "  " + t);
			
			//format to z coordinate
			x += y/2;
			z += y/2;
			
			//format to t coordinate
			x -= w/2;
			z += w/2;
			
					
			x = x * 500/window + 500;
			z = z * -500/window + 500;

			
			if(!Double.isNaN(z) && z <= 1000 && z >= 0 && !Double.isNaN(x) && x <= 1000 && x>=0)
			{	
				if(gridLine)
					g.setColor(Color.white);
				else
					g.setColor(makeColor(x,z,10*y));
				
				g.fillRect((int)x,(int)z,2,2);
		    }
			
			w += tracestep;
			longitude++;
			
			if(w > wMax) 
			{
				v += tracestep;
				w = wMin;
				longitude = 0;
				latitude++;
			}
			
			if(v > vMax) 
			{
				u += tracestep;
				v = vMin;
				longitude = 0;
				latitude = 0;
				grid3++;
			}
				
		}
		
	}
	
	
	//code for drawing background

	
	public void newGrid(Graphics g) 
	{
		g.setColor(Color.white);

   	 	for(int i = 0; i <= 1000; i++) 
   	 	{
   	 		g.fillRect(i,500,2,2);
   	 		g.fillRect(500,i,2,2);
   	 		
   	 		if(i%50 == 0) 
   	 		{   	 			
   	 			g.drawOval(i-5, 500-5, 10, 10);
   	 			g.drawOval(500-5,i-5,10,10);
   	 		}
   	 	}
   	 	
   	 	int count = 0;
   	 	
   	 	for(int i = 250; i <= 750; i++) 
   	 	{
   	 		g.fillRect(i, 1000-i, 2, 2);
   	 		g.fillRect(i, i, 2, 2);
   	 	
   	 		if(count%25 == 0) 
   	 		{
	 			g.drawOval(i-5, (1000-i)-5, 10, 10);
	 			g.drawOval(i-5, i-5, 10, 10);
   	 		}
   	 		
   	 		count++;
   	 	}
   	 	
   	 	for(int i = 250; i <= 750; i++) 
	 	{
	 		g.fillRect(i, 1000-i, 2, 2);
	 	
	 		if(count%25 == 0) 
	 			g.drawOval(i-5, (1000-i)-5, 10, 10);
	 		
	 		count++;
	 	}
	}
	
	
	public void paintBG(Graphics g)
    {
    	//set background
    	g.setColor(Color.white);
 	    g.fillRect(1000,0,400,1050);
 	    
 	    
 	    //create border
    	g.setColor(Color.black);
    	
 	    for(int i = 1025; i < 1370; i++) 
 	    {
 	    	g.fillRect(i,25,2,2);
  	    	g.fillRect(i,975,2,2);
 	    }
 	    
 	   for(int i = 25; i < 975; i++) 
 	   {
 		   g.fillRect(1025,i,2,2);
 		   g.fillRect(1370,i,2,2);
 	   }
 	   
 	   
 	   //writing
 	   g.setColor(Color.red);
 	   Font font = new Font("calibri", Font.BOLD, 30);
 	   g.setFont(font);
 	   g.drawString("4D Equation Grapher", 1075, 100);
    }
	
	
	public void writeEquationNew(Graphics g)
	{
		g.setColor(Color.red);
    	Font font = new Font("calibri", Font.PLAIN, 40);
  	   	g.setFont(font);
  	   	
  	   	g.drawString("x = " + Main.PFunctionx, 1075, 200);
  		g.drawString("y = " + Main.PFunctiony, 1075, 240);
  		g.drawString("z = " + Main.PFunctionz, 1075, 280);
  		g.drawString("t = " + Main.PFunctiont, 1075, 320);
	}


	
    //getters and setters
    
    public void settracestep(double tracestep) {
    	this.tracestep = tracestep;
    }
    
    public double gettracestep() {
    	return tracestep;
    }

	public double getuMin() {
		return uMin;
	}

	public void setuMin(double uMin) {
		this.uMin = uMin;
	}

	public double getuMax() {
		return uMax;
	}

	public void setuMax(double uMax) {
		this.uMax = uMax;
	}

	public double getvMin() {
		return vMin;
	}

	public void setvMin(double vMin) {
		this.vMin = vMin;
	}

	public double getvMax() {
		return vMax;
	}

	public void setvMax(double vMax) {
		this.vMax = vMax;
	}

	public double getWindow() {
		return window;
	}

	public void setWindow(double window) {
		this.window = window;
	}

	public double getwMax() {
		return wMax;
	}

	public void setwMax(double wMax) {
		this.wMax = wMax;
	}

	public double getwMin() {
		return wMin;
	}

	public void setwMin(double wMin) {
		this.wMin = wMin;
	}
}