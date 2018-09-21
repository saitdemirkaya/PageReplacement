import java.io.BufferedReader;
import java.io.File;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Queue;

public class Process {

	public static void file_read(BufferedReader reader,File file,PrintWriter writer) throws Exception {
		 Queue<String> qe=new LinkedList<String>();/*For Fifo page Replacement*/
		 BinarySearchTree tree = new BinarySearchTree();/*For Search algorithm creating tree*/
		 char secondChanceBit[];
		 /*For SecondChance Page algorithm.If I come back from the same element again, I am throwing that element in this lining.*/
		 
		int pageCount=0;
		String line = reader.readLine();
		String[] tokenMemory = line.split("\\s+");				
		int size = Integer.parseInt(tokenMemory[1]);
		
		SecondChanceQueue secondChanceQueue = new SecondChanceQueue (size); /*For SecondChance Algorithm*/
		PriorityQueue priorityQueue = new PriorityQueue(size);/*For PriorityQueue Algorithm*/
		
		secondChanceBit = new char[size];
		for(int i = 0; i < size; i++) secondChanceBit[i] = ' ';/*firstly all secondChance eleman are ' ' */
		
		writer.println("Memory "+size);
		String[] memory = new String[size];
		line = reader.readLine();
		String[] tokenReplacement = line.split("\\s+");/*name of replacement algorithm*/
		line = reader.readLine();/*name of searchStructure*/
		 long startTime = System.nanoTime(); 
/**
* I used binary search tree and queue structure. The first control in the fifo algorithm is to exceed memory size. 
 * If it does not exceed 2, there are 2 controls. If it is in tree, I print the previous elements on screen. 
  * If not, I add both tail and tree.

	If the element is not found in the tree, I remove the first element from both the tree and the tail and add a new one instead.
 
  * I keep this change as an alternative. According to the number of changes, I transfer the queue elements to a queue and write them to the file. 
The first element of the queue is changed, but the queue is the element next to the head. 
		 */
		
		if(tokenReplacement[1].equals("FIFO")) {
			writer.println("FIFO Page Replacement");
			writer.println("Binary Search Tree\n");		
			line = reader.readLine();
			int i=0;
			int change=0;/*for changing elements count*/
			while(line!=null) {
				String[] token = line.split("\\s+");
				char c = token[1].charAt(0);
				int k=0;
				if(i>=size) {
							if(tree.find(c)) {
								writer.print("\t\t");
								
								for(String s : qe) {
									memory[(k+change)%size] =s.toString();/*pass to memory array*/
									k++;
								}
								
								for(int m=0;m<size;m++) {
									writer.print(memory[m]+" ");/*print array*/
								}
								writer.println();	
								
							}else {
								char t = qe.peek().charAt(0);/*find deleting value*/								
								tree.deleteKey(t);/*delete tree*/
								qe.remove();/*delete queue*/
								qe.add(token[1]);
								tree.insert(c);								
								change++;
								
								writer.print("Page Fault \t");
								pageCount++;
								for(String s : qe) {
									memory[(k+change%size)%size] =s.toString();
									k++;
								}
								for(int m=0;m<size;m++) {
									writer.print(memory[m]+" ");
								}
								writer.println();
							}
				}else {/*i< size adding*/
					
					if(tree.find(c)) {/*if queue has a char, don't change anything print queue elements*/
						writer.print("\t\t");
						for(String s : qe) { 
							writer.print(s.toString()+" "); 
							}
						writer.println();
					}else {/*if hasn't char, add queue and tree.Print file*/
						qe.add(token[1]);
						tree.insert(c);
						i++;
						writer.print("Page Fault "+"\t");
						pageCount++;
						for(String s : qe) { 
							writer.print(s.toString()+" "); 
							  
							}
						writer.println();
					}
					
				}
				line = reader.readLine();
			}
			
		}else if(tokenReplacement[1].equals("SecondChance")) {
			/**
			 *In SecondChance I used a 2-dimensional queue array. 
			 *The first one holds the sequence number of the elements, and the other holds the value of the elements.
			 * I've done the same with the Fifo controls here.
			 * 
			 * If the element is in the tree, I go to the chanceReferance () function in the queue and set the referance bit of this element to 1.
			 * 
			 * I need to change the element when I up the size. I checked the referance bits. 
			 * I did the delete operation by passing the referance bit 1. And this elements referance bit = 0.
			 * Now the changeBit [] hold this elements.For print the file.
			 */
			writer.println("SecondChance Page Replacement");
			writer.println("Binary Search Tree\n");		
			line = reader.readLine();// first element
			int i=0;
			int bit=0;
			
			int change=secondChanceQueue.front;/*It walks in the tail to find the element to be deleted.*/
			while(line!=null) {			
				String[] token = line.split("\\s+");
				char c = token[1].charAt(0);
				int secondChange=0;
				char delete_tree;
				//int k=0;
				if(i>=size) {
					if(tree.find(c)) {
						secondChanceQueue.chanceReferance(c);/* change referance bit =1*/
						writer.print("\t\t");
						for(int m=0;m<secondChanceQueue.size;m++) {/*print queue's value*/
							writer.print(secondChanceQueue.array[m][1]+" ");
						}
						writer.println();
						
					}else {
						while(secondChanceQueue.array[change][0]=='1') {/*first element with reference bit is 0.*/
							secondChanceQueue.array[change][0]='0';/*If element's bit 1, then use it.So referance bit set the 0*/
							secondChanceBit[bit] = secondChanceQueue.array[change][1];
							bit++;
							secondChange++;/*If it enters the while loop, it checks the state of writing "Second chance" next to the elements.*/
							change= (change+1)%size;	/*deleting elemen++*/
						}
						delete_tree = secondChanceQueue.array[change][1];
						tree.deleteKey(delete_tree);
						secondChanceQueue.array[change][1]=c;
						tree.insert(c);
						secondChanceQueue.array[change][0]='0';
						change= (change+1)%size;
						writer.print("Page Fault "+"\t");
						pageCount++;
						for(int m=0;m<secondChanceQueue.size;m++) {
							writer.print(secondChanceQueue.array[m][1]+" ");
						}
						if(secondChange!=0) {/*writing Second Chance*/
							writer.print(" Second Chance ");
							for(int a=0;a<size;a++) {/*reset the secondChanceBit array.*/
								if(secondChanceBit[a]!=' ') {
									writer.print(secondChanceBit[a]+" ");
								}
							}
							for(int a=0;a<size;a++) {
								secondChanceBit[a]=' ';
							}
							bit=0;
						}
						
						writer.println();
						
					}
					
				}else {
					if(tree.find(c)) {
						secondChanceQueue.chanceReferance(c);// change referance bit =1 degisiklik yapmadan bas.
						writer.print("\t\t");
						for(int m=0;m<secondChanceQueue.size;m++) {
							writer.print(secondChanceQueue.array[m][1]+" ");
						}
						writer.println();
						
					}else {
						secondChanceQueue.addValue(c);
						tree.insert(c);
						i++;
						writer.print("Page Fault "+"\t");
						pageCount++;
						for(int m=0;m<secondChanceQueue.size;m++) {
							writer.print(secondChanceQueue.array[m][1]+" ");
						}
						writer.println();
						
					}
					
					
				}
					//If the page to be referenced is already in memory.
					
					line = reader.readLine();
					
				}
			
				
			
		}else {/*priority queue*/
			/**
			 * This is the same as the fifo process.
			 * According to Fifo, the element to delete after memory is full here is different.
			 * 
			 * I have defined findMax () function in binarySearchTree for this. Maximum function is found and deleted.
			 * 
			 */
			writer.println("PriorityQueue Page Replacement");
			writer.println("Binary Search Tree");
			
			
			line = reader.readLine();
			int i=0;
			while(line!=null) {
				String[] token = line.split("\\s+");
				char c = token[1].charAt(0);
				if(i>=size) {
					if(tree.find(c)) {
						writer.print("\t\t");
						for(int m=0;m<priorityQueue.size;m++) {
							writer.print(priorityQueue.array[m]+" ");
						}
						writer.println();
					}else {
						char maxChar = tree.maxValueFind();/*finding max char*/
						tree.deleteKey(maxChar);/*delete in tree*/
						for(int s=0;s<priorityQueue.size;s++) {
							if(maxChar==priorityQueue.array[s]) {
								priorityQueue.array[s]=c;
								tree.insert(c);
							}
						}
						writer.print("Page Fault "+"\t");
						
						pageCount++;
						for(int m=0;m<priorityQueue.size;m++) {
							writer.print(priorityQueue.array[m]+" ");
						}
						writer.println();
					}
					
				}else {
					if(tree.find(c)) {
						writer.print("\t\t");
						for(int m=0;m<priorityQueue.size;m++) {
							writer.print(priorityQueue.array[m]+" ");
						}
						writer.println();
					}else {
						priorityQueue.addValue(c);
						tree.insert(c);
						i++;
						writer.print("Page Fault "+"\t");
						pageCount++;
						for(int m=0;m<priorityQueue.size;m++) {
							writer.print(priorityQueue.array[m]+" ");
						}
						writer.println();
					}
					
					
				}
				line = reader.readLine();
			}
			
		}
		 long endTime = System.nanoTime(); 
         long estimatedTime = endTime - startTime; 
         double seconds = (double)estimatedTime/1000000000;
         
		writer.println(pageCount);
		System.out.println(seconds+" seconds");// calculate time
		writer.close();
	}
}