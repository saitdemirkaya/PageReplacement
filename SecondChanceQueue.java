
//Queue using arrays
public class SecondChanceQueue {
	
	public int size;
	public char[][] array;
	public int rear;
	public int front;

	public SecondChanceQueue(int size)
	{
		this.size = size;
		this.array = new char[size][2];	/*for secondChance algorithm*/	
		this.rear = -1;
		this.front=0;
	}
	public void addValue(char value){

		++rear;
		
		if(rear == size)
		{rear=0;}
			array[rear][1] = value;
			array[rear][0]='0';
		
	}
	public void chanceReferance(char ch)/*search value in queue array*/
	{
		for(int i=0; i < size ; i++)
		{
			if(array[i][1]==ch)
			{
				array[i][0]='1';
			}
		}
	}
}
