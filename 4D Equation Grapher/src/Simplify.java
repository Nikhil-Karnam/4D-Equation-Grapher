import java.util.ArrayList;

public class Simplify {
	
	public static void main(String[] args)
	{
		
	}
	
	
    //distributes negative sign
    public static String distribute(String str)
    {
    	String second = str.substring(matchingBracket(str, 1) + 1);
    	String f = str.substring(0, matchingBracket(str, 1) + 1);
    	
    	while(canDistribute(f)) 
    	{			
    			
    			f = f.substring(2, f.length()-1);
    			f = balanceBrackets(f);
        		f = "+" + f;
        		
        		if(f.substring(0,2).equals("+-"))
        		{
        			f = f.substring(1);
        		}
        			    			    			    			
    			for(int i = 0; i < f.length()-1; i++) 
    			{
    				if(outsideBrackets(f, i)) 
    				{
    					//checks for places to distribute negative within the brackets itself (recurs)
    					if(f.substring(i, i+1).equals("-"))
    					{
    						
    						//I have to use 2 for loops because i+2 is out of the bounds for the for loop
    						if(f.substring(i+1, i+2).equals("(")) 
    						{        						
    							int location = 0;
        						int count = 1;
        						
        						for(int j = i+2; j < f.length(); j++) 
        						{
        							if(f.substring(j, j+1).equals("(")) 
        							{
        								count++;
        							}
        							else if(f.substring(j, j+1).equals(")")) 
        							{
        								count--;
        							}
        							
        							if(count == 0)
        							{
        								location = j;
        								break;
        							}
        						}
        						
        						f = f.substring(0,i) + distribute(f.substring(i, location+1)) + f.substring(location+1);
    						}
    					}
    						
    					if(f.substring(i, i+1).equals("+")) 
    					{
    						f = f.substring(0, i) + "-" + f.substring(i+1);
    					}
    					else if(f.substring(i, i+1).equals("-")) 
    					{
    						f = f.substring(0, i) + "+" + f.substring(i+1);
    					}
    				}
    			}
        	}
    	
    	return f + second;
    }
	
	
	//random supporting methods below
	
    //Returns true if string f is a constant
    public static boolean isConstant(String f) 
	{
		if(!f.contains("x"))
			return true;
		
		return false;
	}
	
	
  //returns true if a String can be foiled
  	public static boolean canFoil(String f) 
  	{
  		if(f.startsWith("(") && f.endsWith(")") && !canBalanceBrackets(f))
  		{
  			return true;
  		}
  		
  		return false;
  	}
  	
  //returns the function with all spaces removed
  	public static String noSpace(String f) 
  	{
  		String[] a = f.split(" ");
  		
  		f = "";
  		for(int i = 0; i < a.length; i++) 
  		{
  			f = f + a[i];
  		}
  		
  		return f;
  	}
    
  	
    //returns true if the operator at a point in the equation exists outside brackets
    public static boolean outsideBrackets(String f, int sub) 
    {
    	int count = 0;
    	String[] line = f.split("");
    	
    	for(int i = 0; i < sub; i++)
    	{
    		if(line[i].equals("(")) 
    			count++;
    		
    		if(line[i].equals(")")) 
    			count--;
    	}
    	
    	if(count == 0) 
    		return true;

    	return false;
    }
    
    
    //takes a string, splits it into terms and returns it.
    public static String[] splitf(String f) 
    {    	    	
    	f = f.replaceAll("-", "\\+-");
    	
    	for(int i = 1; i < f.length()-1; i++) 
    	{
    		if(f.substring(i, i+1).equals("+") && outsideBrackets(f, i) && !f.substring(i-1, i).equals("^"))
    		{
    			f = f.substring(0, i) + "plus" + f.substring(i+1);
    		}
    	}
    	
    	f = f.replaceAll("\\+-", "-");

		return f.split("plus");
		
    }
    
    //combines an array of terms into an equation
    public static String unsplit(String[] f) 
    {
    	String a = "";
    	
    	for(int i = 0; i < f.length; i++) 
    		a += f[i] + "+";
    		
    	a = a.substring(0, a.length()-1);
    	a = a.replaceAll("\\+-", "\\-");
    	
    	return a;
    }
    
    //input a string, deletes unnecessary brackets, returns string
    public static String fixBrackets(String f)
    {    	
    	String[] terms = splitf(f);
    	
    	for(int i = 0; i < terms.length; i++) 
    	{
    		//here is where you actually delete the extra brackets on the outside and distribute 1 layer
    		//terms[i] = BalanceDistribute(terms[i]);
    		terms[i] = balanceBrackets(terms[i]);
    		
    		if(splitf(terms[i]).length > 1) 
    		{
    			terms[i] = fixBrackets(terms[i]);
    		}
    	}
    	
    	f = "";
    	for(int i = 0; i < terms.length-1; i++) 
    	{
    		f = f + terms[i] + "+";
    	}
    	f = f + terms[terms.length-1];
    	f = f.replaceAll("\\+-", "-");

    	return f;
    }
    
    
    //returns true if there are unnecessary brackets that can be removed on the outside
    public static boolean canBalanceBrackets(String f) 
    {    	
    	if(!f.startsWith("(") || !f.endsWith(")")) 
    	{
    		return false;
    	}
    	
    	f = f.substring(1, f.length()-1);
    	
    	int count = 0;
    	String[] line = f.split("");

    	for(int i = 0; i < f.length(); i++)
    	{
    		if(line[i].equals("(")) 
    		{
    			count++;
    		}
    		if(line[i].equals(")")) 
    		{
    			count--;
    		}
    		
    		if(count < 0) 
    		{
    			return false;
    		}
    	}
    	
    	if(count == 0) 
    	{
    		return true;
    	}
    	
    	return false;
    }
	
    
    //returns true if the brackets can be distributed
    public static boolean canDistribute(String f) 
    {
    	if(f.length() > 1) 
    	{
    		if(f.substring(0, 2).equals("-(") && f.substring(f.length()-1).equals(")")) 
        	{
        		return true;
        	}
    	}
    	
    	return false;
    }
    
   
    
	//gets rid of extra plus sign from the distribute method
    public static String distributeBrackets(String f) 
    {
    	f = distribute(f);
    	
		if(f.substring(0,1).equals("+"))
		{
			f = f.substring(1);
		}
		
		return f;
    }
    
    //balances brackets
    public static String balanceBrackets(String f) 
    {
    	while(canBalanceBrackets(f))
    	{
        	f = f.substring(1, f.length()-1);
    	}
    	
    	return f;
    }
    
    
    //adds in multiplication signs into the equation wherever necessary
    public static String multiply(String f) 
    {
    	for(int i = 0; i < f.length()-1; i++)
    	{	
    		if(checkMultiply(f, i))
    		{
    			f = f.substring(0, i+1) + "*" + f.substring(i+1);
    		}
    	}
    	
    	return f;
    }
    
    //returns true if you can insert a multiply sign
    public static boolean checkMultiply(String f, int i) 
    {
    	if(Character.isDigit(f.charAt(i)) && (Character.isAlphabetic(f.charAt(i+1)) || f.substring(i+1, i+2).equals("("))) 
			return true;
			
    	else if(f.substring(i, i+1).equals(")") && !isOperator(f.substring(i+1, i+2))) 
    		return true;
    	
    	else if((f.substring(i, i+1).equals("I") || f.substring(i, i+1).equals("E")) && !isOperator(f.substring(i+1, i+2)))
    		return true;
    	
    	else if((f.substring(i, i+1).equals("u") || f.substring(i, i+1).equals("v") || f.substring(i, i+1).equals("w")) && Character.isAlphabetic(f.charAt(i+1)))
    		return true;
    	
    	return false;
    }
    
    //contains list of operators
    //returns true if the input string is an operator
    public static boolean isOperator(String f) 
    {
    	ArrayList<String> list = new ArrayList<String>();
    	
    	list.add("+");
    	list.add("-");
    	list.add("*");
    	list.add("/");
    	list.add("^");
    	list.add(")");
    	list.add(".");
    	
    	for(int i = 0; i < list.size(); i++) 
    	{
    		if(list.get(i).equals(f)) 
    		{
    			return true;
    		}
    	}
    	
    	return false;
    }
    
    //returns index position of the close bracket matching the respective open bracket at index position n
    public static int matchingBracket(String str, int n) 
    {
    	int count = 1;
    	String f = str.substring(n);
    	    	
    	for(int i = 1; i < f.length()-1; i++) 
    	{
    		if(f.substring(i, i+1).equals("("))
    		{
    			count++;
    		}
    		if(f.substring(i, i+1).equals(")")) 
    		{
    			count--;
    		}
    		
    		if(count == 0) 
    		{
    			return i + str.length() - f.length();
    		}
    	}
    	
    	return f.length()-1 + str.length() - f.length();
    }
}
