package methods;

import java.io.*;
//import java.util.Scanner;

public class DNA {
	//private static Scanner x;
	public static void main(String[] args){
		String protein = findProtein();
		IBIO.output(protein);
	}
	/** this method calls all of the other methods
	 *  returns the protein that is made from the dna strand
	 * @param dna
	 * @return
	 */
	public static String findProtein(){
		String newprotein="";
		String dna=readFile();
		dna.toUpperCase();
		String complement=findComplement(dna);
		IBIO.output(complement);
		String rna=findRNAStrand(complement);
		IBIO.output(rna);
		int[] startPos;
		startPos=getStartCodonPositions(rna);
		String AA;
		String protein="";
		boolean stop=false;
		int num=0;
		boolean end=false;
		while(startPos[num]!=-1){
			protein="";
			stop=false;
			for(int i=startPos[num];i<=rna.length()-3 && !stop;i+=3){
				AA=findAminoAcid(rna,i);
				if (AA=="STOP"){
					stop=true;
					end=true;
					protein+="\n";
				}
				else{
					protein+=AA;

				}		
			}
			if(stop){
				newprotein+=protein;
			}
			num++;
		}	
		if(end)
			return "The possible proteins are: \n"+newprotein;
		else
			return "the protein was invalid";
	}
	/** This method takes one half of a DNA double helix and 
	//finds the complementary strand
	//@param – String s – DNA strand
	//@return – String - complement 
	 */
	public static String findComplement (String str){
		String complement="";
		for(int i=0; i<str.length(); i++){
			if (str.substring(i,i+1).equals("A"))
				complement+="T";
			else if (str.substring(i,i+1).equals("T"))
				complement+="A";
			else if (str.substring(i,i+1).equals("C"))
				complement+="G";
			else if (str.substring(i,i+1).equals("G"))
				complement+="C";
		}
		return complement;
	}
	/** This method takes one half of a DNA double helix, 
	// finds the complement and returns the RNA equivalent
	//@param – String s – DNA strand
	//@return – String - RNA strand
	 */
	public static String findRNAStrand (String str){
		String rna="";
		for(int i=0; i<str.length(); i++){
			if (str.substring(i,i+1).equals("A"))
				rna+="A";
			else if (str.substring(i,i+1).equals("T"))
				rna+="U";
			else if (str.substring(i,i+1).equals("C"))
				rna+="C";
			else if (str.substring(i,i+1).equals("G"))
				rna+="G";
		}
		return rna;
	}

	/** This converts a valid codon into the appropriate amino /acid
	/@param - String s – the codon
	/@return - String – the amino acid
	 */
	public static String findAminoAcid(String str, int position){
		final String[] AA_CODE ={"UUU","UUC","UUA","UUG","UCU","UCC","UCA","UCG","UAU","UAC","UAA","UAG","UGU","UGC","UGA","UGG","CUU","CUC","CUA","CUG","CCU","CCC","CCA","CCG","CAU","CAC","CAA","CAG","CGU","CGC","CGA","CGG","AUU","AUC","AUA","AUG","ACU","ACC","ACA","ACG","AAU","AAC","AAA","AAG","AGU","AGC","AGA","AGG","GUU","GUC","GUA","GUG","GCU","GCC","GCA","GCG","GAU","GAC","GAA","GAG","GGU","GGC","GGA","GGG",};
		final String[] AA_CODE_VALUE = {"F","F","L","L","S","S","S","S","Y","Y","STOP","STOP","C","C","STOP","W","L","L","L","L","P","P","P","P","H","H","Q","Q","R","R","R","R","I","I","I","M","T","T","T","T","N","N","K","K","S","S","R","R","V","V","V","V","A","A","A","A","D","D","E","E","G","G","G","G",};
		String AA="";
		for(int i=0;i<AA_CODE.length;i++){
			if(AA_CODE[i].equals(str.substring(position,position+3))){
				AA=AA_CODE_VALUE[i];
				return AA;
			}
		}
		return "this should not be returned";
	}
	// This method should return an array of integers which stores the 
	// position of the first nucleotide for all the start codon sequences. 
	public static int[] getStartCodonPositions(String str){
		int startPos[]=new int[100];
		int num=0;
		int pos=0;
		while(num<=2){
			for(int i=0+num;i<=str.length()-(3+num);i+=3){
				if (str.substring(i,i+3).equals("AUG")){
					startPos[pos]=i;
				//	IBIO.output(startPos[pos]);
					pos++;
				}
			}
			num++;
		}
		startPos[pos]=-1;
		return startPos;
	}

	//use this if you have one field on each line of the text file
	public static String readFile(){
		//set up the stream between the program and the file
		//String output = "";
		//File fileIn = new File("Salmonella.txt"); //you can put a variable within the brackets 
		//FileReader fr = new FileReader (fileIn);
		//BufferedReader inFile = new BufferedReader (fr);
		//String line;
		File f=new File("Salmonella.txt");
		String output="";
		
		try{
			FileReader fr=new FileReader(f);
			int ch = fr.read();
			//x= new Scanner(new File("Salmonella.txt"));
		while(ch!=1){
			output+=(char)fr.read();
			ch=fr.read();
		}
		fr.close();
		}
		catch(Exception e){
			IBIO.output("could not find file");
		}
		
		//while((fileIn.hasNext()){ 
		//	line=inFile.readLine();
		//	output+=line;
			
		//}
		//IBIO.output(i);
		//inFile.close(); //must close the file after reading so that you can write back later
		return output;

	}
}