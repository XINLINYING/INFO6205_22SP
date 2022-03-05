package edu.neu.coe.info6205.union_find;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class UF_Client {
	public int count(int NumberOfSites) {
		   UF_HWQUPC obj = new UF_HWQUPC(NumberOfSites);

	        int NumberOfPairs = 0;
	        Random random = new Random();
	        while (obj.components() > 1){
	            int a = random.nextInt(NumberOfSites);
	            int b = random.nextInt(NumberOfSites);
	            if(!obj.connected(a, b)){
	                obj.union(a,b);
	            }
	            NumberOfPairs++;
	        }
	        return NumberOfPairs;
	}

	public int main() throws IOException  {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			int NumberOfSites =Integer.parseInt(reader.readLine()) ;
			System.out.println("The number of objects: " + NumberOfSites);
			return NumberOfSites;
			
	}
	public static void main(String[] args) throws IOException{
		UF_Client t = new UF_Client();
		System.out.println("Try a number to see the result: ");
		int NumberOfSites = t.main();
		int NumberOfPairs = t.count(NumberOfSites);
		List<Integer> pairs = new ArrayList<>();
	    List<Integer> objects = new ArrayList<>();
	    
		System.out.println("The number of pairs : " + NumberOfPairs);
		
        for(int Objects = 10; Objects < 100000; Objects += 1000 ){
            int Pairs = 0;
            for(int j = 0; j < 10; j++){
            	Pairs += t.count(Objects);
            }
            
            Integer AveragePairs = Pairs / 10;
            pairs.add(AveragePairs);
            objects.add(Objects);
        }
        System.out.println("AveragePairs: " + pairs);
        System.out.println("Objects: " + objects);
	} 
}
