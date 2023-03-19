package DM_Ass3.DM_Ass3;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Random;
import java.util.Vector;

import javax.naming.directory.SearchControls;
import javax.swing.text.Element;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class App 
{
	
	public static Vector<dataForm> allData = new Vector<dataForm>();
	public static Vector<dataForm> data75 = new Vector<dataForm>();
	public static Vector<dataForm> data25 = new Vector<dataForm>();
	
	public static Vector<Classes> classes = new Vector<Classes>();
	public static Vector<probability> probability = new Vector<probability>();
	public static Vector<probability> Classifier = new Vector<probability>();
	public static int index = 0 , accuracy = 0;
	
    public static void main( String[] args ) throws IOException
    {
       
    	loadData("F:\\cardata.xlsx");
    	divideDataTo7525();
    	getClasses();
    	fillClasses();
    	getProb();   	
    	testing();
    	for( int i = 0; i < classes.size() ; i++ )
    	{
    		System.out.println("///////////////////////////////////////////////////////////////////////  name = "+classes.get(i).name + "  count = "+ classes.get(i).count );
    		System.out.println("buyingPrice");
    		for( int j = 0; j < classes.get(i).buyingPrice.size(); j++ )
    		{
    			System.out.println("subName = "+classes.get(i).buyingPrice.get(j).name + "   count = " +classes.get(i).buyingPrice.get(j).count );
    		}
    		System.out.println("**********************************");
    		System.out.println("maintenancePrice");
    		for( int j = 0; j < classes.get(i).maintenancePrice.size(); j++ )
    		{
    			System.out.println("subName = "+classes.get(i).maintenancePrice.get(j).name + "   count = " +classes.get(i).maintenancePrice.get(j).count );
    		}
    		System.out.println("**********************************");
    		System.out.println("numberOfDoors");
    		for( int j = 0; j < classes.get(i).numberOfDoors.size(); j++ )
    		{
    			System.out.println("subName = "+classes.get(i).numberOfDoors.get(j).name + "   count = " +classes.get(i).numberOfDoors.get(j).count );
    		}
    		System.out.println("**********************************");
    		System.out.println("personsToCarry");
    		for( int j = 0; j < classes.get(i).personsToCarry.size(); j++ )
    		{
    			System.out.println("subName = "+classes.get(i).personsToCarry.get(j).name + "   count = " +classes.get(i).personsToCarry.get(j).count );
    		}
    		System.out.println("**********************************");
    		System.out.println("sizeOfLuggageBoot");
    		for( int j = 0; j < classes.get(i).sizeOfLuggageBoot.size(); j++ )
    		{
    			System.out.println("subName = "+classes.get(i).sizeOfLuggageBoot.get(j).name + "   count = " +classes.get(i).sizeOfLuggageBoot.get(j).count );
    		}
    		System.out.println("**********************************");
    		System.out.println("estimatedSafety");
    		for( int j = 0; j < classes.get(i).estimatedSafety.size(); j++ )
    		{
    			System.out.println("subName = "+classes.get(i).estimatedSafety.get(j).name + "   count = " +classes.get(i).estimatedSafety.get(j).count );
    		}
    	}
    	System.out.println("*************/////////////////////////////////////////////////////////////////*********************");
    	for( int j = 0; j < probability.size(); j++ )
		{
			System.out.println(probability.get(j).cName+"  "+probability.get(j).pName+"  "+probability.get(j).prob);
		}
    	System.out.println("*************/////////////////////////////////////////////////////////////////*********************");    
    	System.out.println("Accuracy = "+((double)accuracy/data25.size()*100)+" %");
    	

    }
    
    
    
    public static void loadData(String path) throws IOException
    {
    	File excelFile = new File( path );
        FileInputStream fis = new FileInputStream(excelFile);      
        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        XSSFSheet sheet= workbook.getSheetAt(0);       
        Iterator<Row> rowit = sheet.iterator(); 
        
        int position = 1;
        
        while( rowit.hasNext() )
        {
        	Row row = rowit.next();
        	Iterator<Cell> cellit = row.cellIterator();
        	
        	dataForm x = new dataForm();
        	
        	while( cellit.hasNext() )
        	{
        		Cell cell = cellit.next();
        		if( position == 1 )
        		{
        			x.buyingPrice = cell.toString();
        			position++;
        		}
        		else if( position == 2 )
        		{
        			x.maintenancePrice = cell.toString();
        			position++;
        		}
        		else if( position == 3 )
        		{
        			x.numberOfDoors = cell.toString();
        			position++;
        		}
        		else if( position == 4 )
        		{
        			x.personsToCarry = cell.toString();
        			position++;
        		}
        		else if( position == 5 )
        		{
        			x.sizeOfLuggageBoot = cell.toString();
        			position++;
        		}
        		else if( position == 6 )
        		{
        			x.estimatedSafety = cell.toString();
        			position++;
        		}
        		else if( position == 7 )
        		{
        			x.carAcceptability = cell.toString();
        		}       			
        	}
        	allData.add(x);
        	position = 1;
        } 
        workbook.close();
        fis.close();
    }
    
    
    public static void divideDataTo7525()
    {
    	Vector<Integer> uniqueRow = new Vector<Integer>();
    	Random rand = new Random();
    	int index = 0;
    	while( true )
    	{
    		index = rand.nextInt(allData.size() - 0 + 1) + 0;
    		if( !uniqueRow.contains(index) && index != allData.size() )
    		{
    			uniqueRow.add(index);
    			dataForm x = new dataForm();
        		
        		x.buyingPrice = allData.get(index).buyingPrice;
    		    x.maintenancePrice = allData.get(index).maintenancePrice;
    		    x.numberOfDoors  = allData.get(index).numberOfDoors;
    		    x.personsToCarry = allData.get(index).personsToCarry;
    		    x.sizeOfLuggageBoot = allData.get(index).sizeOfLuggageBoot;
    		    x.estimatedSafety = allData.get(index).estimatedSafety;
    		    x.carAcceptability = allData.get(index).carAcceptability;
    		    
    		    if( data75.size() < allData.size()*0.75 )
    		    {
    		    	data75.add(x);
    		    }
    		    else if ( data25.size() < allData.size()*0.25 )
    		    {
    		    	data25.add(x);	
    		    }
    		    
    		    if( data75.size() + data25.size() == allData.size() )
    		    {
    		    	break;
    		    }
    		}
    	}
    	uniqueRow.clear();
    }
    
    
    public static void getClasses()
    {
    	Vector<String> UniqueChecker = new Vector<String>();
    	for( int i = 0; i < data75.size(); i++ )
    	{
    		if( !UniqueChecker.contains(data75.get(i).carAcceptability) )
    		{
    			UniqueChecker.add(data75.get(i).carAcceptability);
    			
    			Classes x = new Classes();
    			x.name = data75.get(i).carAcceptability;
    			x.count ++;
    			classes.add(x);
    		}
    		else
    		{
    			for( int j = 0; j < classes.size(); j++ )
    			{
    				if( classes.get(j).name.equals(data75.get(i).carAcceptability) )
    				{
    					classes.get(j).count ++;
    					break;
    				}
    			}
    		}
    	}
    	UniqueChecker.clear();
    }
    
    
    public static void fillClasses()
    {
    	Vector<String> buyingPriceChecker = new Vector<String>();
		Vector<String> maintenancePriceChecker = new Vector<String>();
		Vector<String> numberOfDoorsChecker = new Vector<String>();
		Vector<String> personsToCarryChecker = new Vector<String>();
		Vector<String> sizeOfLuggageBootChecker = new Vector<String>();
		Vector<String> estimatedSafetyChecker = new Vector<String>();
		
    	for( int i = 0; i < classes.size(); i++ )
    	{
    		buyingPriceChecker.clear();
    		maintenancePriceChecker.clear();
    		numberOfDoorsChecker.clear();
    		personsToCarryChecker.clear();
    		sizeOfLuggageBootChecker.clear();
    		estimatedSafetyChecker.clear();
    		
    		for( int j = 0; j < data75.size(); j++ )
    		{
    			usedInSideFillClasses( buyingPriceChecker , classes.get(i) , data75.get(j).buyingPrice , data75.get(j).carAcceptability , classes.get(i).buyingPrice );
    			
    			usedInSideFillClasses( maintenancePriceChecker , classes.get(i) , data75.get(j).maintenancePrice , data75.get(j).carAcceptability , classes.get(i).maintenancePrice );
    			
    			usedInSideFillClasses( numberOfDoorsChecker , classes.get(i) , data75.get(j).numberOfDoors , data75.get(j).carAcceptability , classes.get(i).numberOfDoors );
    			
    			usedInSideFillClasses( personsToCarryChecker , classes.get(i) , data75.get(j).personsToCarry , data75.get(j).carAcceptability , classes.get(i).personsToCarry );
    			
    			usedInSideFillClasses( sizeOfLuggageBootChecker , classes.get(i) , data75.get(j).sizeOfLuggageBoot , data75.get(j).carAcceptability , classes.get(i).sizeOfLuggageBoot );
    			
    			usedInSideFillClasses( estimatedSafetyChecker , classes.get(i) , data75.get(j).estimatedSafety , data75.get(j).carAcceptability , classes.get(i).estimatedSafety );
    		}    		
    	}
    	buyingPriceChecker.clear();
		maintenancePriceChecker.clear();
		numberOfDoorsChecker.clear();
		personsToCarryChecker.clear();
		sizeOfLuggageBootChecker.clear();
		estimatedSafetyChecker.clear();
    }
    
    
    public static void usedInSideFillClasses( Vector<String> Checker , Classes cla , String cat , String Class , Vector<element> catVce )
    {
    	if( !Checker.contains( cat ) && Class.equals( cla.name ) )
		{
    		Checker.add(cat);
			
			element x = new element();
			x.name = cat;
			x.count ++;
			catVce.add(x);
		}
		else
		{
			for( int k = 0; k < catVce.size(); k++ )
			{
				if( catVce.get(k).name.equals(cat ) && Class.equals( cla.name ) )
				{
					catVce.get(k).count ++;
					break;
				}
			}
		}
    }
    
    
    public static void getProb()
    {
    	for( int i = 0; i < classes.size() ; i++ )
    	{   	
    		calculateprob( "buyingPrice" , classes.get(i) , classes.get(i).buyingPrice );
   		
    		calculateprob( "maintenancePrice" , classes.get(i) , classes.get(i).maintenancePrice );
    		
    		calculateprob( "numberOfDoors" , classes.get(i) , classes.get(i).numberOfDoors );
    		
    		calculateprob( "personsToCarry" , classes.get(i) , classes.get(i).personsToCarry );
    		
    		calculateprob( "sizeOfLuggageBoot" , classes.get(i) , classes.get(i).sizeOfLuggageBoot );
    		
    		calculateprob( "estimatedSafety" , classes.get(i) , classes.get(i).estimatedSafety );	
    	}
    }
    
    
    public static void calculateprob( String cat , Classes cla , Vector<element> catVce )
    {
    	for( int j = 0; j < catVce.size() ; j++ )
		{
			probability x = new probability();
			x.cName = cla.name;
			x.pName = cat+"_"+catVce.get(j).name;
			x.prob = (double) catVce.get(j).count / cla.count;
			probability.add(x);
		}
    }
    
    
    public static void testing()
    {
    	Vector<Double> bigProb = new Vector<Double>();
    	double rowProb = 1, min = -100000000;
    	int position = -1;
    	for( int j = 0; j < data25.size(); j++ )
    	{
    		bigProb.clear();
    		min = -100000000;
    		for( int i = 0; i < classes.size(); i++ )
        	{
    			rowProb = 1;
    			if( getIndexOfProb( "buyingPrice_"+data25.get(j).buyingPrice , classes.get(i).name ) != -1 )
    			{
    				rowProb *= probability.get( getIndexOfProb( "buyingPrice_"+data25.get(j).buyingPrice , classes.get(i).name ) ).prob;
    			}
    			else
    			{
    				rowProb = 0;
    			}
    			
    			if( getIndexOfProb( "maintenancePrice_"+data25.get(j).maintenancePrice , classes.get(i).name ) != -1 )
    			{
    				rowProb *= probability.get( getIndexOfProb( "maintenancePrice_"+data25.get(j).maintenancePrice , classes.get(i).name ) ).prob;
    			}
    			else
    			{
    				rowProb = 0;
    			}
    			
    			if( getIndexOfProb( "numberOfDoors_"+data25.get(j).numberOfDoors , classes.get(i).name ) != -1 )
    			{
    				rowProb *= probability.get( getIndexOfProb( "numberOfDoors_"+data25.get(j).numberOfDoors , classes.get(i).name ) ).prob;
    			}
    			else
    			{
    				rowProb = 0;
    			}
    			
    			if( getIndexOfProb( "personsToCarry_"+data25.get(j).personsToCarry , classes.get(i).name ) != -1 )
    			{
    				rowProb *= probability.get( getIndexOfProb( "personsToCarry_"+data25.get(j).personsToCarry , classes.get(i).name ) ).prob;
    			}
    			else
    			{
    				rowProb = 0;
    			}
    			
    			if( getIndexOfProb( "sizeOfLuggageBoot_"+data25.get(j).sizeOfLuggageBoot , classes.get(i).name ) != -1 )
    			{
    				rowProb *= probability.get( getIndexOfProb( "sizeOfLuggageBoot_"+data25.get(j).sizeOfLuggageBoot , classes.get(i).name ) ).prob;
    			}
    			else
    			{
    				rowProb = 0;
    			}
    			
    			if( getIndexOfProb( "estimatedSafety_"+data25.get(j).estimatedSafety , classes.get(i).name ) != -1 )
    			{
    				rowProb *= probability.get( getIndexOfProb( "estimatedSafety_"+data25.get(j).estimatedSafety , classes.get(i).name ) ).prob;
    			}
    			else
    			{
    				rowProb = 0;
    			}    
    			bigProb.add(rowProb * ( (double)classes.get(i).count / data75.size() ) );	   			
        	}
    		for( int k = 0; k < bigProb.size(); k++ )
    		{
    			if( bigProb.get(k) > min )
    			{
    				position = k;
    				min = bigProb.get(k);
    			}
    		}
    		if( classes.get(position).name.equals( data25.get(j).carAcceptability ) )
    		{
    			accuracy++;
    		}   		
    	}
    	
    }
    
    
    public static int getIndexOfProb( String seacrh , String givenClass )
    {  	
    	int index =-1;
    	for( int i = 0; i < probability.size(); i++ )
		{
			if( seacrh.equals( probability.get(i).pName ) && givenClass.equals( probability.get(i).cName ) )
			{
				index = i;
			}
		} 
    	return index;
    }
    
    
    
    
}
