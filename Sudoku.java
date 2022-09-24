class Sudoku //  Thanks to Parth for contributing
{
	public static int[] sudoku = new int[81];

	// to fill the sudoku array with 0s
	public static void fillZeroes()
	{
		//  looping through all the elements of the sudoku
		for(int i = 0; i < 81; i++)
			sudoku[i] = 0;
	}

	// to check if the given array is completely filled with zeroes
	public static boolean allZeroes(int[] arr)
	{
		// looping through all the elements of th array
		for(int i = 0; i < arr.length; i++)
			// if a num other than zero is found return false
			if(arr[i] != 0)
				return false;

		// if only zeroes are found return true;
		return true;
	}

	// to print the zeroes
	public static void print(int[] sudoku)
	{
		System.out.println();
		for (int i = 0; i < 81; i++) 
		{
			// for empty places
			if(sudoku[i] == 0)
				System.out.print("_, ");
			// for numbers
			else
				System.out.print(sudoku[i] + ", ");
			// for line breaks 
			if((i+1) % 9 == 0)
				System.out.println();
		}
		System.out.println();
	}

	// to check whether a num can be filled in the sudoku
	public static boolean possible(int[] sudoku, int num, int pos)
	{
		// the x and y pos of the number in the sudoku grid
		int x = pos % 9, y = pos / 9;

		// looping through the row and column of the number to check whether the number is present elsewhere
		for(int i = 0; i < 9; i++)
		{
			// to check the vertical column
			if(sudoku[x + i * 9] == num)
				return false;
			// to check the horizontal row
			if(sudoku[i + y * 9] == num)
				 return false;
		}

		// row and column of the square box in which the number is located
		int row = x / 3, col = y / 3;

		// looping the numbers in the square grid to check whether the numebr is present elsewhere
		for(int i = 0; i < 3; i++)
			for(int j = 0; j < 3; j++)
				// sudoku[x + 9 * y] so that it goes through all the numbers only in that grid
				if(sudoku[(row*3 + i) + (col*3 + j) * 9] == num)
					return false;

		// the number is not present in the row or column or the square grid in which it is located then the number can go in
		return true;
	}

	// to find an empty place in the sudoku
	public static int findZeroInSudoku(int[] sudoku)
	{
		// loop through all the elements and the presence of any zero in it
		for(int i = 0; i < 81; i++)
			if(sudoku[i] == 0)
				return i;
		return -1;
	}

	// using recursion
	// to solve the sudoku
	public static boolean solve()
	{
		// an empty place in the sudoku
		int pos = findZeroInSudoku(sudoku);

		// if no empty place is found then the sudoku is solved
		if(pos == -1)
			return true;

		// loop through all the numbers from 1 to 9 to fill
		for(int i = 1; i < 10; i++)
		{
			// if the number can go
			if(possible(sudoku, i, pos))
			{
				// fill it
				sudoku[pos] = i;

				// try to solve further
				if(solve())
					return true;
			}
			// if the number filled was the wrong number; reset the number
			sudoku[pos] = 0;
		}

		// wasn't able to solve then return false
		return false;
	}

	// using recursion
	// to check whether a sudoku is solvable
	// same as the previous method just returns whether the sudoku is solvable
	// by solving a copy of the sudoku so as to not modify the original sudoku
	public static boolean solvable(int[] sudoku)
	{
		int[] s = sudoku.clone();

		int pos = findZeroInSudoku(s);
		if(pos == -1)
			return true;

		for(int i = 1; i < 10; i++)
		{
			if(possible(s, i, pos))
			{
				s[pos] = i;
				if(solvable(s))
					return true;
			}
			s[pos] = 0 ;
		}
		return false;
	}

	// to try to generate a solved sudoku board by filling it with random while following all the rules of the sudoku
	public static void generateRandom()
	{
		int pos = 0;

		// loop through all the elements of the sudoku
		for(int i = 0; i < 81; i++)
		{
			// the numbers that are in the sudoku
			int[] nums = {1, 2, 3, 4, 5, 6, 7, 8, 9};

			// until a number from 1 to 9 have been filled
			while(!allZeroes(nums))
			{
				int index = 0, num = 0;

				// take a random number from the nums array
				// looped so that 0 does not get taken
				do{
					index = (int)Math.floor(Math.random() * 9);
					num = nums[index];
				}while(num == 0);	

				// if the number can go
				if(possible(sudoku, num, pos))
				{
					// put it
					sudoku[pos] = num;
					// move to the next pos
					pos++;
					// break out of the loop if the num is filled
					break;
				}
				// remove the number from the nums list so as to tell that the number has been tried to fill
				nums[index] = 0;
			}
		}
	}

	// to remove the numbers from the solved Sudoku board to generate a sudoku to solve
	public static void removeNumbers()
	{
		// take a random position from the sudoku array
		int pos = (int)Math.floor(Math.random() * 81);

		// save the value in a temporary variable
		int temp = sudoku[pos];

		// remove the number
		sudoku[pos] = 0;

		// after removing a single number check whether the sudoku is solvable
		if(!solvable(sudoku))
			sudoku[pos] = temp;
	}

	// to generate a sudoku board by making use of the methods above
	public static int[] generate(int amtToTryToRemove)
	{
		// ṭry to generate until a valid sudoku is generated
		do{
			fillZeroes();
			generateRandom();

		}while(!solvable(sudoku));

		int[] solvedSudoku = sudoku.clone();

		// try to remove the numbers from the solved sudoku array to make it playable
		for(int i = 0; i < amtToTryToRemove; i++)
			removeNumbers();

		return solvedSudoku;	
	}

	// Driver Function
	public static void main(String[] args) 
	{	
		// to check the time it took to generate the sudoku
		long start = System.currentTimeMillis();

		int[] solvedSudoku = generate(80);

		// to check the time it took to generate the sudoku
		long end = System.currentTimeMillis();

		System.out.println("It took " + ((end - start)/1000.0) + "s to generate a sudoku and its solution");

		// printing the sudoku with its solution
		print(sudoku);
		print(solvedSudoku);

	}
}
