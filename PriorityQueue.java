
//Queue using arrays
public class PriorityQueue {
	
	public int size;
	public char[] array;
	public int rear;
	public int front;

	public PriorityQueue(int size)
	{
		this.size = size;
		this.array = new char[size];		
		this.rear = -1;
		this.front=0;
	}

	/* Add value in the queue.*/
	public void addValue(char value){

		++rear;
		
		if(rear == size)
		{rear=0;}
			array[rear] = value;
		
	}
}
